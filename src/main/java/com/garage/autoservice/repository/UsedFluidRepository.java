package com.garage.autoservice.repository;

import com.garage.autoservice.entity.UsedFluid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с записями об использованных жидкостях.
 * Обеспечивает стандартные CRUD-операции для сущностей {@link UsedFluid} в базе данных.
 */
@Repository
public interface UsedFluidRepository extends JpaRepository<UsedFluid, Long> {
    // Дополнительные методы для поиска можно добавить здесь при необходимости
}
