// package com.example.demo.service;

// import com.example.demo.entity.TransferRule;
// import java.util.List;

// public interface TransferRuleService {
//     TransferRule createRule(TransferRule rule);
//     TransferRule updateRule(Long id, TransferRule rule);
//     TransferRule getRuleById(Long id); // Throw if not found
//     List<TransferRule> getRulesForUniversities(Long sourceId, Long targetId); // only active
//     void deactivateRule(Long id); // mark as inactive
// }
package com.example.demo.service;

import com.example.demo.entity.TransferRule;

import java.util.List;

public interface TransferRuleService {

    TransferRule createRule(TransferRule rule);

    TransferRule updateRule(Long id, TransferRule rule);

    TransferRule getRuleById(Long id);

    void deactivateRule(Long id);

    List<TransferRule> getRulesForUniversities(Long sourceId, Long targetId);
}
