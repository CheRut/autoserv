package com.garage.autoservice.service;

import com.garage.autoservice.dto.FluidUsageRequest;
import com.garage.autoservice.entity.FluidUsage;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.repository.FluidUsageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления расходом рабочих жидкостей.
 */
@Service
public class FluidUsageService {

    private static final Logger logger = LoggerFactory.getLogger(FluidUsageService.class);

    private final FluidUsageRepository fluidUsageRepository;

    @Autowired
    public FluidUsageService(FluidUsageRepository fluidUsageRepository) {
        this.fluidUsageRepository = fluidUsageRepository;
    }

    /**
     * Создает новую запись о расходе рабочей жидкости и сохраняет ее в базе данных.
     *
     * @param fluidUsageRequest данные о расходе жидкости
     * @return созданная и сохраненная запись
     */
    @Transactional
    public FluidUsage createFluidUsage(FluidUsageRequest fluidUsageRequest) {
        FluidUsage fluidUsage = convertToEntity(fluidUsageRequest);
        logger.debug("Создание записи о расходе рабочей жидкости: {}", fluidUsage);
        return fluidUsageRepository.save(fluidUsage);
    }

    /**
     * Получает все записи о расходе рабочих жидкостей.
     *
     * @return список всех записей
     */
    @Transactional(readOnly = true)
    public List<FluidUsage> getAllFluidUsageRecords() {
        logger.info("Запрос на получение всех записей о расходе рабочих жидкостей");
        return fluidUsageRepository.findAll();
    }


    /**
     * Получает запись о расходе рабочей жидкости по идентификатору.
     *
     * @param id идентификатор записи
     * @return найденная запись
     */
    @Transactional(readOnly = true)
    public Optional<FluidUsage> getFluidUsageById(Long id) {
        logger.info("Запрос на получение записи о расходе рабочей жидкости с ID: {}", id);
        return fluidUsageRepository.findById(id);
    }


    /**
     * Обновляет существующую запись о расходе рабочей жидкости.
     *
     * @param id идентификатор записи
     * @param fluidUsageRequest данные для обновления
     * @return обновленная запись
     * @throws ResourceNotFoundException если запись с указанным id не найдена
     */
    @Transactional
    public FluidUsage updateFluidUsage(Long id, FluidUsageRequest fluidUsageRequest) {
        FluidUsage existingFluidUsage = fluidUsageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись о расходе рабочей жидкости с ID " + id + " не найдена"));

        existingFluidUsage.setFluidType(fluidUsageRequest.getFluidType());
        existingFluidUsage.setFluidVolume(fluidUsageRequest.getFluidVolume());
        existingFluidUsage.setFluidBrand(fluidUsageRequest.getFluidBrand());
        existingFluidUsage.setMileage(fluidUsageRequest.getMileage());
        existingFluidUsage.setDate(fluidUsageRequest.getDate());

        logger.debug("Обновление записи о расходе рабочей жидкости: {}", existingFluidUsage);
        return fluidUsageRepository.save(existingFluidUsage);
    }

    /**
     * Удаляет запись о расходе рабочей жидкости.
     *
     * @param id идентификатор записи
     * @throws ResourceNotFoundException если запись с указанным id не найдена
     */
    @Transactional
    public void deleteFluidUsage(Long id) {
        FluidUsage fluidUsage = fluidUsageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись о расходе рабочей жидкости с ID " + id + " не найдена"));

        fluidUsageRepository.delete(fluidUsage);
        logger.debug("Удалена запись о расходе рабочей жидкости с ID: {}", id);
    }

    /**
     * Конвертирует DTO FluidUsageRequest в сущность FluidUsage.
     *
     * @param fluidUsageRequest DTO с данными о расходе жидкости
     * @return сущность FluidUsage
     */
    public FluidUsage convertToEntity(FluidUsageRequest fluidUsageRequest) {
        FluidUsage fluidUsage = new FluidUsage();
        fluidUsage.setSerialNumber(fluidUsageRequest.getSerialNumber());
        fluidUsage.setFluidType(fluidUsageRequest.getFluidType());
        fluidUsage.setFluidVolume(fluidUsageRequest.getFluidVolume());
        fluidUsage.setFluidBrand(fluidUsageRequest.getFluidBrand());
        fluidUsage.setMileage(fluidUsageRequest.getMileage());
        fluidUsage.setDate(fluidUsageRequest.getDate());
        return fluidUsage;
    }

}
