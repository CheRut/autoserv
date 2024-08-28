package com.garage.autoservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для запроса создания или обновления информации о расходе рабочей жидкости.
 * Используется для передачи данных между клиентом и сервером.
 */
@Data
public class FluidUsageRequest {

    @NotNull(message = "Serial number cannot be null")
    private String serialNumber;

    @NotNull(message = "Fluid type cannot be null")
    private String fluidType;

    @Positive(message = "Fluid volume must be positive")
    private Double fluidVolume;

    private String fluidBrand;

    @Positive(message = "Mileage must be positive")
    private Long mileage;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;
}
