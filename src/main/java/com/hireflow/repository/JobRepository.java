package com.hireflow.repository;

import com.hireflow.enums.JobStatus;
import com.hireflow.model.Job;
import com.hireflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCompany(User company);

    List<Job> findByStatus(JobStatus status);

    List<Job> findByCompanyAndStatus(User company, JobStatus status);

    long countByCompany(User company);
}
