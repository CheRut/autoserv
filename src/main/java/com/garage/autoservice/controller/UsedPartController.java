package com.garage.autoservice.controller;

import com.garage.autoservice.entity.UsedParts;
import com.garage.autoservice.service.UsedPartService;
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
    public ResponseEntity<UsedParts> createUsedPart(@RequestBody UsedParts usedPart) {
        logger.info("Запрос на создание записи об использованной запчасти: {}", usedPart);
        UsedParts createdPart = usedPartService.createUsedPart(usedPart);
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
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsedParts> updateUsedPart(@PathVariable Long id, @RequestBody UsedParts usedPart) {
        logger.info("Запрос на обновление записи об использованной запчасти с ID: {}", id);
        UsedParts updatedPart = usedPartService.updateUsedPart(id, usedPart);
        return ResponseEntity.ok(updatedPart);
    }

    /**
     * Удаляет запись об использованной запчасти.
     *
     * @param id идентификатор записи
     * @return подтверждение удаления
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsedPart(@PathVariable Long id) {
        logger.info("Запрос на удаление записи об использованной запчасти с ID: {}", id);
        usedPartService.deleteUsedPart(id);
        return ResponseEntity.noContent().build();
    }
}
