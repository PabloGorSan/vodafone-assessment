package com.example.vodafoneassessment.persistence;

import com.example.vodafoneassessment.business.Forecast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ForecastPersistenceServiceTest {

    static final double LATITUDE_1 = 10.1;
    static final double LONGITUDE_1 = 20.2;
    static final double TEMPERATURE_1 = 30.3;
    static final LocalDateTime DATE_1 = LocalDateTime.now();

    static final Forecast FORECAST = Forecast.builder()
            .latitude(LATITUDE_1)
            .longitude(LONGITUDE_1)
            .temperature(TEMPERATURE_1)
            .date(DATE_1)
            .build();

    static final ForecastEntity FORECAST_ENTITY = ForecastEntity.builder()
            .latitude(LATITUDE_1)
            .longitude(LONGITUDE_1)
            .temperature(TEMPERATURE_1)
            .date(DATE_1)
            .build();

    @Mock
    MeteoRepository meteoRepository;

    @InjectMocks
    ForecastPersistenceService forecastPersistenceService;

    @Test
    void testGetByLatitudeAndLongitudeSuccessfully() {
        when(meteoRepository.findByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1)).thenReturn(FORECAST_ENTITY);

        Forecast forecast = forecastPersistenceService.getByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);

        assertThat(forecast, is(notNullValue()));
        assertThat(forecast.getLatitude(), is(LATITUDE_1));
        assertThat(forecast.getLongitude(), is(LONGITUDE_1));
        assertThat(forecast.getTemperature(), is(TEMPERATURE_1));
        assertThat(forecast.getDate(), is(DATE_1));
    }

    @Test
    void testGetByLatitudeAndLongitudeNotFound() {
        when(meteoRepository.findByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1)).thenReturn(null);

        Forecast forecast = forecastPersistenceService.getByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);

        assertThat(forecast, is(nullValue()));
    }

    @Test
    void testDeleteByLatitudeAndLongitude() {
        forecastPersistenceService.deleteByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);

        verify(meteoRepository).deleteByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);
    }

    @Test
    void testSaveForecast() {
        forecastPersistenceService.save(FORECAST);

        ArgumentCaptor<ForecastEntity> forecastEntityCaptor = ArgumentCaptor.forClass(ForecastEntity.class);
        verify(meteoRepository).save(forecastEntityCaptor.capture());

        assertThat(forecastEntityCaptor.getValue().getLatitude(), is(FORECAST.getLatitude()));
        assertThat(forecastEntityCaptor.getValue().getLongitude(), is(FORECAST.getLongitude()));
        assertThat(forecastEntityCaptor.getValue().getTemperature(), is(FORECAST.getTemperature()));
        assertThat(forecastEntityCaptor.getValue().getDate(), is(FORECAST.getDate()));
    }
}
