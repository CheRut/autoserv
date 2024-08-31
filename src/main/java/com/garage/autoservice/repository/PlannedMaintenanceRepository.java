package com.garage.autoservice.repository;

import com.garage.autoservice.entity.PlannedMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с запланированными техническими обслуживаниями.
 * Обеспечивает методы для выполнения CRUD-операций с сущностями {@link PlannedMaintenance} в базе данных.
 */
@Repository
public interface PlannedMaintenanceRepository extends JpaRepository<PlannedMaintenance, Long> {
}
