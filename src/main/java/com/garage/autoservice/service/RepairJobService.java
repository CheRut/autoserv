package com.garage.autoservice.service;

import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.repository.PartRepository;
import com.garage.autoservice.repository.RepairJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления ремонтными работами.
 */
@Service
public class RepairJobService {

    private final RepairJobRepository repairJobRepository;
    private final PartRepository partRepository;

    @Autowired
    public RepairJobService(RepairJobRepository repairJobRepository, PartRepository partRepository) {
        this.repairJobRepository = repairJobRepository;
        this.partRepository = partRepository;
    }

    /**
     * Создает новую ремонтную работу и сохраняет ее в базе данных.
     *
     * @param request данные о ремонтной работе
     * @return созданная и сохраненная ремонтная работа
     */
    @Transactional
    public RepairJob createRepairJob(RepairJobRequest request) {
        RepairJob repairJob = new RepairJob();
        repairJob.setJobName(request.getJobName());
        repairJob.setIntervalInMileage(request.getIntervalInMileage());
        repairJob.setIntervalInHours(request.getIntervalInHours());
        repairJob.setIntervalInDays(request.getIntervalInDays());
        repairJob.setLastMileage(request.getLastMileage());
        repairJob.setLastJobDate(request.getLastJobDate());

        List<Part> requiredParts = processParts(request.getRequiredParts());
        repairJob.setRequiredParts(requiredParts);

        return repairJobRepository.save(repairJob);
    }

    /**
     * Обрабатывает создание списка ремонтных работ и сохраняет их в базе данных.
     *
     * @param requests список данных о ремонтных работах
     * @return список созданных и сохраненных ремонтных работ
     */
    @Transactional
    public List<RepairJob> createRepairJobsBatch(List<RepairJobRequest> requests) {
        List<RepairJob> createdJobs = new ArrayList<>();
        for (RepairJobRequest request : requests) {
            RepairJob createdJob = createRepairJob(request);
            createdJobs.add(createdJob);
        }
        return createdJobs;
    }

    /**
     * Получает ремонтную работу по идентификатору.
     *
     * @param id идентификатор ремонтной работы
     * @return найденная ремонтная работа
     */
    public RepairJob getRepairJobById(Long id) {
        return repairJobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ремонтная работа с ID " + id + " не найдена"));
    }

    /**
     * Получает все ремонтные работы.
     *
     * @return список всех ремонтных работ
     */
    public List<RepairJob> getAllRepairJobs() {
        return repairJobRepository.findAll();
    }

    /**
     * Обновляет существующую ремонтную работу.
     *
     * @param id идентификатор ремонтной работы
     * @param request данные об обновленной ремонтной работе
     * @return обновленная ремонтная работа
     * @throws IllegalArgumentException если ремонтная работа с указанным id не найдена
     */
    @Transactional
    public RepairJob updateRepairJob(Long id, RepairJobRequest request) {
        RepairJob repairJob = repairJobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ремонтная работа с ID " + id + " не найдена"));

        repairJob.setJobName(request.getJobName());
        repairJob.setIntervalInMileage(request.getIntervalInMileage());
        repairJob.setIntervalInHours(request.getIntervalInHours());
        repairJob.setIntervalInDays(request.getIntervalInDays());
        repairJob.setLastMileage(request.getLastMileage());
        repairJob.setLastJobDate(request.getLastJobDate());

        List<Part> updatedParts = processParts(request.getRequiredParts());
        repairJob.setRequiredParts(updatedParts);

        return repairJobRepository.save(repairJob);
    }

    /**
     * Обрабатывает и сохраняет список запчастей, указанных в запросе.
     *
     * @param partRequests список данных о запчастях
     * @return список сохраненных запчастей
     */
    private List<Part> processParts(List<PartRequest> partRequests) {
        List<Part> parts = new ArrayList<>();

        for (PartRequest partRequest : partRequests) {
            Optional<Part> existingPart = partRepository.findByNameAndManufacturerAndPartNumber(
                    partRequest.getName(), partRequest.getManufacturer(), partRequest.getPartNumber());

            if (existingPart.isPresent()) {
                Part part = existingPart.get();
                part.setQuantity(part.getQuantity() + partRequest.getQuantity());
                partRepository.save(part);
                parts.add(part);
            } else {
                Part newPart = new Part();
                newPart.setName(partRequest.getName());
                newPart.setManufacturer(partRequest.getManufacturer());
                newPart.setPartNumber(partRequest.getPartNumber());
                newPart.setQuantity(partRequest.getQuantity());
                newPart.setVin(partRequest.getVin());
                partRepository.save(newPart);
                parts.add(newPart);
            }
        }

        return parts;
    }
    /**
     * Получает все ремонтные работы для заданного автомобиля за указанный период времени.
     *
     * @param serialNumber идентификатор автомобиля (serialNumber)
     * @param startDate начальная дата периода
     * @param endDate конечная дата периода
     * @return список ремонтных работ
     */
    @Transactional(readOnly = true)
    public List<RepairJob> getJobsForVehicleInPeriod(String serialNumber, LocalDate startDate, LocalDate endDate) {
        return repairJobRepository.findAllBySerialNumberAndLastJobDateBetween(serialNumber, startDate, endDate);
    }
}
