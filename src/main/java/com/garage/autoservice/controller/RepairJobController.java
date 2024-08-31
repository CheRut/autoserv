package com.garage.autoservice.controller;

import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.service.RepairJobService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST-контроллер для управления ремонтными работами.
 */
@RestController
@RequestMapping("/api/repair-jobs")
public class RepairJobController {

    private static final Logger logger = LoggerFactory.getLogger(RepairJobController.class);

    private final RepairJobService repairJobService;

    public RepairJobController(RepairJobService repairJobService) {
        this.repairJobService = repairJobService;
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    /**
     * Создает новую ремонтную работу.
     *
     * @param repairJobRequest запрос с данными для создания ремонтной работы.
     * @return созданная ремонтная работа.
     */
    @PostMapping
    public ResponseEntity<RepairJob> createRepairJob(@RequestBody RepairJobRequest repairJobRequest) {
        logger.info("Получен запрос на создание новой ремонтной работы: {}", repairJobRequest);

        try {
            logger.info("Вызов метода сервиса createRepairJob");
            RepairJob repairJob = repairJobService.createRepairJob(repairJobRequest);
            logger.info("Ремонтная работа создана с ID: {}", repairJob.getId());
            return ResponseEntity.ok(repairJob);
        } catch (Exception e) {
            logger.error("Ошибка при создании ремонтной работы: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Создает несколько ремонтных работ.
     *
     * @param repairJobRequests список запросов на создание ремонтных работ.
     * @return список созданных ремонтных работ.
     */
    @PostMapping("/batch")
    public ResponseEntity<List<RepairJob>> createRepairJobsBatch(@Valid @RequestBody List<RepairJobRequest> repairJobRequests) {
        logger.info("Создание нескольких ремонтных работ (batch)");
        try {
            List<RepairJob> repairJobs = repairJobService.createRepairJobsBatch(repairJobRequests);
            logger.info("Создано {} ремонтных работ", repairJobs.size());
            return ResponseEntity.ok(repairJobs);
        } catch (Exception e) {
            logger.error("Ошибка при создании нескольких ремонтных работ: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
    /**
     * Получает ремонтную работу по идентификатору.
     *
     * @param id идентификатор ремонтной работы
     * @return найденная ремонтная работа
     * @throws ResourceNotFoundException если работа с заданным ID не найдена
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepairJob> getRepairJobById(@NotNull @PathVariable Long id) {
        logger.info("Запрос на получение ремонтной работы с ID: {}", id);
        RepairJob repairJob = repairJobService.getRepairJobById(id);
        if (repairJob == null) {
            logger.error("Ремонтная работа с ID {} не найдена", id);
            throw new ResourceNotFoundException("Ремонтная работа с ID " + id + " не найдена");
        }
        return ResponseEntity.ok(repairJob);
    }

    /**
     * Получает все ремонтные работы.
     *
     * @return список всех ремонтных работ
     */
    @GetMapping
    public ResponseEntity<List<RepairJob>> getAllRepairJobs() {
        logger.info("Запрос на получение всех ремонтных работ");
        List<RepairJob> repairJobs = repairJobService.getAllRepairJobs();
        return ResponseEntity.ok(repairJobs);
    }

    /**
     * Обновляет существующую ремонтную работу.
     *
     * @param id идентификатор ремонтной работы
     * @param repairJob данные для обновления ремонтной работы
     * @return обновленная ремонтная работа
     * @throws ResourceNotFoundException если работа с заданным ID не найдена
     */
    @PutMapping("/{id}")
    public ResponseEntity<RepairJob> updateRepairJob(@NotNull @PathVariable Long id, @Valid @RequestBody RepairJobRequest repairJob) {
        logger.info("Запрос на обновление ремонтной работы с ID: {}", id);
        RepairJob updatedJob = repairJobService.updateRepairJob(id, repairJob);
        if (updatedJob == null) {
            logger.error("Ремонтная работа с ID {} не найдена", id);
            throw new ResourceNotFoundException("Ремонтная работа с ID " + id + " не найдена");
        }
        logger.info("Ремонтная работа с ID {} успешно обновлена", id);
        return ResponseEntity.ok(updatedJob);
    }

    /**
     * Получает все ремонтные работы для заданного автомобиля за указанный период времени.
     *
     * @param serialNumber идентификатор автомобиля (serialNumber)
     * @param startDate начальная дата периода
     * @param endDate конечная дата периода
     * @return список ремонтных работ
     */
    @GetMapping("/by-period")
    public ResponseEntity<List<RepairJob>> getJobsForVehicleInPeriod(
            @NotNull @RequestParam String serialNumber,
            @NotNull @RequestParam LocalDate startDate,
            @NotNull @RequestParam LocalDate endDate) {
        logger.info("Запрос на получение ремонтных работ для автомобиля с serialNumber: {} за период с {} по {}", serialNumber, startDate, endDate);
        List<RepairJob> repairJobs = repairJobService.getJobsForVehicleInPeriod(serialNumber, startDate, endDate);
        return ResponseEntity.ok(repairJobs);
    }
}
