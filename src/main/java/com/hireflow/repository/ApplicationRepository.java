package com.hireflow.repository;

import com.hireflow.model.Application;
import com.hireflow.model.Job;
import com.hireflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCandidate(User candidate);

    List<Application> findByJob(Job job);

    Optional<Application> findByCandidateAndJob(User candidate, Job job);

    boolean existsByCandidateAndJob(User candidate, Job job);
}
