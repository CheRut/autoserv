package com.garage.autoservice.repository;

import com.garage.autoservice.entity.RepairJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для сущности RepairJob.
 */
@Repository
public interface RepairJobRepository extends JpaRepository<RepairJob, Long> {


}
