package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.app.entity.TransferRule;

public interface TransferRuleRepo
        extends JpaRepository<TransferRule, Long> {

    List<TransferRule>
    findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
            Long sourceId, Long targetId);
}
