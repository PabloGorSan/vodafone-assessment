package com.example.vodafoneassessment.controller;

import com.example.vodafoneassessment.Utils.ForecastMapper;
import com.example.vodafoneassessment.business.ForecastService;
import com.example.vodafoneassessment.business.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forecast")
public class ForecastController {

    private final ForecastService forecastService;
    private final KafkaProducerService kafkaProducerService;

    private final ForecastMapper mapper = getMapper(ForecastMapper.class);

    @GetMapping()
    public ForecastDto getForecast(@RequestParam double latitude, @RequestParam double longitude) {
        ForecastDto forecast = mapper.toDto(forecastService.getForecast(latitude, longitude));
        kafkaProducerService.sendMessage("my-Topic", forecast);
        return forecast;
    }

    @DeleteMapping()
    public void deleteForecast(@RequestParam double latitude, @RequestParam double longitude) {
        forecastService.deleteForecast(latitude, longitude);
    }

}
