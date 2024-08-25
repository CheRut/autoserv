package com.garage.autoservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Представляет сущность запчасти, связанной с автомобилем.
 * Эта сущность используется для хранения информации о запчастях в системе.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Part {

    /**
     * Уникальный идентификатор запчасти, генерируемый автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Используем автоинкремент
    private Long id;

    /**
     * Название запчасти.
     */
    private String name;

    /**
     * Производитель запчасти.
     */
    private String manufacturer;

    /**
     * Номер запчасти.
     */
    private String partNumber;

    /**
     * Количество запчастей на складе.
     */
    private int quantity;

    /**
     * Цена за единицу запчасти.
     */
    private double price;

    /**
     * VIN-код автомобиля, к которому подходит запчасть.
     */
    private String vin;

    /**
     * Ссылка на автомобиль, с которым связана запчасть.
     */
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = true) // Связь с автомобилем необязательна
    @JsonBackReference
    private Car car;
}
