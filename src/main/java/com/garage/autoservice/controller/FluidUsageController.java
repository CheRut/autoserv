package com.garage.autoservice.controller;

import com.garage.autoservice.dto.FluidUsageRequest;
import com.garage.autoservice.entity.FluidUsage;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.service.FluidUsageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления расходом рабочих жидкостей.
 */
@RestController
@RequestMapping("/api/fluid-usage")
public class FluidUsageController {

    private static final Logger logger = LoggerFactory.getLogger(FluidUsageController.class);

    private final FluidUsageService fluidUsageService;

    @Autowired
    public FluidUsageController(FluidUsageService fluidUsageService) {
        this.fluidUsageService = fluidUsageService;
    }

    /**
     * Создать новую запись о расходе рабочей жидкости.
     *
     * @param fluidUsageRequest данные о расходе жидкости
     * @return созданная запись
     */
    @PostMapping
    public ResponseEntity<FluidUsage> createFluidUsage(@RequestBody FluidUsageRequest fluidUsageRequest) {
        logger.info("Запрос на создание новой записи о расходе рабочей жидкости: {}", fluidUsageRequest);
        FluidUsage createdFluidUsage = fluidUsageService.createFluidUsage(fluidUsageRequest);
        logger.debug("Создана запись о расходе рабочей жидкости с ID: {}", createdFluidUsage.getId());
        return ResponseEntity.ok(createdFluidUsage);
    }

    /**
     * Получить все записи о расходе рабочих жидкостей.
     *
     * @return список всех записей
     */
    @GetMapping
    public ResponseEntity<List<FluidUsage>> getAllFluidUsageRecords() {
        logger.info("Запрос на получение всех записей о расходе рабочих жидкостей");
        List<FluidUsage> fluidUsages = fluidUsageService.getAllFluidUsageRecords();
        return ResponseEntity.ok(fluidUsages);
    }

    /**
     * Получить запись о расходе рабочей жидкости по идентификатору.
     *
     * @param id идентификатор записи
     * @return найденная запись
     */
    @GetMapping("/{id}")
    public ResponseEntity<FluidUsage> getFluidUsageById(@PathVariable Long id) {
        logger.info("Запрос на получение записи о расходе рабочей жидкости с ID: {}", id);
        FluidUsage fluidUsage = fluidUsageService.getFluidUsageById(id)
                .orElseThrow(() -> {
                    logger.error("Запись о расходе рабочей жидкости с ID {} не найдена", id);
                    return new ResourceNotFoundException("Запись о расходе рабочей жидкости с ID " + id + " не найдена");
                });
        return ResponseEntity.ok(fluidUsage);
    }

    /**
     * Обновить запись о расходе рабочей жидкости.
     *
     * @param id идентификатор записи
     * @param fluidUsageRequest данные для обновления
     * @return обновленная запись
     */
    @PutMapping("/{id}")
    public ResponseEntity<FluidUsage> updateFluidUsage(@PathVariable Long id, @RequestBody FluidUsageRequest fluidUsageRequest) {
        logger.info("Запрос на обновление записи о расходе рабочей жидкости с ID: {}", id);
        FluidUsage updatedFluidUsage = fluidUsageService.updateFluidUsage(id, fluidUsageRequest);
        logger.debug("Запись о расходе рабочей жидкости с ID {} обновлена", id);
        return ResponseEntity.ok(updatedFluidUsage);
    }

    /**
     * Удалить запись о расходе рабочей жидкости.
     *
     * @param id идентификатор записи
     * @return статус выполнения
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFluidUsage(@PathVariable Long id) {
        logger.info("Запрос на удаление записи о расходе рабочей жидкости с ID: {}", id);
        fluidUsageService.deleteFluidUsage(id);
        logger.debug("Запись о расходе рабочей жидкости с ID {} успешно удалена", id);
        return ResponseEntity.ok().build();
    }
}
