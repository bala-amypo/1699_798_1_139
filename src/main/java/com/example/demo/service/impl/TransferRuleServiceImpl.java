package com.example.demo.service.impl;

import com.example.demo.entity.TransferRule;
import com.example.demo.repository.TransferRuleRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.TransferRuleService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class TransferRuleServiceImpl implements TransferRuleService {

    @Autowired
    private TransferRuleRepository repo;
    @Autowired
    private UniversityRepository univRepo;

    @Override
    public TransferRule createRule(TransferRule rule) {

        if (rule.getMinimumOverlapPercentage() < 0 || rule.getMinimumOverlapPercentage() > 100) {
            throw new IllegalArgumentException("0-100");
        }

        if (rule.getCreditHourTolerance() != null && rule.getCreditHourTolerance() < 0) {
            throw new IllegalArgumentException(">= 0");
        }

        univRepo.findById(rule.getSourceUniversity().getId())
                .orElseThrow(() -> new RuntimeException("not found"));

        univRepo.findById(rule.getTargetUniversity().getId())
                .orElseThrow(() -> new RuntimeException("not found"));

        return repo.save(rule);
    }

    @Override
    public TransferRule updateRule(Long id, TransferRule rule) {
        TransferRule existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        return repo.save(existing);
    }

    @Override
    public TransferRule getRuleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public void deactivateRule(Long id) {
        TransferRule r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        r.setActive(false);
        repo.save(r);
    }

    @Override
    public List<TransferRule> getRulesForUniversities(Long sourceId, Long targetId) {
        return repo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(sourceId, targetId);
    }
}
