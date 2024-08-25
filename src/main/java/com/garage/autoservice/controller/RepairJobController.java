package com.garage.autoservice.controller;

import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.repository.PartRepository;
import com.garage.autoservice.repository.RepairJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST-контроллер для управления операциями с ремонтными работами.
 * Предоставляет API для выполнения CRUD-операций с сущностью {@link RepairJob}.
 */
@RestController
@RequestMapping("/api/repair-jobs")
public class RepairJobController {

    @Autowired
    private RepairJobRepository repairJobRepository;

    @Autowired
    private PartRepository partRepository;

    /**
     * Получить список всех ремонтных работ.
     *
     * @return список всех ремонтных работ
     */
    @GetMapping
    public List<RepairJob> getAllRepairJobs() {
        return repairJobRepository.findAll();
    }

    /**
     * Создать новую ремонтную работу.
     *
     * @param repairJob объект с данными новой ремонтной работы
     * @return объект {@link ResponseEntity}, содержащий созданную ремонтную работу
     */
    @PostMapping
    public ResponseEntity<RepairJob> createRepairJob(@RequestBody RepairJob repairJob) {
        RepairJob savedRepairJob = repairJobRepository.save(repairJob);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRepairJob);
    }

    /**
     * Создать список новых ремонтных работ.
     *
     * @param repairJobs список объектов с данными новых ремонтных работ
     * @return список созданных ремонтных работ
     */

    @PostMapping("/batch")
    public List<RepairJob> createRepairJobsBatch(@RequestBody List<RepairJobRequest> requests) {
        return requests.stream().map(request -> {
            // Сохранение всех необходимых запчастей (если они еще не сохранены)
            List<Part> parts = request.getRequiredParts().stream().map(partRequest -> {
                Part part = new Part();
                part.setName(partRequest.getName());
                part.setManufacturer(partRequest.getManufacturer());
                part.setPartNumber(partRequest.getPartNumber());
                part.setQuantity(partRequest.getQuantity());
                part.setPrice(partRequest.getPrice());
                part.setVin(partRequest.getVin());

                // Сохранение Part перед использованием
                return partRepository.save(part);
            }).collect(Collectors.toList());

            // Создание нового RepairJob с сохраненными запчастями
            RepairJob repairJob = new RepairJob();
            repairJob.setJobName(request.getJobName());
            repairJob.setIntervalInDays(request.getIntervalInDays());
            repairJob.setIntervalInMileage(request.getIntervalInMileage());
            repairJob.setRequiredParts(parts);
            repairJob.setPartQuantities(request.getPartQuantities());

            // Сохранение RepairJob
            return repairJobRepository.save(repairJob);
        }).collect(Collectors.toList());
    }


    /**
     * Получить ремонтную работу по идентификатору.
     *
     * @param id идентификатор ремонтной работы
     * @return объект {@link ResponseEntity}, содержащий ремонтную работу или статус 404, если работа не найдена
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepairJob> getRepairJobById(@PathVariable Long id) {
        return repairJobRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Обновить информацию о ремонтной работе.
     *
     * @param id идентификатор ремонтной работы для обновления
     * @param repairJobDetails объект с обновленными данными ремонтной работы
     * @return объект {@link ResponseEntity}, содержащий обновленную ремонтную работу или статус 404, если работа не найдена
     */
    @PutMapping("/{id}")
    public ResponseEntity<RepairJob> updateRepairJob(@PathVariable Long id, @RequestBody RepairJob repairJobDetails) {
        return repairJobRepository.findById(id)
                .map(repairJob -> {
                    repairJob.setJobName(repairJobDetails.getJobName());
                    repairJob.setIntervalInMileage(repairJobDetails.getIntervalInMileage());
                    repairJob.setIntervalInHours(repairJobDetails.getIntervalInHours());
                    repairJob.setIntervalInDays(repairJobDetails.getIntervalInDays());
                    repairJob.setLastMileage(repairJobDetails.getLastMileage());
                    repairJob.setLastHours(repairJobDetails.getLastHours());
                    repairJob.setLastServiceDate(repairJobDetails.getLastServiceDate());
                    repairJob.setRequiredParts(repairJobDetails.getRequiredParts());
                    repairJob.setPartQuantities(repairJobDetails.getPartQuantities());
                    RepairJob updatedRepairJob = repairJobRepository.save(repairJob);
                    return ResponseEntity.ok(updatedRepairJob);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Удалить ремонтную работу по идентификатору.
     *
     * @param id идентификатор ремонтной работы для удаления
     * @return объект {@link ResponseEntity} со статусом 200, если удаление успешно, или статус 404, если работа не найдена
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
