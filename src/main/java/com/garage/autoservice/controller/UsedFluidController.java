package com.garage.autoservice.controller;

import com.garage.autoservice.entity.UsedFluid;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.service.UsedFluidService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления записями об использованных жидкостях.
 */
@RestController
@RequestMapping("/api/used-fluids")
public class UsedFluidController {

    private static final Logger logger = LoggerFactory.getLogger(UsedFluidController.class);

    private final UsedFluidService usedFluidService;

    public UsedFluidController(UsedFluidService usedFluidService) {
        this.usedFluidService = usedFluidService;
    }

    /**
     * Создает новую запись об использованной жидкости.
     *
     * @param usedFluid Запись об использованной жидкости
     * @return созданная запись
     */
    @PostMapping
    public ResponseEntity<UsedFluid> createUsedFluid(@Valid @RequestBody UsedFluid usedFluid) {
        logger.info("Запрос на создание записи об использованной жидкости: {}", usedFluid);
        UsedFluid createdFluid = usedFluidService.createUsedFluid(usedFluid);
        logger.debug("Создана запись об использованной жидкости с ID: {}", createdFluid.getId());
        return ResponseEntity.ok(createdFluid);
    }

    /**
     * Получает все записи об использованных жидкостях.
     *
     * @return список всех записей
     */
    @GetMapping
    public ResponseEntity<List<UsedFluid>> getAllUsedFluids() {
        logger.info("Запрос на получение всех записей об использованных жидкостях");
        List<UsedFluid> fluids = usedFluidService.getAllUsedFluids();
        return ResponseEntity.ok(fluids);
    }

    /**
     * Обновляет существующую запись об использованной жидкости.
     *
     * @param id идентификатор записи
     * @param usedFluid данные для обновления
     * @return обновленная запись
     * @throws ResourceNotFoundException если запись с данным ID не найдена
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsedFluid> updateUsedFluid(@PathVariable Long id, @Valid @RequestBody UsedFluid usedFluid) {
        logger.info("Запрос на обновление записи об использованной жидкости с ID: {}", id);
        UsedFluid updatedFluid = usedFluidService.updateUsedFluid(id, usedFluid);
        if (updatedFluid == null) {
            logger.error("Запись об использованной жидкости с ID {} не найдена", id);
            throw new ResourceNotFoundException("Запись об использованной жидкости с ID " + id + " не найдена");
        }
        logger.debug("Запись об использованной жидкости с ID {} успешно обновлена", id);
        return ResponseEntity.ok(updatedFluid);
    }

    /**
     * Удаляет запись об использованной жидкости.
     *
     * @param id идентификатор записи
     * @return подтверждение удаления
     * @throws ResourceNotFoundException если запись с данным ID не найдена
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsedFluid(@PathVariable Long id) {
        logger.info("Запрос на удаление записи об использованной жидкости с ID: {}", id);
        usedFluidService.deleteUsedFluid(id);
        logger.debug("Запись об использованной жидкости с ID {} успешно удалена", id);
        return ResponseEntity.noContent().build();
    }
}
