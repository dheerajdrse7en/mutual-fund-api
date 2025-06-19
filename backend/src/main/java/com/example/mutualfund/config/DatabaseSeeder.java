package com.example.mutualfund.config;

import com.example.mutualfund.model.MutualFund;
import com.example.mutualfund.repository.MutualFundRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatabaseSeeder {

    @Autowired
    private MutualFundRepository mutualFundRepository;

    @PostConstruct
    public void seedData() {
        if (mutualFundRepository.count() == 0) {
            MutualFund fund1 = new MutualFund();
            fund1.setFundName("Axis Bluechip");
            fund1.setCurrentPrice(150.5);
            fund1.setLastUpdated(LocalDateTime.now());

            MutualFund fund2 = new MutualFund();
            fund2.setFundName("Bluechip Growth");
            fund2.setCurrentPrice(142.75);
            fund2.setLastUpdated(LocalDateTime.now());

            MutualFund fund3 = new MutualFund();
            fund3.setFundName("ICICI Value");
            fund3.setCurrentPrice(123.90);
            fund3.setLastUpdated(LocalDateTime.now());

            mutualFundRepository.save(fund1);
            mutualFundRepository.save(fund2);
            mutualFundRepository.save(fund3);
        }
    }
}
