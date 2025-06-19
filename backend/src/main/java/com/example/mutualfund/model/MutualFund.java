package com.example.mutualfund.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MutualFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fundName;
    private double currentPrice;
    private LocalDateTime lastUpdated;

    public MutualFund() {}

    public MutualFund(String fundName, double currentPrice, LocalDateTime lastUpdated) {
        this.fundName = fundName;
        this.currentPrice = currentPrice;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
