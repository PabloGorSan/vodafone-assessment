package com.example.vodafoneassessment.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeteoRepository extends MongoRepository<ForecastEntity, String> {
    ForecastEntity findByLatitudeAndLongitude(double latitude, double longitude);

    void deleteByLatitudeAndLongitude(double latitude, double longitude);
}
