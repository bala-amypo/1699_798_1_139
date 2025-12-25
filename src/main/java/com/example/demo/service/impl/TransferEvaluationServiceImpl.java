package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.TransferEvaluationService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransferEvaluationServiceImpl implements TransferEvaluationService {

    private CourseRepository courseRepo;
    private CourseContentTopicRepository topicRepo;
    private TransferRuleRepository ruleRepo;
    private TransferEvaluationResultRepository resultRepo;

    @Override
    public TransferEvaluationResult evaluateTransfer(Long srcId, Long tgtId) {

        Course src = courseRepo.findById(srcId)
                .orElseThrow(() -> new RuntimeException("not found"));
        Course tgt = courseRepo.findById(tgtId)
                .orElseThrow(() -> new RuntimeException("not found"));

        if (!src.isActive() || !tgt.isActive()) {
            throw new IllegalArgumentException("active");
        }

        List<CourseContentTopic> srcTopics = topicRepo.findByCourseId(srcId);
        List<CourseContentTopic> tgtTopics = topicRepo.findByCourseId(tgtId);

        double overlap = 0;
        for (CourseContentTopic s : srcTopics) {
            for (CourseContentTopic t : tgtTopics) {
                if (s.getTopicName().equalsIgnoreCase(t.getTopicName())) {
                    overlap += Math.min(
                            s.getWeightPercentage(),
                            t.getWeightPercentage()
                    );
                }
            }
        }

        TransferEvaluationResult result = new TransferEvaluationResult();
        result.setSourceCourseId(srcId);
        result.setTargetCourseId(tgtId);
        result.setOverlapPercentage(overlap);

        List<TransferRule> rules = ruleRepo
                .findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
                        src.getUniversity().getId(),
                        tgt.getUniversity().getId()
                );

        boolean eligible = false;

        for (TransferRule r : rules) {
            int tolerance = r.getCreditHourTolerance() == null ? 0 : r.getCreditHourTolerance();
            if (overlap >= r.getMinimumOverlapPercentage()
                    && Math.abs(src.getCreditHours() - tgt.getCreditHours()) <= tolerance) {
                eligible = true;
                break;
            }
        }

        if (rules.isEmpty()) {
            result.setNotes("No active transfer rule");
        } else if (!eligible) {
            result.setNotes("No active rule satisfied");
        }

        result.setIsEligibleForTransfer(eligible);
        return resultRepo.save(result);
    }

    @Override
    public TransferEvaluationResult getEvaluationById(Long id) {
        return resultRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public List<TransferEvaluationResult> getEvaluationsForCourse(Long courseId) {
        return resultRepo.findBySourceCourseId(courseId);
    }
}
