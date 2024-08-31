package com.garage.autoservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Сущность для учета выполненных ремонтных работ по автомобилям.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRecord {

    /**
     * Автомобиль, к которому относится запись о ремонте.
     */
    @ManyToOne
    @JoinColumn(name = "car_id")
    @NotNull(message = "Автомобиль не может быть пустым")
    private Car car;

    /**
     * Уникальный идентификатор записи о ремонте.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * VIN-код автомобиля.
     */
    @NotBlank(message = "VIN-код не может быть пустым")
    private String vin;

    /**
     * Наименование выполненной работы.
     */
    @NotBlank(message = "Наименование работы не может быть пустым")
    private String jobName;

    /**
     * Пробег автомобиля на момент проведения работы (в километрах).
     */
    @Min(value = 0, message = "Пробег не может быть отрицательным")
    private int mileage;

    /**
     * Моточасы на момент проведения работы.
     */
    @Min(value = 0, message = "Моточасы не могут быть отрицательными")
    private int hours;

    /**
     * Дата проведения работы.
     */
    @NotNull(message = "Дата проведения работы не может быть пустой")
    private Date date;

    /**
     * Список использованных запчастей.
     */
    @OneToMany(mappedBy = "maintenanceRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsedParts> usedParts;

    /**
     * Список использованных жидкостей.
     */
    @OneToMany(mappedBy = "maintenanceRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsedFluid> usedFluids;

    /**
     * Интервал по пробегу для планирования следующей работы (в километрах).
     */
    @Min(value = 0, message = "Интервал по пробегу не может быть отрицательным")
    private Integer intervalMileage;

    /**
     * Интервал по моточасам для планирования следующей работы.
     */
    @Min(value = 0, message = "Интервал по моточасам не может быть отрицательным")
    private Integer intervalHours;

    /**
     * Интервал по времени для планирования следующей работы (в днях).
     */
    @Min(value = 0, message = "Интервал по времени не может быть отрицательным")
    private Integer intervalDays;
}
