package com.garage.autoservice.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

/**
 * DTO (Data Transfer Object) для запроса создания или обновления информации о запчасти.
 * Используется для передачи данных между клиентом и сервером.
 */
@Data
public class PartRequest {

    /**
     * Название запчасти.
     */
    @NotEmpty(message = "Название запчасти не может быть пустым")
    private String name;

    /**
     * Производитель запчасти.
     */
    @NotEmpty(message = "Производитель запчасти не может быть пустым")
    private String manufacturer;

    /**
     * Номер запчасти.
     */
    @NotEmpty(message = "Номер запчасти не может быть пустым")
    private String partNumber;

    /**
     * Количество запчастей на складе.
     */
    @Positive(message = "Количество запчастей должно быть положительным числом")
    private int quantity;

    /**
     * Цена за единицу запчасти.
     */
    @Positive(message = "Код карточки должен быть положительным числом")
    private int cardNumber;

    /**
     * VIN-код автомобиля, к которому подходит запчасть (может быть null).
     */
    private String vin;
}
