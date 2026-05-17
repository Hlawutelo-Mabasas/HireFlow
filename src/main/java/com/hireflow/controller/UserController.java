package com.hireflow.controller;

import com.hireflow.enums.Role;
import com.hireflow.model.User;
import com.hireflow.service.UserService;
import com.hireflow.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            String email = body.get("email");
            String password = body.get("password");
            Role role = Role.valueOf(body.getOrDefault("role", "CANDIDATE").toUpperCase());
            String companyName = body.get("companyName");
            User user = userService.register(name, email, password, role, companyName);
            String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().toString());
            return ResponseEntity.ok(Map.of(
                "token", token,
                "userId", user.getId(),
                "role", user.getRole(),
                "name", user.getName()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");
            User user = userService.login(email, password);
            String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().toString());
            return ResponseEntity.ok(Map.of(
                "token", token,
                "userId", user.getId(),
                "role", user.getRole(),
                "name", user.getName(),
                "email", user.getEmail()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/companies")
    public ResponseEntity<List<User>> getAllCompanies() {
        return ResponseEntity.ok(userService.getAllCompanies());
    }
    @GetMapping("/candidates")
    public ResponseEntity<List<User>> getAllCandidates() {
        return ResponseEntity.ok(userService.getAllCandidates());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}
