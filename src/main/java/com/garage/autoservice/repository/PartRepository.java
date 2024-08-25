package com.garage.autoservice.repository;

import com.garage.autoservice.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления сущностями {@link Part}.
 * Обеспечивает методы для выполнения CRUD-операций с запчастями в базе данных.
 */
@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    /**
     * Метод для поиска запчастей по их названию.
     *
     * @param name название запчасти
     * @return список запчастей с указанным названием
     */
    List<Part> findByName(String name);
}
