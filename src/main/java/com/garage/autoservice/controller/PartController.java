package com.garage.autoservice.controller;

import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.repository.PartRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST-контроллер для управления операциями с запчастями.
 * Предоставляет API для выполнения CRUD-операций с сущностью {@link Part}.
 */
@RestController
@RequestMapping("/api/parts")
public class PartController {

    private static final Logger logger = LoggerFactory.getLogger(PartController.class);

    @Autowired
    private PartRepository partRepository;

    /**
     * Получить список всех запчастей.
     *
     * @return список всех запчастей
     */
    @GetMapping
    public List<Part> getAllParts() {
        logger.info("Получение списка всех запчастей");
        return partRepository.findAll();
    }

    /**
     * Создать новую запчасть.
     *
     * @param request запрос с данными запчасти для создания
     * @return созданная запчасть
     */
    @PostMapping
    public Part createPart(@Valid @RequestBody PartRequest request) {
        logger.info("Создание новой запчасти с параметрами: {}", request);

        Part part = new Part();
        part.setName(request.getName());
        part.setManufacturer(request.getManufacturer());
        part.setPartNumber(request.getPartNumber());
        part.setQuantity(request.getQuantity());
        part.setCardNumber(request.getCardNumber());
        part.setVin(request.getVin());

        Part savedPart = partRepository.save(part);
        logger.info("Запчасть успешно создана с ID: {}", savedPart.getId());
        return savedPart;
    }

    /**
     * Создать новые запчасти и связать их с автомобилями по VIN-коду.
     *
     * @param requests список запросов на создание запчастей
     * @return список созданных запчастей
     */
    @PostMapping("/batch")
    public List<Part> createParts(@Valid @RequestBody List<PartRequest> requests) {
        logger.info("Создание нескольких запчастей (batch)");
        return requests.stream().map(request -> {
            Part part = new Part();
            part.setName(request.getName());
            part.setManufacturer(request.getManufacturer());
            part.setPartNumber(request.getPartNumber());
            part.setQuantity(request.getQuantity());
            part.setCardNumber(request.getCardNumber());
            part.setVin(request.getVin());

            Part savedPart = partRepository.save(part);
            logger.info("Запчасть создана с ID: {}", savedPart.getId());
            return savedPart;
        }).collect(Collectors.toList());
    }

    /**
     * Получить запчасть по идентификатору.
     *
     * @param id идентификатор запчасти
     * @return объект {@link ResponseEntity}, содержащий запчасть или статус 404, если запчасть не найдена
     */
    @GetMapping("/{id}")
    public ResponseEntity<Part> getPartById(@NotNull @PathVariable Long id) {
        logger.info("Запрос на получение запчасти с ID: {}", id);
        Part part = partRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Запчасть с ID {} не найдена", id);
                    return new ResourceNotFoundException("Запчасть с ID " + id + " не найдена");
                });
        return ResponseEntity.ok(part);
    }

    /**
     * Обновить информацию о запчасти.
     *
     * @param id идентификатор запчасти для обновления
     * @param partDetails объект с обновленными данными запчасти
     * @return объект {@link ResponseEntity}, содержащий обновленную запчасть или статус 404, если запчасть не найдена
     */
    @PutMapping("/{id}")
    public ResponseEntity<Part> updatePart(@NotNull @PathVariable Long id, @Valid @RequestBody Part partDetails) {
        logger.info("Запрос на обновление запчасти с ID: {}", id);
        Part part = partRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Запчасть с ID {} не найдена для обновления", id);
                    return new ResourceNotFoundException("Запчасть с ID " + id + " не найдена");
                });

        part.setName(partDetails.getName());
        part.setManufacturer(partDetails.getManufacturer());
        part.setPartNumber(partDetails.getPartNumber());
        part.setQuantity(partDetails.getQuantity());
        part.setCardNumber(partDetails.getCardNumber());

        if (partDetails.getVin() != null) {
            part.setVin(partDetails.getVin());
        }

        Part updatedPart = partRepository.save(part);
        logger.info("Запчасть с ID {} успешно обновлена", id);
        return ResponseEntity.ok(updatedPart);
    }

    /**
     * Удалить запчасть по идентификатору.
     *
     * @param id идентификатор запчасти для удаления
     * @return объект {@link ResponseEntity} со статусом 200, если удаление успешно, или статус 404, если запчасть не найдена
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@NotNull @PathVariable Long id) {
        logger.info("Запрос на удаление запчасти с ID: {}", id);
        Part part = partRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Запчасть с ID {} не найдена для удаления", id);
                    return new ResourceNotFoundException("Запчасть с ID " + id + " не найдена");
                });

        partRepository.delete(part);
        logger.info("Запчасть с ID {} успешно удалена", id);
        return ResponseEntity.ok().build();
    }
}
