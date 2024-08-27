package com.garage.autoservice.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) для запроса создания или обновления информации о запчасти.
 * Используется для передачи данных между клиентом и сервером.
 */
@Data
public class PartRequest {

    /**
     * Название запчасти.
     */
    private String name;

    /**
     * Производитель запчасти.
     */
    private String manufacturer;

    /**
     * Номер запчасти.
     */
    private String partNumber;

    /**
     * Количество запчастей на складе.
     */
    private int quantity;

    /**
     * Цена за единицу запчасти.
     */
    private int cardNumber;

    /**
     * VIN-код автомобиля, к которому подходит запчасть (может быть null).
     */
    private String vin;
}
