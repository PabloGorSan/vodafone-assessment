package com.example.vodafoneassessment.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenMeteoResponse {

    public record Current(double temperature_2m){}

    private double latitude;
    private double longitude;
    private Current current;


}
