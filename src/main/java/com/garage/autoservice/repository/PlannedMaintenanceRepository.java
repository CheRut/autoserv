package com.garage.autoservice.repository;

import com.garage.autoservice.entity.PlannedMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с запланированными техническими обслуживаниями.
 */
public interface PlannedMaintenanceRepository extends JpaRepository<PlannedMaintenance, Long> {
}
