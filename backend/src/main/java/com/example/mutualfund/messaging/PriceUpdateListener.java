package com.example.mutualfund.messaging;

import com.example.mutualfund.model.MutualFund;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

@Component
public class PriceUpdateListener implements MessageListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "fund-prices")
    public void onKafkaMessage(ConsumerRecord<?, MutualFund> record) {
        publisher.publishEvent(record.value());
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            MutualFund fund = objectMapper.readValue(message.getBody(), MutualFund.class);
            publisher.publishEvent(fund);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
