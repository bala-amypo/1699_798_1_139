package com.example.demo.service.impl;

import com.example.demo.entity.TransferRule;
import com.example.demo.entity.University;
import com.example.demo.repository.TransferRuleRepo;
import com.example.demo.repository.UniversityRepo;
import com.example.demo.service.TransferRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferRuleServiceImpl implements TransferRuleService {

    @Autowired
    private TransferRuleRepo ruleRepo;

    @Autowired
    private UniversityRepo universityRepo;

@Override
public TransferRule createRule(TransferRule rule) {

    if (rule.getSourceUniversity() == null || rule.getTargetUniversity() == null) {
        throw new RuntimeException("Source and Target universities are required");
    }

    rule.setId(null);

    University source = universityRepo.findById(
            rule.getSourceUniversity().getId()
    ).orElseThrow(() -> new RuntimeException("Source university not found"));

    University target = universityRepo.findById(
            rule.getTargetUniversity().getId()
    ).orElseThrow(() -> new RuntimeException("Target university not found"));

    rule.setSourceUniversity(source);
    rule.setTargetUniversity(target);
    rule.setActive(true);

    return ruleRepo.save(rule);
}

    }

    @Override
    public TransferRule updateRule(Long id, TransferRule rule) {

        TransferRule existing = ruleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer rule not found"));

        University source = universityRepo.findById(
                rule.getSourceUniversity().getId()
        ).orElseThrow(() -> new RuntimeException("Source university not found"));

        University target = universityRepo.findById(
                rule.getTargetUniversity().getId()
        ).orElseThrow(() -> new RuntimeException("Target university not found"));

        existing.setSourceUniversity(source);
        existing.setTargetUniversity(target);
        existing.setMinimumOverlapPercentage(rule.getMinimumOverlapPercentage());
        existing.setCreditHourTolerance(rule.getCreditHourTolerance());
        existing.setActive(rule.getActive());

        return ruleRepo.save(existing);
    }

    @Override
    public TransferRule getRuleById(Long id) {
        return ruleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer rule not found"));
    }

    @Override
    public List<TransferRule> getRulesForUniversities(Long sourceId, Long targetId) {
        return ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
                sourceId, targetId
        );
    }

    @Override
    public void deactivateRule(Long id) {
        TransferRule rule = getRuleById(id);
        rule.setActive(false);
        ruleRepo.save(rule);
    }
}
