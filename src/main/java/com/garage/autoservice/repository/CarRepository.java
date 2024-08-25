package com.garage.autoservice.repository;

import com.garage.autoservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями {@link Car}.
 * Обеспечивает методы для выполнения CRUD-операций с автомобилями в базе данных.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    /**
     * Метод для поиска автомобиля по его VIN-коду.
     *
     * @param vin VIN-код автомобиля
     * @return объект {@link Optional}, содержащий найденный автомобиль, если он существует
     */
    Optional<Car> findByVin(String vin);
}
