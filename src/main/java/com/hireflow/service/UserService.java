package com.hireflow.service;

import com.hireflow.enums.Role;
import com.hireflow.enums.SubscriptionPlan;
import com.hireflow.model.Subscription;
import com.hireflow.model.User;
import com.hireflow.repository.SubscriptionRepository;
import com.hireflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;
    public User register(String name, String email, String rawPassword, Role role, String companyName) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .role(role)
                .companyName(companyName)
                .build();
        User savedUser = userRepository.save(user);
        if (role == Role.COMPANY) {
            Subscription subscription = Subscription.builder()
                    .company(savedUser)
                    .plan(SubscriptionPlan.FREE)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusMonths(1))
                    .active(true)
                    .build();
            subscriptionRepository.save(subscription);
        }
        return savedUser;
    }
    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<User> getAllCompanies() {
        return userRepository.findByRole(Role.COMPANY);
    }
    public List<User> getAllCandidates() {
        return userRepository.findByRole(Role.CANDIDATE);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
