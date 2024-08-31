//package com.garage.autoservice.entity;
//
//import jakarta.persistence.*;
//
//import jakarta.persistence.*;
//import java.time.LocalDate;
//import java.util.List;
//
///**
// * Сущность, представляющая запланированную ремонтную работу.
// */
//@Entity
//@Table(name = "scheduled_repair_jobs")
//public class ScheduledRepairJob {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String vin;
//
//    @Column(nullable = false)
//    private String jobName;
//
//    @Column(nullable = false)
//    private LocalDate scheduledDate;
//
//    @Column(nullable = false)
//    private boolean partsAvailable;
//
//    @OneToMany
//    @JoinColumn(name = "scheduled_repair_job_id")
//    private List<Part> requiredParts;
//
//    // Getters and Setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getVin() {
//        return vin;
//    }
//
//    public void setVin(String vin) {
//        this.vin = vin;
//    }
//
//    public String getJobName() {
//        return jobName;
//    }
//
//    public void setJobName(String jobName) {
//        this.jobName = jobName;
//    }
//
//    public LocalDate getScheduledDate() {
//        return scheduledDate;
//    }
//
//    public void setScheduledDate(LocalDate scheduledDate) {
//        this.scheduledDate = scheduledDate;
//    }
//
//    public boolean isPartsAvailable() {
//        return partsAvailable;
//    }
//
//    public void setPartsAvailable(boolean partsAvailable) {
//        this.partsAvailable = partsAvailable;
//    }
//
//    public List<Part> getRequiredParts() {
//        return requiredParts;
//    }
//
//    public void setRequiredParts(List<Part> requiredParts) {
//        this.requiredParts = requiredParts;
//    }
//}
