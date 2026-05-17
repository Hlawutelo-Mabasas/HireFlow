package com.hireflow.service;

import com.hireflow.enums.JobStatus;
import com.hireflow.model.Job;
import com.hireflow.model.User;
import com.hireflow.repository.JobRepository;
import com.hireflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;
    public Job postJob(Long companyId, String title, String description, String location, String salary) {
        User company = userRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        long currentJobCount = jobRepository.countByCompany(company);
        if (!subscriptionService.canPostJob(companyId, currentJobCount)) {
            throw new RuntimeException("Job posting limit reached. Upgrade to PRO plan.");
        }
        Job job = Job.builder()
                .title(title)
                .description(description)
                .location(location)
                .salary(salary)
                .status(JobStatus.OPEN)
                .company(company)
                .build();
        return jobRepository.save(job);
    }
    public List<Job> getAllOpenJobs() {
        return jobRepository.findByStatus(JobStatus.OPEN);
    }
    public List<Job> getJobsByCompany(Long companyId) {
        User company = userRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return jobRepository.findByCompany(company);
    }
    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }
    public Job closeJob(Long jobId) {
        Job job = getJobById(jobId);
        job.setStatus(JobStatus.CLOSED);
        return jobRepository.save(job);
    }
    public Job reopenJob(Long jobId) {
        Job job = getJobById(jobId);
        job.setStatus(JobStatus.OPEN);
        return jobRepository.save(job);
    }
    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
