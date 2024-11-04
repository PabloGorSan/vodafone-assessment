package com.example.vodafoneassessment.data;

import com.example.vodafoneassessment.Utils.ForecastMapper;
import com.example.vodafoneassessment.business.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static org.mapstruct.factory.Mappers.getMapper;

@Service
@RequiredArgsConstructor
public class OpenMeteoDataService {

    private final OpenMeteoClient openMeteoClient;
    private final ForecastMapper mapper = getMapper(ForecastMapper.class);

    public Forecast getTemperature(double latitude, double longitude) {
        return mapper.toModel(openMeteoClient.getTemperature(latitude, longitude));
    }
}
