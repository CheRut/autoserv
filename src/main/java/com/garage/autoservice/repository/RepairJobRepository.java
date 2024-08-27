package com.garage.autoservice.repository;

import com.garage.autoservice.entity.RepairJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для сущности RepairJob.
 */
@Repository
public interface RepairJobRepository extends JpaRepository<RepairJob, Long> {
    /**
     * Получает все ремонтные работы для заданного автомобиля за указанный период времени.
     *
     * @param serialNumber идентификатор автомобиля (serialNumber)
     * @param startDate начальная дата периода
     * @param endDate конечная дата периода
     * @return список ремонтных работ
     */
    List<RepairJob> findAllBySerialNumberAndLastJobDateBetween(String serialNumber, LocalDate startDate, LocalDate endDate);

}
