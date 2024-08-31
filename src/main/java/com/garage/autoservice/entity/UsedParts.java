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
 * Сущность для учета использованных запчастей в ремонтных работах.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedParts {

    private static final Logger logger = LoggerFactory.getLogger(UsedParts.class);

    /**
     * Уникальный идентификатор записи об использованных запчастях.
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
     * Название запчасти.
     */
    @NotBlank(message = "Название запчасти не может быть пустым")
    private String name;

    /**
     * Производитель запчасти.
     */
    @NotBlank(message = "Производитель запчасти не может быть пустым")
    private String manufacturer;

    /**
     * Номер запчасти.
     */
    @NotBlank(message = "Номер запчасти не может быть пустым")
    private String partNumber;

    /**
     * Количество использованных запчастей.
     */
    @Min(value = 1, message = "Количество использованных запчастей должно быть хотя бы 1")
    private int quantity;

    /**
     * Идентификационный номер запчасти на складе.
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

    public String getName() {
        logger.debug("Получение названия запчасти: {}", name);
        return name;
    }

    public void setName(String name) {
        logger.debug("Установка названия запчасти: {}", name);
        this.name = name;
    }

    public String getManufacturer() {
        logger.debug("Получение производителя запчасти: {}", manufacturer);
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        logger.debug("Установка производителя запчасти: {}", manufacturer);
        this.manufacturer = manufacturer;
    }

    public String getPartNumber() {
        logger.debug("Получение номера запчасти: {}", partNumber);
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        logger.debug("Установка номера запчасти: {}", partNumber);
        this.partNumber = partNumber;
    }

    public int getQuantity() {
        logger.debug("Получение количества использованных запчастей: {}", quantity);
        return quantity;
    }

    public void setQuantity(int quantity) {
        logger.debug("Установка количества использованных запчастей: {}", quantity);
        this.quantity = quantity;
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
