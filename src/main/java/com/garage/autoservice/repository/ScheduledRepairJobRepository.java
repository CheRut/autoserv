package com.garage.autoservice.repository;

import com.garage.autoservice.entity.ScheduledRepairJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для сущности ScheduledRepairJob.
 */
@Repository
public interface ScheduledRepairJobRepository extends JpaRepository<ScheduledRepairJob, Long> {
    List<ScheduledRepairJob> findByVin(String vin);
}
