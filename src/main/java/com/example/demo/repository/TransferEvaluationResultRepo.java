package com.example.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.app.entity.TransferEvaluationResult;

public interface TransferEvaluationResultRepo
        extends JpaRepository<TransferEvaluationResult, Long> {

    List<TransferEvaluationResult> findByCourseId(Long courseId);
}
