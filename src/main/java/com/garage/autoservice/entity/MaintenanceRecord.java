package com.garage.autoservice.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vin;  // VIN-код автомобиля
    private String jobName;  // Наименование работы
    private int mileage;  // Пробег на момент проведения работы
    private int hours;  // Моточасы на момент проведения работы
    private Date date;  // Дата проведения работы

    @OneToMany(mappedBy = "maintenanceRecord", cascade = CascadeType.ALL)
    private List<UsedParts> usedParts;  // Список использованных запчастей

    @OneToMany(mappedBy = "maintenanceRecord", cascade = CascadeType.ALL)
    private List<UsedFluid> usedFluids;  // Список использованных жидкостей

    // Добавление интервалов
    private Integer intervalMileage;  // Интервал по пробегу (км)
    private Integer intervalHours;  // Интервал по моточасам
    private Integer intervalDays;  // Интервал по дням

}
