package com.garage.autoservice.repository;

import com.garage.autoservice.entity.FluidUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для управления сущностями {@link FluidUsage}.
 * Обеспечивает методы для выполнения CRUD-операций и специфических запросов по расходу рабочих жидкостей.
 */
@Repository
public interface FluidUsageRepository extends JpaRepository<FluidUsage, Long> {

    /**
     * Находит все записи о расходе рабочих жидкостей для указанного автомобиля по его серийному номеру.
     *
     * @param serialNumber серийный номер автомобиля
     * @return список записей о расходе жидкостей для данного автомобиля
     */
    List<FluidUsage> findBySerialNumber(String serialNumber);

    /**
     * Находит записи о расходе рабочих жидкостей для указанного автомобиля по его серийному номеру
     * за указанный период времени.
     *
     * @param serialNumber серийный номер автомобиля
     * @param startDate начальная дата периода
     * @param endDate конечная дата периода
     * @return список записей о расходе жидкостей для данного автомобиля за указанный период
     */
    List<FluidUsage> findBySerialNumberAndDateBetween(String serialNumber, LocalDate startDate, LocalDate endDate);
}
