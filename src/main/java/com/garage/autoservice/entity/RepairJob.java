package com.garage.autoservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность для представления ремонтных работ.
 * Включает информацию о запланированных интервалах выполнения работ и связанных запчастях.
 */
@Entity
public class RepairJob {

    private static final Logger logger = LoggerFactory.getLogger(RepairJob.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название работы не может быть пустым")
    private String jobName;

    @Positive(message = "Интервал по пробегу должен быть положительным числом")
    private Long intervalInMileage;

    @Min(value = 0, message = "Интервал по моточасам должен быть не меньше 0")
    private Long intervalInHours;

    @Positive(message = "Интервал по дням должен быть положительным числом")
    private Long intervalInDays;

    @Positive(message = "Последний пробег должен быть положительным числом")
    private Long lastMileage;
    @NotNull(message = "Дата последнего проведения работы не может быть null")
    private LocalDate lastJobDate;

    @Min(value = 0, message = "Интервал по моточасам должен быть не меньше 0")
    private Long lastHours;
    @NotBlank(message = "Серийный номер не может быть пустым")
    private String serialNumber;

    @ManyToMany
    @JoinTable(
            name = "repairjob_parts",
            joinColumns = @JoinColumn(name = "repairjob_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id")
    )
    private List<Part> requiredParts = new ArrayList<>();

    public Long getId() {
        logger.debug("Получение ID: {}", id);
        return id;
    }

    public void setId(Long id) {
        logger.debug("Установка ID: {}", id);
        this.id = id;
    }

    public Long getLastHours() {
        return lastHours;
    }

    public void setLastHours(Long lastHours) {
        this.lastHours = lastHours;
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
