package com.example.vodafoneassessment.controller;

import com.example.vodafoneassessment.business.Forecast;
import com.example.vodafoneassessment.business.ForecastService;
import com.example.vodafoneassessment.business.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ForecastController.class)
public class ForecastControllerTest {

    static final double LATITUDE_1 = 10.1;
    static final double LONGITUDE_1 = 20.2;
    static final double TEMPERATURE_1 = 30.3;

    static final Forecast FORECAST_1 = Forecast.builder()
                                                .latitude(LATITUDE_1)
                                                .longitude(LONGITUDE_1)
                                                .temperature(TEMPERATURE_1)
                                                .build();

    static final String URI = "/forecast";

    @MockBean
    ForecastService forecastService;

    @MockBean
    KafkaProducerService kafkaProducerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getForecastTypeError() throws Exception {
        mockMvc.perform(get(URI)
                .param("latitude", "bad_latitude")
                .param("longitude", "bad_longitude"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getForecastSuccessfully() throws Exception {
        when(forecastService.getForecast(FORECAST_1.getLatitude(), FORECAST_1.getLongitude())).thenReturn(FORECAST_1);

        mockMvc.perform(get(URI)
                        .param("latitude", String.valueOf(FORECAST_1.getLatitude()))
                        .param("longitude", String.valueOf(FORECAST_1.getLongitude())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(FORECAST_1.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(FORECAST_1.getLongitude()))
                .andExpect(jsonPath("$.temperature").value(FORECAST_1.getTemperature()));

        ArgumentCaptor<ForecastDto> forecastCaptor = ArgumentCaptor.forClass(ForecastDto.class);
        verify(kafkaProducerService).sendMessage(eq("my-Topic"), forecastCaptor.capture());

        assertThat(FORECAST_1.getLatitude(), is(forecastCaptor.getValue().getLatitude()));
        assertThat(FORECAST_1.getLongitude(), is(forecastCaptor.getValue().getLongitude()));
        assertThat(FORECAST_1.getTemperature(), is(forecastCaptor.getValue().getTemperature()));
    }

    @Test
    void deleteForecastSuccessfully() throws Exception {
        mockMvc.perform(delete(URI)
                        .param("latitude", String.valueOf(FORECAST_1.getLatitude()))
                        .param("longitude", String.valueOf(FORECAST_1.getLongitude())))
                .andExpect(status().isOk());

        verify(forecastService).deleteForecast(FORECAST_1.getLatitude(), FORECAST_1.getLongitude());
    }
}
