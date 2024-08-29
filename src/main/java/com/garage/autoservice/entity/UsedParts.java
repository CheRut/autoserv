package com.garage.autoservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность для учета использованных запчастей в ремонтных работах.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedParts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maintenance_record_id")
    private MaintenanceRecord maintenanceRecord;

    private String name;  // Название запчасти
    private String manufacturer;  // Производитель запчасти
    private String partNumber;  // Номер запчасти
    private int quantity;  // Количество использованных запчастей
    private String cardNumber;  // Идентификационный номер на складе
}
