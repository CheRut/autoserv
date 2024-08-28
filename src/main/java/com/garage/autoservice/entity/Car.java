package com.garage.autoservice.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Представляет сущность автомобиля с подробной информацией о транспортном средстве.
 * Эта сущность используется для хранения информации о различных типах транспортных средств в системе.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    private String enterpriseNumber;

    private String licensePlate;

    private String make;

    private String model;

    private String engineType;

    private String engineNumber;

    private String transmissionType;

    private String transmissionNumber;

    private int yearOfManufacture;

    @Column(unique = true, nullable = false)
    private String vin;

    private Long mileage;

    private Long engineHours;

    @Enumerated(EnumType.STRING)
    private CarType carType;

    /**
     * Пробег автомобиля на момент последней выполненной работы.
     */
    private Long lastMileage;

    /**
     * Дата последнего выполнения работы.
     */
    private LocalDate lastServiceDate;

    /**
     * Список выполненных и запланированных работ, связанных с автомобилем.
     */
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceRecord> maintenanceRecords;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> parts;

    public enum CarType {
        PASSENGER_CAR,
        TRUCK,
        BUS,
        AGRICULTURAL_MACHINERY
    }
}
