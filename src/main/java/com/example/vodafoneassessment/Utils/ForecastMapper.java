package com.example.vodafoneassessment.Utils;

import com.example.vodafoneassessment.controller.ForecastDto;
import com.example.vodafoneassessment.data.OpenMeteoResponse;
import com.example.vodafoneassessment.persistence.ForecastEntity;
import com.example.vodafoneassessment.business.Forecast;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper
public interface ForecastMapper {

    @Mapping(target = "id", ignore = true)
    ForecastEntity toEntity(Forecast forecast);

    Forecast toModel(ForecastEntity entity);

    ForecastDto toDto(Forecast forecast);

    @Mapping(target = "temperature", source = "response", qualifiedByName = "MapTemperature")
    @Mapping(target = "date", source = "response", qualifiedByName = "MapDate")
    Forecast toModel(OpenMeteoResponse response);


    @Named("MapTemperature")
    default double getTemperature(OpenMeteoResponse response) {
        return response.getCurrent().temperature_2m();
    }

    @Named("MapDate")
    default LocalDateTime getDate(OpenMeteoResponse response) {
        return LocalDateTime.now();
    }
}
