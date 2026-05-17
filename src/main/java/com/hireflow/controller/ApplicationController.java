package com.hireflow.controller;

import com.hireflow.enums.ApplicationStatus;
import com.hireflow.model.Application;
import com.hireflow.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody Map<String, String> body) {
        try {
            Long candidateId = Long.parseLong(body.get("candidateId"));
            Long jobId = Long.parseLong(body.get("jobId"));
            String coverLetter = body.getOrDefault("coverLetter", "");
            Application application = applicationService.applyForJob(candidateId, jobId, coverLetter);
            return ResponseEntity.ok(Map.of("applicationId", application.getId(), "status", application.getStatus()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            ApplicationStatus newStatus = ApplicationStatus.valueOf(body.get("status").toUpperCase());
            Application application = applicationService.updateStatus(id, newStatus);
            return ResponseEntity.ok(Map.of("applicationId", application.getId(), "newStatus", application.getStatus()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJob(jobId));
    }
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<Application>> getByCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok(applicationService.getApplicationsByCandidate(candidateId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Application>> getAll() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }
}
