package com.garage.autoservice.repository;

import com.garage.autoservice.entity.RepairJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления сущностями {@link RepairJob}.
 * Обеспечивает методы для выполнения CRUD-операций с ремонтными работами в базе данных.
 */
@Repository
public interface RepairJobRepository extends JpaRepository<RepairJob, Long> {

    /**
     * Получает все ремонтные работы для заданного автомобиля за указанный период времени.
     *
     * @param serialNumber идентификатор автомобиля (serialNumber)
     * @param startDate начальная дата периода
     * @param endDate конечная дата периода
     * @return список ремонтных работ, выполненных на автомобиле в указанный период времени
     */
    List<RepairJob> findAllBySerialNumberAndLastJobDateBetween(String serialNumber, LocalDate startDate, LocalDate endDate);

    Optional<RepairJob> findBySerialNumber(String serialNumber);

    Optional<RepairJob> findByOrderNumber(String orderNumber);
}
