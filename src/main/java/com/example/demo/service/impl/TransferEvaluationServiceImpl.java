// package com.example.demo.service.impl;

// import com.example.demo.entity.*;
// import com.example.demo.repository.*;
// import com.example.demo.service.TransferEvaluationService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.demo.exception.ResourceNotFoundException;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// @Service
// public class TransferEvaluationServiceImpl implements TransferEvaluationService {

//     // REQUIRED FIELD NAMES
//     @Autowired
//     private TransferEvaluationResultRepo resultRepo;

//     @Autowired
//     private CourseRepo courseRepo;

//     @Autowired
//     private CourseContentTopicRepo topicRepo;

//     @Autowired
//     private TransferRuleRepo ruleRepo;

//     @Override
//     public TransferEvaluationResult evaluateTransfer(Long sourceCourseId, Long targetCourseId) {

//         Course source = courseRepo.findById(sourceCourseId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

//         Course target = courseRepo.findById(targetCourseId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

//         if (!source.getActive() || !target.getActive()) {
//             throw new ResourceNotFoundException("Course is not active");
//         }

//         List<CourseContentTopic> sourceTopics = topicRepo.findByCourseId(sourceCourseId);
//         List<CourseContentTopic> targetTopics = topicRepo.findByCourseId(targetCourseId);

//         TransferEvaluationResult result = new TransferEvaluationResult();
//         result.setSourceCourse(source);
//         result.setTargetCourse(target);

//         List<TransferRule> rules =
//                 ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
//                         source.getUniversity().getId(),
//                         target.getUniversity().getId()
//                 );

//         if (rules.isEmpty()) {
//             result.setIsEligibleForTransfer(false);
//             result.setNotes("No active transfer rule");
//             return resultRepo.save(result);
//         }

//         TransferRule rule = rules.get(0);

//         Map<String, Double> targetMap = targetTopics.stream()
//                 .collect(Collectors.toMap(
//                         t -> t.getTopicName().toLowerCase(),
//                         CourseContentTopic::getWeightPercentage
//                 ));

//         double overlap = 0.0;
//         for (CourseContentTopic s : sourceTopics) {
//             Double targetWeight = targetMap.get(s.getTopicName().toLowerCase());
//             if (targetWeight != null) {
//                 overlap += Math.min(s.getWeightPercentage(), targetWeight);
//             }
//         }

//         int creditDiff = Math.abs(
//                 source.getCreditHours() - target.getCreditHours()
//         );

//         boolean eligible =
//                 overlap >= rule.getMinimumOverlapPercentage()
//                         && creditDiff <= rule.getCreditHourTolerance();

//         result.setOverlapPercentage(overlap);
//         result.setCreditHourDifference(creditDiff);
//         result.setIsEligibleForTransfer(eligible);

//         result.setNotes(
//                  eligible ? "Eligible" : "Rule conditions not satisfied"
//         );

//         return resultRepo.save(result);
//     }

//     @Override
//     public TransferEvaluationResult getEvaluationById(Long id) {
//         return resultRepo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("not found"));
//     }

//     @Override
//     public List<TransferEvaluationResult> getEvaluationsForCourse(Long courseId) {
//         return resultRepo.findBySourceCourseId(courseId);

//     }
// }

package com.example.app.service.impl;

import com.example.app.entity.*;
import com.example.app.repository.*;
import com.example.app.service.TransferEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransferEvaluationServiceImpl implements TransferEvaluationService {

    // REQUIRED FIELD NAMES
    @Autowired
    private TransferEvaluationResultRepository resultRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private CourseContentTopicRepository topicRepo;

    @Autowired
    private TransferRuleRepository ruleRepo;

    @Override
    public TransferEvaluationResult evaluateTransfer(Long sourceCourseId, Long targetCourseId) {

        Course source = courseRepo.findById(sourceCourseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Course target = courseRepo.findById(targetCourseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!source.getActive() || !target.getActive()) {
            throw new RuntimeException("Course is not active");
        }

        List<CourseContentTopic> sourceTopics = topicRepo.findByCourseId(sourceCourseId);
        List<CourseContentTopic> targetTopics = topicRepo.findByCourseId(targetCourseId);

        TransferEvaluationResult result = new TransferEvaluationResult();
        result.setSourceCourse(source);
        result.setTargetCourse(target);
        result.setEvaluatedAt(LocalDateTime.now());

        List<TransferRule> rules =
                ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(
                        source.getUniversity().getId(),
                        target.getUniversity().getId()
                );

        if (rules.isEmpty()) {
            result.setIsEligibleForTransfer(false);
            result.setNotes("No active transfer rule");
            return resultRepo.save(result);
        }

        TransferRule rule = rules.get(0);

        Map<String, Double> targetMap = targetTopics.stream()
                .collect(Collectors.toMap(
                        t -> t.getTopicName().toLowerCase(),
                        CourseContentTopic::getWeightPercentage
                ));

        double overlap = 0.0;
        for (CourseContentTopic s : sourceTopics) {
            Double targetWeight = targetMap.get(s.getTopicName().toLowerCase());
            if (targetWeight != null) {
                overlap += Math.min(s.getWeightPercentage(), targetWeight);
            }
        }

        int creditDiff = Math.abs(
                source.getCreditHours() - target.getCreditHours()
        );

        boolean eligible =
                overlap >= rule.getMinimumOverlapPercentage()
                        && creditDiff <= rule.getCreditHourTolerance();

        result.setOverlapPercentage(overlap);
        result.setCreditHourDifference(creditDiff);
        result.setIsEligibleForTransfer(eligible);

        result.setNotes(
                eligible ? "Eligible" : "No active rule satisfied"
        );

        return resultRepo.save(result);
    }

    @Override
    public TransferEvaluationResult getEvaluationById(Long id) {
        return resultRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public List<TransferEvaluationResult> getEvaluationsForCourse(Long courseId) {
        return resultRepo.findByCourseId(courseId);
    }
}
