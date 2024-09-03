package com.garage.autoservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сущность для учета жидкостей и масел на складе.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fluid {

    private static final Logger logger = LoggerFactory.getLogger(Fluid.class);

    /**
     * Уникальный идентификатор записи о жидкости.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование жидкости.
     */
    @NotBlank(message = "Наименование жидкости не может быть пустым")
    private String name;

    /**
     * Количество жидкости на складе.
     */
    @Min(value = 0, message = "Количество жидкости не может быть отрицательным")
    private int quantity;


    /**
     * Идентификационный номер жидкости на складе.
     */
    @NotBlank(message = "Идентификационный номер на складе не может быть пустым")
    private String cardNumber;

    /**
     * Тип жидкости (например, масло, антифриз, фреон и т.д.).
     */
    @NotBlank(message = "Тип жидкости не может быть пустым")
    private String type;

    // Методы доступа с логированием

    public Long getId() {
        logger.debug("Получение ID: {}", id);
        return id;
    }

    public void setId(Long id) {
        logger.debug("Установка ID: {}", id);
        this.id = id;
    }

    public String getName() {
        logger.debug("Получение наименования жидкости: {}", name);
        return name;
    }

    public void setName(String name) {
        logger.debug("Установка наименования жидкости: {}", name);
        this.name = name;
    }

    public int getQuantity() {
        logger.debug("Получение количества жидкости: {}", quantity);
        return quantity;
    }

    public void setQuantity(int quantity) {
        logger.debug("Установка количества жидкости: {}", quantity);
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

    public String getType() {
        logger.debug("Получение типа жидкости: {}", type);
        return type;
    }

    public void setType(String type) {
        logger.debug("Установка типа жидкости: {}", type);
        this.type = type;
    }
}

