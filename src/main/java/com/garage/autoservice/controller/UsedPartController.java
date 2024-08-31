package com.garage.autoservice.controller;

import com.garage.autoservice.entity.UsedParts;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.service.UsedPartService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления записями об использованных запчастях.
 */
@RestController
@RequestMapping("/api/used-parts")
public class UsedPartController {

    private static final Logger logger = LoggerFactory.getLogger(UsedPartController.class);

    private final UsedPartService usedPartService;

    public UsedPartController(UsedPartService usedPartService) {
        this.usedPartService = usedPartService;
    }

    /**
     * Создает новую запись об использованной запчасти.
     *
     * @param usedPart Запись об использованной запчасти
     * @return созданная запись
     */
    @PostMapping
    public ResponseEntity<UsedParts> createUsedPart(@Valid @RequestBody UsedParts usedPart) {
        logger.info("Запрос на создание записи об использованной запчасти: {}", usedPart);
        UsedParts createdPart = usedPartService.createUsedPart(usedPart);
        logger.debug("Создана запись об использованной запчасти с ID: {}", createdPart.getId());
        return ResponseEntity.ok(createdPart);
    }

    /**
     * Получает все записи об использованных запчастях.
     *
     * @return список всех записей
     */
    @GetMapping
    public ResponseEntity<List<UsedParts>> getAllUsedParts() {
        logger.info("Запрос на получение всех записей об использованных запчастях");
        List<UsedParts> parts = usedPartService.getAllUsedParts();
        return ResponseEntity.ok(parts);
    }

    /**
     * Обновляет существующую запись об использованной запчасти.
     *
     * @param id идентификатор записи
     * @param usedPart данные для обновления
     * @return обновленная запись
     * @throws ResourceNotFoundException если запись с данным ID не найдена
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsedParts> updateUsedPart(@PathVariable Long id, @Valid @RequestBody UsedParts usedPart) {
        logger.info("Запрос на обновление записи об использованной запчасти с ID: {}", id);
        UsedParts updatedPart = usedPartService.updateUsedPart(id, usedPart);
        if (updatedPart == null) {
            logger.error("Запись об использованной запчасти с ID {} не найдена", id);
            throw new ResourceNotFoundException("Запись об использованной запчасти с ID " + id + " не найдена");
        }
        logger.debug("Запись об использованной запчасти с ID {} успешно обновлена", id);
        return ResponseEntity.ok(updatedPart);
    }

    /**
     * Удаляет запись об использованной запчасти.
     *
     * @param id идентификатор записи
     * @return подтверждение удаления
     * @throws ResourceNotFoundException если запись с данным ID не найдена
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsedPart(@PathVariable Long id) {
        logger.info("Запрос на удаление записи об использованной запчасти с ID: {}", id);
        usedPartService.deleteUsedPart(id);
        logger.debug("Запись об использованной запчасти с ID {} успешно удалена", id);
        return ResponseEntity.noContent().build();
    }
}
