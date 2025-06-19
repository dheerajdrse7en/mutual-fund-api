package com.example.mutualfund.repository;

import com.example.mutualfund.model.FundHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundHistoryRepository extends JpaRepository<FundHistory, Long> {
    List<FundHistory> findByFundIdOrderByTimestampDesc(Long fundId);
}
