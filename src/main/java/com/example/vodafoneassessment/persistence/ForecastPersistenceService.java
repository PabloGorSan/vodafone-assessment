package com.example.vodafoneassessment.persistence;

import com.example.vodafoneassessment.Utils.ForecastMapper;
import com.example.vodafoneassessment.business.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.mapstruct.factory.Mappers.getMapper;

@Service
@RequiredArgsConstructor
public class ForecastPersistenceService {

    private final MeteoRepository meteoRepository;
    private final ForecastMapper mapper = getMapper(ForecastMapper.class);

    public Forecast getByLatitudeAndLongitude(double latitude, double longitude) {
        return mapper.toModel(meteoRepository.findByLatitudeAndLongitude(latitude, longitude));
    }

    public void deleteByLatitudeAndLongitude(double latitude, double longitude) {
        meteoRepository.deleteByLatitudeAndLongitude(latitude, longitude);
    }

    public void save(Forecast forecast) {
        meteoRepository.save(mapper.toEntity(forecast));
    }
}
