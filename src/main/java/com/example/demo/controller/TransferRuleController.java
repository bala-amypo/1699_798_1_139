package com.example.demo.controller;

import com.example.demo.entity.TransferRule;
import com.example.demo.service.TransferRuleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfer-rules")
@Tag(name = "Transfer Rule API")
public class TransferRuleController {

    @Autowired
    private TransferRuleService service;

    @PostMapping
    public TransferRule create(@RequestBody TransferRule rule) {
        return service.createRule(rule);
    }

    @PutMapping("/{id}")
    public TransferRule update(@PathVariable Long id, @RequestBody TransferRule rule) {
        return service.updateRule(id, rule);
    }

    @GetMapping("/{id}")
    public TransferRule getById(@PathVariable Long id) {
        return service.getRuleById(id);
    }

    @GetMapping("/pair/{sourceId}/{targetId}")
    public List<TransferRule> getByUniversities(
            @PathVariable Long sourceId,
            @PathVariable Long targetId) {
        return service.getRulesForUniversities(sourceId, targetId);
    }

    @PutMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        service.deactivateRule(id);
    }
}
