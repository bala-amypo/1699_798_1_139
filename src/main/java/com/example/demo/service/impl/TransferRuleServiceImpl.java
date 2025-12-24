// package com.example.demo.service.impl;

// import com.example.demo.entity.TransferRule;
// import com.example.demo.entity.University;
// import com.example.demo.repository.TransferRuleRepository;
// import com.example.demo.repository.UniversityRepository;
// import com.example.demo.service.TransferRuleService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class TransferRuleServiceImpl implements TransferRuleService {

//     @Autowired
//     private TransferRuleRepository ruleRepo;

//     @Autowired
//     private UniversityRepository universityRepo;

//     @Override
//     public TransferRule createRule(TransferRule rule) {

//         // POST → id must be null
//         rule.setId(null);

//         University source = universityRepo.findById(
//                 rule.getSourceUniversity().getId()
//         ).orElseThrow(() -> new RuntimeException("Source university not found"));

//         University target = universityRepo.findById(
//                 rule.getTargetUniversity().getId()
//         ).orElseThrow(() -> new RuntimeException("Target university not found"));

//         rule.setSourceUniversity(source);
//         rule.setTargetUniversity(target);
//         rule.setActive(true);

//         return ruleRepo.save(rule);
//     }

//     @Override
//     public TransferRule updateRule(Long id, TransferRule rule) {

//         TransferRule existing = ruleRepo.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Transfer rule not found"));

//         University source = universityRepo.findById(
//                 rule.getSourceUniversity().getId()
//         ).orElseThrow(() -> new RuntimeException("Source university not found"));

//         University target = universityRepo.findById(
//                 rule.getTargetUniversity().getId()
//         ).orElseThrow(() -> new RuntimeException("Target university not found"));

//         existing.setSourceUniversity(source);
//         existing.setTargetUniversity(target);
//         existing.setMinimumOverlapPercentage(rule.getMinimumOverlapPercentage());
//         existing.setCreditHourTolerance(rule.getCreditHourTolerance());
//         existing.setActive(rule.getActive());

//         return ruleRepo.save(existing);
//     }

//     @Override
//     public TransferRule getRuleById(Long id) {
//         return ruleRepo.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Transfer rule not found"));
//     }

//     @Override
//     public List<TransferRule> getRulesForUniversities(Long sourceId, Long targetId) {
//         return ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
//                 sourceId, targetId
//         );
//     }

//     @Override
//     public void deactivateRule(Long id) {
//         TransferRule rule = getRuleById(id);
//         rule.setActive(false);
//         ruleRepo.save(rule);
//     }
// }
package com.example.demo.service.impl;

import com.example.demo.entity.TransferRule;
import com.example.demo.repository.TransferRuleRepository;
import com.example.demo.repository.UniversityRepository;
import com.example.demo.service.TransferRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferRuleServiceImpl implements TransferRuleService {

    private TransferRuleRepository repo;
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
