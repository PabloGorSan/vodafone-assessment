package com.example.vodafoneassessment.business;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Forecast {

    private double latitude;
    private double longitude;
    private double temperature;
    private LocalDateTime date;
}
