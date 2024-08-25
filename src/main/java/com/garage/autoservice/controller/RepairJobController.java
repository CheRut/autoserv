package com.garage.autoservice.controller;

import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.repository.RepairJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repair-jobs")
public class RepairJobController {

    @Autowired
    private RepairJobRepository repairJobRepository;

    /**
     * Получение списка всех типов ремонтных работ.
     *
     * @return список всех ремонтных работ
     */
    @GetMapping
    public List<RepairJob> getAllRepairJobs() {
        return repairJobRepository.findAll();
    }

    /**
     * Создание нового типа ремонтной работы.
     *
     * @param repairJob объект RepairJob для создания
     * @return созданный объект RepairJob
     */
    @PostMapping
    public RepairJob createRepairJob(@RequestBody RepairJob repairJob) {
        return repairJobRepository.save(repairJob);
    }

    /**
     * Создание нескольких типов ремонтных работ списком.
     *
     * @param repairJobs список объектов RepairJob для создания
     * @return список созданных объектов RepairJob
     */
    @PostMapping("/batch")
    public List<RepairJob> createRepairJobsBatch(@RequestBody List<RepairJob> repairJobs) {
        return repairJobRepository.saveAll(repairJobs);
    }

    /**
     * Получение ремонтной работы по её идентификатору.
     *
     * @param id идентификатор ремонтной работы
     * @return объект RepairJob или 404, если не найдено
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepairJob> getRepairJobById(@PathVariable Long id) {
        return repairJobRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Обновление информации о типе ремонтной работы.
     *
     * @param id идентификатор ремонтной работы для обновления
     * @param repairJobDetails объект RepairJob с обновленными данными
     * @return обновленный объект RepairJob или 404, если не найдено
     */
    @PutMapping("/{id}")
    public ResponseEntity<RepairJob> updateRepairJob(@PathVariable Long id, @RequestBody RepairJob repairJobDetails) {
        return repairJobRepository.findById(id)
                .map(repairJob -> {
                    repairJob.setJobName(repairJobDetails.getJobName());
                    repairJob.setIntervalInDays(repairJobDetails.getIntervalInDays());
                    repairJob.setIntervalInMileage(repairJobDetails.getIntervalInMileage());

                    RepairJob updatedRepairJob = repairJobRepository.save(repairJob);
                    return ResponseEntity.ok(updatedRepairJob);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Удаление типа ремонтной работы.
     *
     * @param id идентификатор ремонтной работы для удаления
     * @return статус 200, если удалено, или 404, если не найдено
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepairJob(@PathVariable Long id) {
        return repairJobRepository.findById(id)
                .map(repairJob -> {
                    repairJobRepository.delete(repairJob);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
