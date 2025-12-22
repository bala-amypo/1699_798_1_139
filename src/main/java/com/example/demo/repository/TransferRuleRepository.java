package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.entity.TransferRule;

public interface TransferRuleRepository
        extends JpaRepository<TransferRule, Long> {

    List<TransferRule>
    findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
            Long sourceId, Long targetId);
}
