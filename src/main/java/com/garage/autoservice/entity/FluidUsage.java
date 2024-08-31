package com.garage.autoservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Сущность для представления расхода рабочих жидкостей (масла, антифриз, гидравлические жидкости и т.д.).
 */
@Entity
@Data
public class FluidUsage {

    private static final Logger logger = LoggerFactory.getLogger(FluidUsage.class);

    /**
     * Уникальный идентификатор записи о расходе жидкости.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Серийный номер автомобиля, к которому относится данная запись.
     */
    @NotBlank(message = "Серийный номер автомобиля не может быть пустым")
    private String serialNumber;

    /**
     * Дата проведения замены или долива жидкости.
     */
    @NotNull(message = "Дата проведения замены не может быть пустой")
    private LocalDate date;

    /**
     * Объем использованной жидкости в литрах.
     */
    @Min(value = 0, message = "Объем жидкости не может быть отрицательным")
    private double fluidVolume;

    /**
     * Тип жидкости (например, масло, антифриз, гидравлическая жидкость).
     */
    @NotBlank(message = "Тип жидкости не может быть пустым")
    private String fluidType;

    /**
     * Бренд жидкости.
     */
    @NotBlank(message = "Бренд жидкости не может быть пустым")
    private String fluidBrand;

    /**
     * Пробег автомобиля на момент замены жидкости.
     */
    @Min(value = 0, message = "Пробег не может быть отрицательным")
    private double mileage;

    /**
     * Метод для регистрации использования жидкости.
     *
     * @throws IllegalArgumentException если объем жидкости или пробег отрицательные.
     */
    public void registerFluidUsage() {
        if (fluidVolume < 0 || mileage < 0) {
            logger.error("Ошибка регистрации расхода жидкости: отрицательные значения для объема или пробега.");
            throw new IllegalArgumentException("Объем жидкости и пробег не могут быть отрицательными.");
        }
        logger.info("Зарегистрировано использование {} литров {} на автомобиле с серийным номером {}.",
                fluidVolume, fluidType, serialNumber);
    }
}
