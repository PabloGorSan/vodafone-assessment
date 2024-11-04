package com.example.vodafoneassessment.controller;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ForecastDto {
    private double latitude;
    private double longitude;
    private double temperature;
}
