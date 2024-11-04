package com.example.vodafoneassessment.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "open-meteo", url = "${open-meteo.url}")
public interface OpenMeteoClient {

    @GetMapping("/forecast?current=temperature_2m")
    OpenMeteoResponse getTemperature(@RequestParam double latitude, @RequestParam double longitude);

}
