package com.garage.autoservice.controller;

import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.entity.ScheduledRepairJob;
import com.garage.autoservice.service.RepairJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления ремонтными работами.
 */
@RestController
@RequestMapping("/api/repair-jobs")
public class RepairJobController {

    private final RepairJobService repairJobService;

    public RepairJobController(RepairJobService repairJobService) {
        this.repairJobService = repairJobService;
    }

    /**
     * Создает новую ремонтную работу.
     *
     * @param repairJobRequest запрос с данными для создания ремонтной работы.
     * @return созданная ремонтная работа.
     */
    @PostMapping
    public ResponseEntity<RepairJob> createRepairJob(@RequestBody RepairJobRequest repairJobRequest) {
        RepairJob repairJob = repairJobService.createRepairJob(repairJobRequest);
        return ResponseEntity.ok(repairJob);
    }

    /**
     * Создает несколько ремонтных работ.
     *
     * @param repairJobRequests список запросов на создание ремонтных работ.
     * @return список созданных ремонтных работ.
     */
    @PostMapping("/batch")
    public ResponseEntity<List<RepairJob>> createRepairJobsBatch(@RequestBody List<RepairJobRequest> repairJobRequests) {
        List<RepairJob> repairJobs = repairJobService.createRepairJobsBatch(repairJobRequests);
        return ResponseEntity.ok(repairJobs);
    }

    /**
     * Получает ремонтную работу по идентификатору.
     *
     * @param id идентификатор ремонтной работы
     * @return найденная ремонтная работа
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepairJob> getRepairJobById(@PathVariable Long id) {
        RepairJob repairJob = repairJobService.getRepairJobById(id);
        return ResponseEntity.ok(repairJob);
    }

    /**
     * Получает все ремонтные работы.
     *
     * @return список всех ремонтных работ
     */
    @GetMapping
    public ResponseEntity<List<RepairJob>> getAllRepairJobs() {
        List<RepairJob> repairJobs = repairJobService.getAllRepairJobs();
        return ResponseEntity.ok(repairJobs);
    }
    /**
     * Обновляет существующую ремонтную работу.
     *
     * @param id идентификатор ремонтной работы
     * @param repairJob данные для обновления ремонтной работы
     * @return обновленная ремонтная работа
     */
    @PutMapping("/{id}")
    public ResponseEntity<RepairJob> updateRepairJob(@PathVariable Long id, @RequestBody RepairJobRequest repairJob) {
        RepairJob updatedJob = repairJobService.updateRepairJob(id, repairJob);
        return ResponseEntity.ok(updatedJob);
    }


}
