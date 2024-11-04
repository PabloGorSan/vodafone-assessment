package com.example.vodafoneassessment.business;

import com.example.vodafoneassessment.controller.ForecastDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, ForecastDto> kafkaTemplate;

    public void sendMessage(String topic, ForecastDto message) {
        kafkaTemplate.send(topic, message);
    }

}
