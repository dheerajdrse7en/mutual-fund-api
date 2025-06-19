
package com.example.mutualfund.messaging;

import com.example.mutualfund.model.MutualFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(MutualFund fund) {
        kafkaTemplate.send("fund-prices", fund);
    }
}
