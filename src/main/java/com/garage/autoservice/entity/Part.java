package com.garage.autoservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название запчасти.
     */
    @NotBlank(message = "Название запчасти не может быть пустым")
    @Size(max = 100, message = "Название запчасти не может превышать 100 символов")
    private String name;

    /**
     * Производитель запчасти.
     */
    @NotBlank(message = "Производитель запчасти не может быть пустым")
    @Size(max = 100, message = "Производитель запчасти не может превышать 100 символов")
    private String manufacturer;

    /**
     * Номер запчасти.
     */
    @NotBlank(message = "Номер запчасти не может быть пустым")
    @Size(max = 50, message = "Номер запчасти не может превышать 50 символов")
    private String partNumber;

    /**
     * Количество запчастей на складе.
     */
    @Min(value = 0, message = "Количество запчастей не может быть отрицательным")
    private int quantity;

    /**
     * Карточка-идентификатор запчасти на складе.
     */
    @NotBlank(message = "cardNumber не может быть пустым")
    @Size(max = 50, message = "Номер запчасти не может превышать 50 символов")
    private String cardNumber;

    /**
     * VIN-код автомобиля, к которому подходит запчасть.
     */
    @NotBlank(message = "VIN-код не может быть пустым")
    @Size(max = 17, message = "VIN-код не может превышать 17 символов")
    @Column(nullable = false)
    private String vin;

    /**
     * Ссылка на автомобиль, с которым связана запчасть.
     */
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = true) // Связь с автомобилем необязательна
    @JsonBackReference
    private Car car;
}
