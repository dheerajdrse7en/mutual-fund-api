package com.example.mutualfund.service;

import com.example.mutualfund.model.FundHistory;
import com.example.mutualfund.model.MutualFund;
import com.example.mutualfund.repository.FundHistoryRepository;
import com.example.mutualfund.repository.MutualFundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MutualFundService {

    @Autowired
    private MutualFundRepository mutualFundRepository;

    @Autowired
    private FundHistoryRepository fundHistoryRepository;

    public MutualFund addFund(MutualFund fund) {
        fund.setLastUpdated(LocalDateTime.now());
        MutualFund saved = mutualFundRepository.save(fund);

        FundHistory history = new FundHistory();
        history.setFund(saved);
        history.setPrice(saved.getCurrentPrice());
        history.setTimestamp(LocalDateTime.now());
        fundHistoryRepository.save(history);

        return saved;
    }

    public MutualFund updateFundPrice(Long id, Double newPrice) {
        MutualFund fund = mutualFundRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fund not found"));
        fund.setCurrentPrice(newPrice);
        fund.setLastUpdated(LocalDateTime.now());
        mutualFundRepository.save(fund);

        FundHistory history = new FundHistory();
        history.setFund(fund);
        history.setPrice(newPrice);
        history.setTimestamp(LocalDateTime.now());
        fundHistoryRepository.save(history);

        return fund;
    }
}
