package com.garage.autoservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Сущность, представляющая работу по ремонту и обслуживанию для конкретного автомобиля.
 * Включает в себя информацию о типе работ, интервалах проведения и данных о последних проведенных работах.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование типа работ (например, замена масла).
     */
    private String jobName;

    /**
     * Интервал проведения работы по пробегу (в километрах).
     */
    private int intervalInMileage;

    /**
     * Интервал проведения работы по моточасам (в часах).
     */
    private int intervalInHours;

    /**
     * Интервал проведения работы по сроку (в днях).
     */
    private int intervalInDays;

    /**
     * Пробег автомобиля на момент последней проведенной работы.
     */
    private Long lastMileage;

    /**
     * Моточасы на момент последней проведенной работы.
     */
    private Long lastHours;

    /**
     * Дата последней проведенной работы.
     */
    @Temporal(TemporalType.DATE)
    private Date lastServiceDate;

    /**
     * Список запчастей, необходимых для выполнения работы.
     */
    @ManyToMany
    @JoinTable(
            name = "repairjob_parts",
            joinColumns = @JoinColumn(name = "repairjob_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id")
    )
    private List<Part> requiredParts;

    /**
     * Количество запчастей, необходимых для выполнения работы.
     */
    @ElementCollection
    @CollectionTable(name = "repairjob_part_quantity", joinColumns = @JoinColumn(name = "repairjob_id"))
    @Column(name = "quantity")
    private List<Integer> partQuantities;
}
