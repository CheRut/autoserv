package com.garage.autoservice.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность для представления ремонтных работ.
 */
@Entity
public class RepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;
    private Long intervalInMileage;
    private Long intervalInHours;
    private Long intervalInDays;
    private Long lastMileage;
    private LocalDate lastJobDate;

    @ManyToMany
    @JoinTable(
            name = "repairjob_parts",
            joinColumns = @JoinColumn(name = "repairjob_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id")
    )
    private List<Part> requiredParts = new ArrayList<>();

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Long getIntervalInMileage() {
        return intervalInMileage;
    }

    public void setIntervalInMileage(Long intervalInMileage) {
        this.intervalInMileage = intervalInMileage;
    }

    public Long getIntervalInHours() {
        return intervalInHours;
    }

    public void setIntervalInHours(Long intervalInHours) {
        this.intervalInHours = intervalInHours;
    }

    public Long getIntervalInDays() {
        return intervalInDays;
    }

    public void setIntervalInDays(Long intervalInDays) {
        this.intervalInDays = intervalInDays;
    }

    public Long getLastMileage() {
        return lastMileage;
    }

    public void setLastMileage(Long lastMileage) {
        this.lastMileage = lastMileage;
    }

    public LocalDate getLastJobDate() {
        return lastJobDate;
    }

    public void setLastJobDate(LocalDate lastJobDate) {
        this.lastJobDate = lastJobDate;
    }

    public List<Part> getRequiredParts() {
        return requiredParts;
    }

    public void setRequiredParts(List<Part> requiredParts) {
        this.requiredParts = requiredParts;
    }
}
