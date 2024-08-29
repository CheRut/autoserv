package com.garage.autoservice.repository;

import com.garage.autoservice.entity.MaintenanceRecord;
import com.garage.autoservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для взаимодействия с записями о выполненных технических обслуживаниях (MaintenanceRecord).
 */
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {

    /**
     * Метод для получения всех записей о техобслуживании для конкретного автомобиля.
     *
     * @param car автомобиль, для которого ищутся записи
     * @return список записей о техобслуживании
     */
    List<MaintenanceRecord> findByCar(Car car);


    /**
     * Найти все записи о техобслуживании по VIN-коду автомобиля.
     *
     * @param vin VIN-код автомобиля, для которого ищутся записи
     * @return список записей о техобслуживании
     */
    List<MaintenanceRecord> findByVin(String vin);
}
