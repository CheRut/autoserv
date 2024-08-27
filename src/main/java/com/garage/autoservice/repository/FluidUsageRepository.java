package com.garage.autoservice.repository;

import com.garage.autoservice.entity.FluidUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FluidUsageRepository extends JpaRepository<FluidUsage, Long> {
    List<FluidUsage> findBySerialNumber(String serialNumber);
    List<FluidUsage> findBySerialNumberAndDateBetween(String serialNumber, LocalDate startDate, LocalDate endDate);
}
