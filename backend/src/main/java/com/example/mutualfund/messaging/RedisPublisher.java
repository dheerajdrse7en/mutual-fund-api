
package com.example.mutualfund.messaging;

import com.example.mutualfund.model.MutualFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisPublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(MutualFund fund) {
        redisTemplate.convertAndSend("fundPriceTopic", fund);
    }
}
