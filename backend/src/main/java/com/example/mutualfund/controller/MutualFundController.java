package com.example.mutualfund.controller;

import com.example.mutualfund.model.MutualFund;
import com.example.mutualfund.model.FundHistory;
import com.example.mutualfund.repository.MutualFundRepository;
import com.example.mutualfund.repository.FundHistoryRepository;
import com.example.mutualfund.service.MutualFundService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/funds")
public class MutualFundController {

    @Autowired
    private MutualFundRepository mutualFundRepository;

    @Autowired
    private FundHistoryRepository fundHistoryRepository;

    @Autowired
    private MutualFundService mutualFundService;

    @GetMapping
    public List<MutualFund> getAll() {
        return mutualFundRepository.findAll();
    }

    @PostMapping
    public MutualFund addFund(@RequestBody MutualFund fund) {
        return mutualFundService.addFund(fund);
    }

    @PutMapping("/{id}/price")
    public MutualFund updateFund(@PathVariable Long id, @RequestBody MutualFund request) {
        return mutualFundService.updateFundPrice(id, request.getCurrentPrice());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFund(@PathVariable Long id) {
        mutualFundRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<Void> deleteNullFunds() {
        List<MutualFund> nullFunds = new ArrayList<>();
        for (MutualFund fund : mutualFundRepository.findAll()) {
            boolean nameBlank = fund.getFundName() == null || fund.getFundName().trim().isEmpty();
            boolean priceZero = fund.getCurrentPrice() == 0.0;
            if (nameBlank || priceZero) {
                nullFunds.add(fund);
            }
        }
        mutualFundRepository.deleteAll(nullFunds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/history")
    public List<FundHistory> getFundHistory(@PathVariable Long id) {
        return fundHistoryRepository.findByFundIdOrderByTimestampDesc(id);
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=funds.csv");

        List<MutualFund> funds = mutualFundRepository.findAll();
        PrintWriter writer = response.getWriter();
        writer.println("ID,Fund Name,Price,Last Updated");

        for (MutualFund fund : funds) {
            writer.printf("%d,%s,%.2f,%s%n",
                fund.getId(), fund.getFundName(), fund.getCurrentPrice(), fund.getLastUpdated());
        }

        writer.flush();
        writer.close();
    }

    // 🆕 Reindex fund IDs (development use only)
    @GetMapping("/reindex")
    public ResponseEntity<String> reindexIds() {
        List<MutualFund> allFunds = mutualFundRepository.findAll();
        mutualFundRepository.deleteAll();

        List<MutualFund> reordered = new ArrayList<>();
        for (MutualFund f : allFunds) {
            MutualFund newFund = new MutualFund();
            newFund.setFundName(f.getFundName());
            newFund.setCurrentPrice(f.getCurrentPrice());
            newFund.setLastUpdated(f.getLastUpdated());
            reordered.add(newFund);
        }
        mutualFundRepository.saveAll(reordered);
        return ResponseEntity.ok("IDs reindexed successfully");
    }
}
