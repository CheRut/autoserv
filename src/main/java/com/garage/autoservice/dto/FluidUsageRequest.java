package com.garage.autoservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для запроса создания или обновления информации о расходе рабочей жидкости.
 * Используется для передачи данных между клиентом и сервером.
 */
@Data
public class FluidUsageRequest {

    /**
     * Серийный номер автомобиля, к которому относится данная запись о расходе жидкости.
     * Этот параметр не может быть null.
     */
    @NotNull(message = "Serial number cannot be null")
    private String serialNumber;

    /**
     * Тип рабочей жидкости (например, масло, антифриз).
     * Этот параметр не может быть null.
     */
    @NotNull(message = "Fluid type cannot be null")
    private String fluidType;

    /**
     * Объем использованной жидкости в литрах.
     * Этот параметр должен быть положительным числом.
     */
    @Positive(message = "Fluid volume must be positive")
    private Double fluidVolume;

    /**
     * Бренд рабочей жидкости. Этот параметр может быть null.
     */
    private String fluidBrand;

    /**
     * Пробег автомобиля на момент замены или долива жидкости.
     * Этот параметр должен быть положительным числом.
     */
    @Positive(message = "Mileage must be positive")
    private Long mileage;

    /**
     * Дата проведения замены или долива жидкости.
     * Этот параметр не может быть null.
     */
    @NotNull(message = "Date cannot be null")
    private LocalDate date;
}
