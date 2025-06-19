
package com.example.mutualfund.controller;

import com.example.mutualfund.model.MutualFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StompPriceController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handlePriceUpdate(MutualFund fund) {
        messagingTemplate.convertAndSend("/topic/price", fund);
    }
}
