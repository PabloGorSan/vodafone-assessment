package com.example.vodafoneassessment.business;

import com.example.vodafoneassessment.data.OpenMeteoDataService;
import com.example.vodafoneassessment.persistence.ForecastPersistenceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ForecastServiceTest {

    static final double LATITUDE_1 = 10.1;
    static final double LONGITUDE_1 = 20.2;
    static final double TEMPERATURE_1 = 30.3;
    static final LocalDateTime DATE_1 = LocalDateTime.now();
    static final LocalDateTime DATE_2 = LocalDateTime.now().minusMinutes(10);

    static final Forecast FORECAST = Forecast.builder()
            .latitude(LATITUDE_1)
            .longitude(LONGITUDE_1)
            .temperature(TEMPERATURE_1)
            .date(DATE_1)
            .build();

    static final Forecast PAST_FORECAST = Forecast.builder()
            .latitude(LATITUDE_1)
            .longitude(LONGITUDE_1)
            .temperature(TEMPERATURE_1)
            .date(DATE_2)
            .build();

    @Mock
    OpenMeteoDataService openMeteoDataService;
    @Mock
    ForecastPersistenceService forecastPersistenceService;

    @InjectMocks
    ForecastService forecastService;

    @Test
    void testGetNewForecast() {
        when(forecastPersistenceService.getByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1))
                .thenReturn(null);
        when(openMeteoDataService.getTemperature(LATITUDE_1, LONGITUDE_1))
                .thenReturn(FORECAST);

        Forecast forecastResponse = forecastService.getForecast(LATITUDE_1, LONGITUDE_1);

        assertThat(forecastResponse, is(notNullValue()));
        assertThat(forecastResponse.getLatitude(), is(LATITUDE_1));
        assertThat(forecastResponse.getLongitude(), is(LONGITUDE_1));
        assertThat(forecastResponse.getTemperature(), is(TEMPERATURE_1));
        assertThat(forecastResponse.getDate(), is(DATE_1));

        verify(forecastPersistenceService).getByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);
        verify(openMeteoDataService).getTemperature(LATITUDE_1, LONGITUDE_1);
        verify(forecastPersistenceService).deleteByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);
        verify(forecastPersistenceService).save(FORECAST);
    }

    @Test
    void testGetPastForecast() {
        when(forecastPersistenceService.getByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1))
                .thenReturn(PAST_FORECAST);
        when(openMeteoDataService.getTemperature(LATITUDE_1, LONGITUDE_1))
                .thenReturn(FORECAST);

        Forecast forecastResponse = forecastService.getForecast(LATITUDE_1, LONGITUDE_1);

        assertThat(forecastResponse, is(notNullValue()));
        assertThat(forecastResponse.getLatitude(), is(LATITUDE_1));
        assertThat(forecastResponse.getLongitude(), is(LONGITUDE_1));
        assertThat(forecastResponse.getTemperature(), is(TEMPERATURE_1));
        assertThat(forecastResponse.getDate(), is(DATE_1));

        verify(forecastPersistenceService).getByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);
        verify(openMeteoDataService).getTemperature(LATITUDE_1, LONGITUDE_1);
        verify(forecastPersistenceService).deleteByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);
        verify(forecastPersistenceService).save(FORECAST);
    }

    @Test
    void testGetSavedForecast() {
        when(forecastPersistenceService.getByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1))
                .thenReturn(FORECAST);

        Forecast forecastResponse = forecastService.getForecast(LATITUDE_1, LONGITUDE_1);

        assertThat(forecastResponse, is(notNullValue()));
        assertThat(forecastResponse.getLatitude(), is(LATITUDE_1));
        assertThat(forecastResponse.getLongitude(), is(LONGITUDE_1));
        assertThat(forecastResponse.getTemperature(), is(TEMPERATURE_1));
        assertThat(forecastResponse.getDate(), is(DATE_1));

        verifyNoInteractions(openMeteoDataService);
        verify(forecastPersistenceService).getByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);
        verifyNoMoreInteractions(forecastPersistenceService);
    }

    @Test
    void testDeleteForecast() {
        forecastService.deleteForecast(LATITUDE_1, LONGITUDE_1);

        verify(forecastPersistenceService).deleteByLatitudeAndLongitude(LATITUDE_1, LONGITUDE_1);
    }
}
