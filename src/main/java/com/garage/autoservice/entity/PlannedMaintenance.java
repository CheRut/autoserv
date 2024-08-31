package com.garage.autoservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Сущность для хранения данных о запланированных технических обслуживаниях.
 * Используется для отслеживания будущих работ, запланированных на автомобиле.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlannedMaintenance {

    /**
     * Уникальный идентификатор запланированного технического обслуживания.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Автомобиль, для которого запланировано техническое обслуживание.
     */
    @ManyToOne
    @JoinColumn(name = "car_id")
    @NotNull(message = "Автомобиль не может быть пустым")
    private Car car;

    /**
     * Наименование запланированной работы.
     */
    @NotBlank(message = "Наименование работы не может быть пустым")
    private String jobName;

    /**
     * Дата, на которую запланировано выполнение работы.
     */
    @NotNull(message = "Дата запланированного обслуживания не может быть пустой")
    private Date plannedDate;
}
