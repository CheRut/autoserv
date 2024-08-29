package com.garage.autoservice.repository;

import com.garage.autoservice.entity.UsedParts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с записями об использованных запчастях.
 */
@Repository
public interface UsedPartRepository extends JpaRepository<UsedParts, Long> {
    // Дополнительные методы для поиска можно добавить здесь
}
