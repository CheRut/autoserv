package com.garage.autoservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность для учета использованных жидкостей в ремонтных работах.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedFluid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maintenance_record_id")
    private MaintenanceRecord maintenanceRecord;

    private String type;  // Тип жидкости (например, масло, антифриз)
    private String brand;  // Производитель жидкости
    private double volume;  // Объем использованной жидкости
    private String cardNumber;  // Идентификационный номер на складе
}
