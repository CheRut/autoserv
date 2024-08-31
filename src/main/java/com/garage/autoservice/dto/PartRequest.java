package com.garage.autoservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;



/**
 * DTO (Data Transfer Object) для запроса создания или обновления информации о запчасти.
 * Используется для передачи данных между клиентом и сервером.
 */
@Data
public class PartRequest {

    /**
     * Название запчасти.
     * Этот параметр не может быть пустым.
     */
    @NotEmpty(message = "Название запчасти не может быть пустым")
    private String name;

    /**
     * Производитель запчасти.
     * Этот параметр не может быть пустым.
     */
    @NotEmpty(message = "Производитель запчасти не может быть пустым")
    private String manufacturer;

    /**
     * Номер запчасти.
     * Этот параметр не может быть пустым.
     */
    @NotEmpty(message = "Номер запчасти не может быть пустым")
    private String partNumber;

    /**
     * Количество запчастей на складе.
     * Этот параметр должен быть положительным числом.
     */
    @Positive(message = "Количество запчастей должно быть положительным числом")
    private int quantity;

    /**
     * Код карточки на складе, соответствующий запчасти.
     * Этот параметр должен быть положительным числом.
     */
    @Positive(message = "Код карточки должен быть положительным числом")
    private int cardNumber;

    /**
     * VIN-код автомобиля, к которому подходит запчасть (может быть null).
     * Этот параметр может быть пустым, если запчасть не привязана к конкретному автомобилю.
     */
    private String vin;
}
