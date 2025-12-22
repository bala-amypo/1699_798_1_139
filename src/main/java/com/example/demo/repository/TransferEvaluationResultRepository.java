package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.TransferEvaluationResult;

public interface TransferEvaluationResultRepository
        extends JpaRepository<TransferEvaluationResult, Long> {

    List<TransferEvaluationResult> findByCourseId(Long courseId);

    List<TransferEvaluationResult> findBySourceCourseIdOrTargetCourseId(Long courseId, Long courseId2);
}
