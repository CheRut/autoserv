package com.garage.autoservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сущность для учета использованных жидкостей в ремонтных работах.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedFluid {

    private static final Logger logger = LoggerFactory.getLogger(UsedFluid.class);

    /**
     * Уникальный идентификатор записи об использованной жидкости.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Связь с записью о проведенном техническом обслуживании.
     */
    @ManyToOne
    @JoinColumn(name = "maintenance_record_id")
    @NotNull(message = "Запись о техобслуживании не может быть null")
    private MaintenanceRecord maintenanceRecord;

    /**
     * Тип использованной жидкости (например, масло, антифриз).
     */
    @NotBlank(message = "Тип жидкости не может быть пустым")
    private String type;

    /**
     * Производитель жидкости.
     */
    @NotBlank(message = "Производитель жидкости не может быть пустым")
    private String brand;

    /**
     * Объем использованной жидкости.
     */
    @Min(value = 0, message = "Объем жидкости не может быть отрицательным")
    private double volume;

    /**
     * Идентификационный номер жидкости на складе.
     */
    @NotBlank(message = "Идентификационный номер на складе не может быть пустым")
    private String cardNumber;

    // Методы доступа с логированием

    public Long getId() {
        logger.debug("Получение ID: {}", id);
        return id;
    }

    public void setId(Long id) {
        logger.debug("Установка ID: {}", id);
        this.id = id;
    }

    public MaintenanceRecord getMaintenanceRecord() {
        logger.debug("Получение записи о техобслуживании: {}", maintenanceRecord);
        return maintenanceRecord;
    }

    public void setMaintenanceRecord(MaintenanceRecord maintenanceRecord) {
        logger.debug("Установка записи о техобслуживании: {}", maintenanceRecord);
        this.maintenanceRecord = maintenanceRecord;
    }

    public String getType() {
        logger.debug("Получение типа жидкости: {}", type);
        return type;
    }

    public void setType(String type) {
        logger.debug("Установка типа жидкости: {}", type);
        this.type = type;
    }

    public String getBrand() {
        logger.debug("Получение производителя жидкости: {}", brand);
        return brand;
    }

    public void setBrand(String brand) {
        logger.debug("Установка производителя жидкости: {}", brand);
        this.brand = brand;
    }

    public double getVolume() {
        logger.debug("Получение объема использованной жидкости: {}", volume);
        return volume;
    }

    public void setVolume(double volume) {
        logger.debug("Установка объема использованной жидкости: {}", volume);
        this.volume = volume;
    }

    public String getCardNumber() {
        logger.debug("Получение идентификационного номера на складе: {}", cardNumber);
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        logger.debug("Установка идентификационного номера на складе: {}", cardNumber);
        this.cardNumber = cardNumber;
    }
}
