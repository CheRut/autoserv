package com.garage.autoservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO для передачи данных о ремонтных заданиях.
 * Используется для передачи данных между клиентом и сервером при создании или обновлении ремонтных работ.
 */
@Data
public class RepairJobRequest {

    /**
     * Название ремонтной работы.
     * Этот параметр не может быть пустым.
     */
    @NotEmpty(message = "Название ремонтной работы не может быть пустым")
    private String jobName;

    /**
     * Интервал пробега (в километрах) для выполнения ремонтной работы.
     * Этот параметр должен быть положительным числом.
     */
    @Positive(message = "Интервал пробега должен быть положительным числом")
    private long intervalInMileage;

    /**
     * Интервал моточасов для выполнения ремонтной работы.
     * Этот параметр должен быть положительным числом.
     */
    @Positive(message = "Интервал моточасов должен быть положительным числом")
    private long intervalInHours;

    /**
     * Интервал дней для выполнения ремонтной работы.
     * Этот параметр должен быть положительным числом.
     */
    @Positive(message = "Интервал дней должен быть положительным числом")
    private long intervalInDays;

    /**
     * Список требуемых запчастей для выполнения ремонтной работы.
     * Этот параметр не может быть null и должен содержать хотя бы одну запчасть.
     */
    @NotNull(message = "Список требуемых запчастей не может быть null")
    @NotEmpty(message = "Список требуемых запчастей не может быть пустым")
    private List<PartRequest> requiredParts;
    /**
     * Номер накладной-заказа,на тот случай если работы проводились не на территории предприятия
     * Этот параметр не может быть null и должен содержать хотя бы одну запчасть.
     */
    private String orderNumber;
    /**
     * Пробег автомобиля на момент последнего выполнения данной ремонтной работы.
     * Этот параметр должен быть положительным числом.
     */
    @Positive(message = "Последний пробег должен быть положительным числом")
    private long lastMileage;

    /**
     * Дата последнего выполнения данной ремонтной работы.
     * Этот параметр не может быть null.
     */
    @NotNull(message = "Дата последнего выполнения не может быть null")
    private LocalDate lastJobDate;

    /**
     * показания моточасов при выполнении последней ремонтной работы.
     * Этот параметр должен быть положительным числом.
     */
    @Positive(message = "Интервал моточасов должен быть положительным числом")
    private long lastHours;
    /**
     * Серийный номер автомобиля, к которому относится данная работа.
     * Этот параметр не может быть пустым.
     */
    @NotEmpty(message = "Серийный номер не может быть пустым")
    private String serialNumber;

    @NotEmpty(message = "Тип работ не может быть пустым")
    private String jobsType;



}
