package com.garage.autoservice.service;

import com.garage.autoservice.entity.Fluid;
import com.garage.autoservice.repository.FluidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления сущностью Fluid.
 * Предоставляет методы для взаимодействия с базой данных.
 */
@Service
public class FluidService {

    @Autowired
    private FluidRepository fluidRepository;

    /**
     * Сохранить жидкость в базу данных.
     *
     * @param fluid сущность, которую необходимо сохранить.
     */
    public void save(Fluid fluid) {
        fluidRepository.save(fluid);
    }

    /**
     * Удалить жидкость из базы данных.
     *
     * @param fluid сущность, которую необходимо удалить.
     */
    public void delete(Fluid fluid) {
        fluidRepository.delete(fluid);
    }

    /**
     * Найти все жидкости в базе данных.
     *
     * @return список всех сущностей Fluid.
     */
    public List<Fluid> findAll() {
        return fluidRepository.findAll();
    }

    /**
     * Найти жидкость по идентификационному номеру на складе.
     *
     * @param cardNumber идентификационный номер на складе.
     * @return сущность Fluid, если найдена.
     */
    public Fluid findByCardNumber(String cardNumber) {
        return fluidRepository.findByCardNumber(cardNumber).orElse(null);
    }

    public Optional<Fluid> findById(Long id) {
        return fluidRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return fluidRepository.existsById(id);
    }

    public void deleteById(Long id) {
        fluidRepository.deleteById(id);
    }
}
