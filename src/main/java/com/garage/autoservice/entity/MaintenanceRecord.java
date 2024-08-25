package com.garage.autoservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vin;  // VIN-код автомобиля
    private String jobName;  // Наименование работы
    private int mileage;  // Пробег на момент проведения работы
    private int hours;  // Моточасы на момент проведения работы
    private Date date;  // Дата проведения работы
}
