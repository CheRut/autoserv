package com.garage.autoservice.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для запроса создания или обновления информации о расходе рабочей жидкости.
 * Используется для передачи данных между клиентом и сервером.
 */
@Data
public class FluidUsageRequest {

    private String serialNumber;

    private String fluidType;

    private Double fluidVolume;

    private String fluidBrand;

    private Long mileage;

    private LocalDate date;
}
