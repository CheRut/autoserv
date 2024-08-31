package com.garage.autoservice.repository;

import com.garage.autoservice.entity.UsedParts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с записями об использованных запчастях.
 * Обеспечивает стандартные CRUD-операции для сущностей {@link UsedParts} в базе данных.
 */
@Repository
public interface UsedPartRepository extends JpaRepository<UsedParts, Long> {
    // Дополнительные методы для поиска можно добавить здесь при необходимости
}
