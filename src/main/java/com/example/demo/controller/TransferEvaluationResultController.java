package com.example.demo.controller;

import com.example.demo.entity.TransferEvaluationResult;
import com.example.demo.service.TransferEvaluationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfer-evaluations")
@Tag(name = "Transfer Evaluation API")
public class TransferEvaluationController {

    @Autowired
    private TransferEvaluationService service;

    @PostMapping("/evaluate/{sourceCourseId}/{targetCourseId}")
    public TransferEvaluationResult evaluate(
            @PathVariable Long sourceCourseId,
            @PathVariable Long targetCourseId) {
        return service.evaluateTransfer(sourceCourseId, targetCourseId);
    }

    @GetMapping("/{id}")
    public TransferEvaluationResult getById(@PathVariable Long id) {
        return service.getEvaluationById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<TransferEvaluationResult> getByCourse(@PathVariable Long courseId) {
        return service.getEvaluationsForCourse(courseId);
    }
}
