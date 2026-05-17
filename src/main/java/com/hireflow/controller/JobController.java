package com.hireflow.controller;

import com.hireflow.model.Job;
import com.hireflow.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    @PostMapping("/post")
    public ResponseEntity<?> postJob(@RequestParam Long companyId, @RequestBody Map<String, String> body) {
        try {
            Job job = jobService.postJob(companyId, body.get("title"), body.get("description"), body.get("location"), body.get("salary"));
            return ResponseEntity.ok(Map.of("jobId", job.getId(), "title", job.getTitle()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/open")
    public ResponseEntity<List<Job>> getAllOpenJobs() {
        return ResponseEntity.ok(jobService.getAllOpenJobs());
    }
    @GetMapping("/all")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Job>> getJobsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(jobService.getJobsByCompany(companyId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(jobService.getJobById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{id}/close")
    public ResponseEntity<?> closeJob(@PathVariable Long id) {
        try {
            Job job = jobService.closeJob(id);
            return ResponseEntity.ok(Map.of("jobId", job.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @PatchMapping("/{id}/reopen")
    public ResponseEntity<?> reopenJob(@PathVariable Long id) {
        try {
            Job job = jobService.reopenJob(id);
            return ResponseEntity.ok(Map.of("jobId", job.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok(Map.of("message", "Job deleted successfully"));
    }
}
