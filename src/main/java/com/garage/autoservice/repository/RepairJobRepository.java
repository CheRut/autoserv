package com.garage.autoservice.repository;

import com.garage.autoservice.entity.RepairJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairJobRepository extends JpaRepository<RepairJob, Long> {
    // Здесь можно добавить дополнительные методы для поиска или фильтрации RepairJob, если потребуется.
}
