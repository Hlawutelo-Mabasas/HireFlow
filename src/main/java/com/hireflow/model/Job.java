package com.hireflow.model;

import com.hireflow.enums.JobStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String location;
    private String salary;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;
    @Column(name = "posted_at")
    private LocalDateTime postedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private User company;
    @PrePersist
    public void prePersist() {
        this.postedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = JobStatus.OPEN;
        }
    }
}
