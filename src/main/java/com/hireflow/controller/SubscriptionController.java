package com.hireflow.controller;

import com.hireflow.model.Subscription;
import com.hireflow.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getSubscription(@PathVariable Long companyId) {
        try {
            Subscription sub = subscriptionService.getSubscription(companyId);
            return ResponseEntity.ok(sub);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/company/{companyId}/upgrade")
    public ResponseEntity<?> upgradeToPro(@PathVariable Long companyId) {
        try {
            Subscription sub = subscriptionService.upgradeToPro(companyId);
            return ResponseEntity.ok(Map.of("plan", sub.getPlan(), "endDate", sub.getEndDate().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @PatchMapping("/company/{companyId}/downgrade")
    public ResponseEntity<?> downgradeToFree(@PathVariable Long companyId) {
        try {
            Subscription sub = subscriptionService.downgradeToFree(companyId);
            return ResponseEntity.ok(Map.of("plan", sub.getPlan()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }
}
