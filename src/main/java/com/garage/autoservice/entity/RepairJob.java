package com.garage.autoservice.entity;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность для представления ремонтных работ.
 */
@Entity
public class RepairJob {

    private static final Logger logger = LoggerFactory.getLogger(RepairJob.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;
    private Long intervalInMileage;
    private Long intervalInHours;
    private Long intervalInDays;
    private Long lastMileage;
    private LocalDate lastJobDate;

    private String serialNumber;

    @ManyToMany
    @JoinTable(
            name = "repairjob_parts",
            joinColumns = @JoinColumn(name = "repairjob_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id")
    )
    private List<Part> requiredParts = new ArrayList<>();

    // Getters and setters with logging

    public Long getId() {
        logger.debug("Получение ID: {}", id);
        return id;
    }

    public void setId(Long id) {
        logger.debug("Установка ID: {}", id);
        this.id = id;
    }

    public String getJobName() {
        logger.debug("Получение названия работы: {}", jobName);
        return jobName;
    }

    public void setJobName(String jobName) {
        logger.debug("Установка названия работы: {}", jobName);
        this.jobName = jobName;
    }

    public Long getIntervalInMileage() {
        logger.debug("Получение интервала по пробегу: {}", intervalInMileage);
        return intervalInMileage;
    }

    public void setIntervalInMileage(Long intervalInMileage) {
        logger.debug("Установка интервала по пробегу: {}", intervalInMileage);
        this.intervalInMileage = intervalInMileage;
    }

    public Long getIntervalInHours() {
        logger.debug("Получение интервала по часам: {}", intervalInHours);
        return intervalInHours;
    }

    public void setIntervalInHours(Long intervalInHours) {
        logger.debug("Установка интервала по часам: {}", intervalInHours);
        this.intervalInHours = intervalInHours;
    }

    public Long getIntervalInDays() {
        logger.debug("Получение интервала по дням: {}", intervalInDays);
        return intervalInDays;
    }

    public void setIntervalInDays(Long intervalInDays) {
        logger.debug("Установка интервала по дням: {}", intervalInDays);
        this.intervalInDays = intervalInDays;
    }

    public Long getLastMileage() {
        logger.debug("Получение последнего пробега: {}", lastMileage);
        return lastMileage;
    }

    public void setLastMileage(Long lastMileage) {
        logger.debug("Установка последнего пробега: {}", lastMileage);
        this.lastMileage = lastMileage;
    }

    public LocalDate getLastJobDate() {
        logger.debug("Получение последней даты выполнения работы: {}", lastJobDate);
        return lastJobDate;
    }

    public void setLastJobDate(LocalDate lastJobDate) {
        logger.debug("Установка последней даты выполнения работы: {}", lastJobDate);
        this.lastJobDate = lastJobDate;
    }

    public List<Part> getRequiredParts() {
        logger.debug("Получение списка запчастей: {}", requiredParts);
        return requiredParts;
    }

    public void setRequiredParts(List<Part> requiredParts) {
        logger.debug("Установка списка запчастей: {}", requiredParts);
        this.requiredParts = requiredParts;
    }

    public String getSerialNumber() {
        logger.debug("Получение серийного номера: {}", serialNumber);
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        logger.debug("Установка серийного номера: {}", serialNumber);
        this.serialNumber = serialNumber;
    }
}
