package com.garage.autoservice.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
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
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Уникальный идентификатор автомобиля.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Серийный номер автомобиля.
     */
    @NotBlank(message = "Серийный номер не может быть пустым")
    @Size(max = 50, message = "Серийный номер не может превышать 50 символов")
    private String serialNumber;

    /**
     * Номер предприятия, к которому приписан автомобиль.
     */
    @NotBlank(message = "Номер предприятия не может быть пустым")
    @Size(max = 50, message = "Номер предприятия не может превышать 50 символов")
    private String enterpriseNumber;

    /**
     * Государственный регистрационный номер автомобиля.
     */
    @NotBlank(message = "Государственный регистрационный номер не может быть пустым")
    @Size(max = 20, message = "Государственный регистрационный номер не может превышать 20 символов")
    private String licensePlate;

    /**
     * Марка автомобиля.
     */
    @NotBlank(message = "Марка автомобиля не может быть пустой")
    @Size(max = 50, message = "Марка автомобиля не может превышать 50 символов")
    private String make;

    /**
     * Модель автомобиля.
     */
    @NotBlank(message = "Модель автомобиля не может быть пустой")
    @Size(max = 50, message = "Модель автомобиля не может превышать 50 символов")
    private String model;

    /**
     * Тип двигателя автомобиля (например, бензиновый, дизельный).
     */
    @NotBlank(message = "Тип двигателя не может быть пустым")
    @Size(max = 50, message = "Тип двигателя не может превышать 50 символов")
    private String engineType;

    /**
     * Номер двигателя автомобиля.
     */
    @NotBlank(message = "Номер двигателя не может быть пустым")
    @Size(max = 50, message = "Номер двигателя не может превышать 50 символов")
    private String engineNumber;

    /**
     * Тип трансмиссии автомобиля (например, автоматическая, механическая).
     */
    @NotBlank(message = "Тип трансмиссии не может быть пустым")
    @Size(max = 50, message = "Тип трансмиссии не может превышать 50 символов")
    private String transmissionType;

    /**
     * Номер трансмиссии автомобиля.
     */
    @NotBlank(message = "Номер трансмиссии не может быть пустым")
    @Size(max = 50, message = "Номер трансмиссии не может превышать 50 символов")
    private String transmissionNumber;

    /**
     * Год выпуска автомобиля.
     */
    @Min(value = 1886, message = "Год выпуска не может быть меньше 1886")
    @Max(value = 2100, message = "Год выпуска не может быть больше 2100")
    private int yearOfManufacture;

    /**
     * VIN-код автомобиля. Этот код является уникальным и необходим для идентификации автомобиля.
     */
    @NotBlank(message = "VIN-код не может быть пустым")
    @Size(max = 17, message = "VIN-код не может превышать 17 символов")
    @Column(unique = true, nullable = false)
    private String vin;

    /**
     * Пробег автомобиля в километрах.
     */
    @PositiveOrZero(message = "Пробег не может быть отрицательным")
    private Long mileage;

    /**
     * Моточасы двигателя автомобиля.
     */
    @PositiveOrZero(message = "Моточасы не могут быть отрицательными")
    private Long engineHours;

    /**
     * Тип автомобиля (например, легковой автомобиль, грузовик, автобус, сельскохозяйственная техника).
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Тип автомобиля не может быть пустым")
    private CarType carType;

    /**
     * Пробег автомобиля на момент последней выполненной работы.
     */
    @PositiveOrZero(message = "Пробег на момент последней работы не может быть отрицательным")
    private Long lastMileage;

    /**
     * Дата последнего выполнения работы.
     */
    private LocalDate lastServiceDate;

    /**
     * Список выполненных и запланированных работ, связанных с автомобилем.
     * Работы связаны с автомобилем через поле "car" в сущности {@link MaintenanceRecord}.
     */
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceRecord> maintenanceRecords;

    /**
     * Список запчастей, связанных с автомобилем.
     * Запчасти связаны с автомобилем через поле "car" в сущности {@link Part}.
     */
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> parts;




    /**
     * Перечисление типов автомобилей.
     */
    public enum CarType {
        /**
         * Легковой автомобиль.
         */
        PASSENGER_CAR,

        /**
         * Грузовик.
         */
        TRUCK,

        /**
         * Автобус.
         */
        BUS,

        /**
         * Сельскохозяйственная техника.
         */
        AGRICULTURAL_MACHINERY
    }
}
