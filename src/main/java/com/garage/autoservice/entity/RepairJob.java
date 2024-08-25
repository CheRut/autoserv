package com.garage.autoservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;          // Наименование типа работ
    private int intervalInDays;      // Интервал проведения по сроку (в днях)
    private int intervalInMileage;   // Интервал проведения по пробегу (в километрах)

    // Этот класс теперь самостоятельный и не имеет привязок к другим сущностям.
}
