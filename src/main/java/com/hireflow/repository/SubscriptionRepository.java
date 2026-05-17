package com.hireflow.repository;

import com.hireflow.model.Subscription;
import com.hireflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByCompany(User company);

    Optional<Subscription> findByCompanyId(Long companyId);
}
