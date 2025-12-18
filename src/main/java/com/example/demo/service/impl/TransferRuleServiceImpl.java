package com.example.demo.service.impl;

import com.example.demo.entity.TransferRule;
import com.example.demo.repository.TransferRuleRepo;
import com.example.demo.service.TransferRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferRuleServiceImpl implements TransferRuleService {

    @Autowired
    private TransferRuleRepo ruleRepo;

    @Override
    public TransferRule createRule(TransferRule rule) {
        rule.setActive(true);
        return ruleRepo.save(rule);
    }

    @Override
    public TransferRule updateRule(Long id, TransferRule rule) {
        return ruleRepo.findById(id).map(existing -> {
            existing.setSourceUniversity(rule.getSourceUniversity());
            existing.setTargetUniversity(rule.getTargetUniversity());
            existing.setMinimumOverlapPercentage(rule.getMinimumOverlapPercentage());
            return ruleRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Transfer rule not found with id " + id));
    }

    @Override
    public TransferRule getRuleById(Long id) {
        return ruleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer rule not found with id " + id));
    }

    @Override
    public List<TransferRule> getRulesForUniversities(Long sourceId, Long targetId) {
        return ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(sourceId, targetId);
    }

    @Override
    public void deactivateRule(Long id) {
        TransferRule rule = getRuleById(id);
        rule.setActive(false);
        ruleRepo.save(rule);
    }
}
