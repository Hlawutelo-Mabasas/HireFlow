package com.hireflow.service;

import com.hireflow.enums.SubscriptionPlan;
import com.hireflow.model.Subscription;
import com.hireflow.repository.SubscriptionRepository;
import com.hireflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    public Subscription getSubscription(Long companyId) {
        return subscriptionRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new RuntimeException("No subscription found"));
    }
    public Subscription upgradeToPro(Long companyId) {
        Subscription subscription = getSubscription(companyId);
        subscription.setPlan(SubscriptionPlan.PRO);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(1));
        subscription.setActive(true);
        return subscriptionRepository.save(subscription);
    }
    public Subscription downgradeToFree(Long companyId) {
        Subscription subscription = getSubscription(companyId);
        subscription.setPlan(SubscriptionPlan.FREE);
        subscription.setEndDate(LocalDate.now().plusMonths(1));
        return subscriptionRepository.save(subscription);
    }
    public boolean canPostJob(Long companyId, long currentJobCount) {
        Subscription subscription = getSubscription(companyId);
        if (!subscription.isActive() || subscription.getEndDate().isBefore(LocalDate.now())) {
            return false;
        }
        if (subscription.getPlan() == SubscriptionPlan.PRO) {
            return true;
        }
        return currentJobCount < 3;
    }
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }
}
