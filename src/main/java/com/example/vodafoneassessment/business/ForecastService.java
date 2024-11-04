package com.example.vodafoneassessment.business;

import com.example.vodafoneassessment.data.OpenMeteoDataService;
import com.example.vodafoneassessment.persistence.ForecastPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final OpenMeteoDataService openMeteoDataService;
    private final ForecastPersistenceService forecastPersistenceService;

    public Forecast getForecast(double latitude, double longitude) {

        Forecast currentForecast = forecastPersistenceService.getByLatitudeAndLongitude(latitude, longitude);
        System.out.println("Hasta aqui");

        if (currentForecast == null || LocalDateTime.now().isAfter(currentForecast.getDate().plusMinutes(1))) {
            System.out.println("Entre");
            currentForecast = openMeteoDataService.getTemperature(latitude, longitude);

            forecastPersistenceService.deleteByLatitudeAndLongitude(currentForecast.getLatitude(), currentForecast.getLongitude());
            forecastPersistenceService.save(currentForecast);
        }
        System.out.println("Sali");
        return currentForecast;
    }

    public void deleteForecast(double latitude, double longitude) {
        forecastPersistenceService.deleteByLatitudeAndLongitude(latitude, longitude);
    }

}
