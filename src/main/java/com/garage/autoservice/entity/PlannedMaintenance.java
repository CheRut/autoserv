package com.garage.autoservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Сущность для хранения данных о запланированных технических обслуживаниях.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlannedMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private String jobName;  // Наименование работы
    private Date plannedDate;  // Дата, на которую запланировано выполнение работы
}
