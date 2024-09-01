package com.garage.autoservice.repository;

import com.garage.autoservice.entity.Fluid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Fluid.
 */
@Repository
public interface FluidRepository extends JpaRepository<Fluid, Long> {

    /**
     * Найти жидкость по идентификационному номеру на складе.
     *
     * @param cardNumber идентификационный номер на складе.
     * @return Optional сущности Fluid.
     */
    Optional<Fluid> findByCardNumber(String cardNumber);
}
