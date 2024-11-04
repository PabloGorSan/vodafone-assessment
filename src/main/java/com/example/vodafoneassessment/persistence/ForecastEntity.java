package com.example.vodafoneassessment.persistence;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@Builder
public class ForecastEntity {

    @Id
    private String id;
    private double latitude;
    private double longitude;
    private double temperature;
    private LocalDateTime date;
}
