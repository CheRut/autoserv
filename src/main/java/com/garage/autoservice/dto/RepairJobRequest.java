package com.garage.autoservice.dto;

import com.garage.autoservice.entity.Part;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO для передачи данных о ремонтных заданиях.
 */
@Data
public class RepairJobRequest {

    @NotEmpty(message = "Название ремонтной работы не может быть пустым")
    private String jobName;

    @Positive(message = "Интервал пробега должен быть положительным числом")
    private long intervalInMileage;

    @Positive(message = "Интервал моточасов должен быть положительным числом")
    private long intervalInHours;

    @Positive(message = "Интервал дней должен быть положительным числом")
    private long intervalInDays;

    @NotNull(message = "Список требуемых запчастей не может быть null")
    private List<PartRequest> requiredParts;

    @Positive(message = "Последний пробег должен быть положительным числом")
    private long lastMileage;

    @NotNull(message = "Дата последнего выполнения не может быть null")
    private LocalDate lastJobDate;
}
