package com.garage.autoservice.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Сущность для представления расхода рабочих жидкостей (масла, антифриз, гидравлические жидкости и т.д.).
 */
@Entity
@Data
public class FluidUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    private LocalDate date;

    private double fluidVolume;  // Объем жидкости в литрах

    private String fluidType;  // Тип жидкости (масло, антифриз, гидравлическая жидкость и т.д.)

    private String fluidBrand;  // Бренд жидкости

    private double mileage;  // Пробег автомобиля на момент замены жидкости
}

