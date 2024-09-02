package com.garage.autoservice.repository;

import com.garage.autoservice.entity.PlannedRepairJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannedRepairJobRepository extends JpaRepository<PlannedRepairJob, Long> {
}
