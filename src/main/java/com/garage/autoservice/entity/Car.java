package com.garage.autoservice.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    /**
     * Уникальный идентификатор автомобиля, генерируемый автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Серийный номер автомобиля или техники.
     * Обычно используется для идентификации конкретной единицы техники внутри компании или организации.
     */
    private String serialNumber;

    /**
     * Внутренний номер предприятия, к которому относится техника.
     * Используется для учета техники по филиалам или подразделениям внутри компании.
     */
    private String enterpriseNumber;

    /**
     * Государственный регистрационный номер автомобиля.
     * Это уникальный номер, присваиваемый транспортному средству при регистрации.
     */
    private String licensePlate;

    /**
     * Марка автомобиля или производителя техники.
     * Это название компании, которая выпустила технику.
     */
    private String make;

    /**
     * Модель автомобиля или техники.
     * Это конкретное наименование модели, выпущенной производителем.
     */
    private String model;

    /**
     * Тип двигателя автомобиля.
     * Может включать информацию о топливе или принципе работы двигателя.
     */
    private String engineType;

    /**
     * Номер двигателя автомобиля.
     * Это уникальный номер, присваиваемый двигателю на заводе.
     */
    private String engineNumber;

    /**
     * Тип коробки передач автомобиля (КПП).
     * Указывает на тип трансмиссии, установленной в автомобиле.
     */
    private String transmissionType;

    /**
     * Номер коробки передач (КПП).
     * Это уникальный номер, присваиваемый коробке передач.
     */
    private String transmissionNumber;

    /**
     * Год выпуска автомобиля или техники.
     * Указывает на год, в котором транспортное средство было произведено.
     */
    private int yearOfManufacture;

    /**
     * VIN-код автомобиля, уникальный идентификатор транспортного средства.
     */
    @Column(unique = true)
    private String vin;

    /**
     * Пробег автомобиля в километрах.
     */
    private Long mileage;

    /**
     * Моточасы для сельскохозяйственной техники.
     * Используется вместо пробега для определенных типов техники.
     */
    private Long engineHours;

    /**
     * Тип автомобиля (например, легковой автомобиль, грузовик, автобус, сельскохозяйственная техника).
     */
    @Enumerated(EnumType.STRING)
    private CarType carType;

    /**
     * Список запчастей, связанных с автомобилем.
     * Эти запчасти управляются как связь один ко многим с автомобилем.
     */
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> parts;

    /**
     * Перечисление, представляющее различные типы автомобилей.
     */
    public enum CarType {
        PASSENGER_CAR,
        TRUCK,
        BUS,
        AGRICULTURAL_MACHINERY
    }
}
