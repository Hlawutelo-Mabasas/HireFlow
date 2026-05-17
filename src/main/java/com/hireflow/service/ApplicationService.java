package com.hireflow.service;

import com.hireflow.enums.ApplicationStatus;
import com.hireflow.model.Application;
import com.hireflow.model.Job;
import com.hireflow.model.User;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.JobRepository;
import com.hireflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    public Application applyForJob(Long candidateId, Long jobId, String coverLetter) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        if (applicationRepository.existsByCandidateAndJob(candidate, job)) {
            throw new RuntimeException("Already applied");
        }
        Application application = Application.builder()
                .candidate(candidate)
                .job(job)
                .coverLetter(coverLetter)
                .status(ApplicationStatus.APPLIED)
                .build();
        return applicationRepository.save(application);
    }
    public Application updateStatus(Long applicationId, ApplicationStatus newStatus) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(newStatus);
        return applicationRepository.save(application);
    }
    public List<Application> getApplicationsByJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return applicationRepository.findByJob(job);
    }
    public List<Application> getApplicationsByCandidate(Long candidateId) {
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        return applicationRepository.findByCandidate(candidate);
    }
    public Application getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
