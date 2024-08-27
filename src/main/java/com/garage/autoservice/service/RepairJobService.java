package com.garage.autoservice.service;

import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.exception.InvalidRequestException;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.repository.PartRepository;
import com.garage.autoservice.repository.RepairJobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(RepairJobService.class);

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
        logger.info("Создание новой ремонтной работы: {}", request);
        try {
            RepairJob repairJob = new RepairJob();
            repairJob.setJobName(request.getJobName());
            repairJob.setIntervalInMileage(request.getIntervalInMileage());
            repairJob.setIntervalInHours(request.getIntervalInHours());
            repairJob.setIntervalInDays(request.getIntervalInDays());
            repairJob.setLastMileage(request.getLastMileage());
            repairJob.setLastJobDate(request.getLastJobDate());

            List<Part> requiredParts = processParts(request.getRequiredParts());
            repairJob.setRequiredParts(requiredParts);

            RepairJob savedJob = repairJobRepository.save(repairJob);
            logger.info("Ремонтная работа успешно создана с ID: {}", savedJob.getId());
            return savedJob;
        } catch (Exception e) {
            logger.error("Ошибка при создании ремонтной работы: {}", e.getMessage());
            throw new InvalidRequestException("Не удалось создать ремонтную работу: " + e.getMessage());
        }
    }

    /**
     * Обрабатывает создание списка ремонтных работ и сохраняет их в базе данных.
     *
     * @param requests список данных о ремонтных работах
     * @return список созданных и сохраненных ремонтных работ
     */
    @Transactional
    public List<RepairJob> createRepairJobsBatch(List<RepairJobRequest> requests) {
        logger.info("Создание партии ремонтных работ: {}", requests);
        List<RepairJob> createdJobs = new ArrayList<>();
        try {
            for (RepairJobRequest request : requests) {
                RepairJob createdJob = createRepairJob(request);
                createdJobs.add(createdJob);
            }
            logger.info("Успешно создано {} ремонтных работ", createdJobs.size());
            return createdJobs;
        } catch (Exception e) {
            logger.error("Ошибка при создании партии ремонтных работ: {}", e.getMessage());
            throw new InvalidRequestException("Не удалось создать партию ремонтных работ: " + e.getMessage());
        }
    }

    /**
     * Получает ремонтную работу по идентификатору.
     *
     * @param id идентификатор ремонтной работы
     * @return найденная ремонтная работа
     */
    public RepairJob getRepairJobById(Long id) {
        logger.info("Получение ремонтной работы с ID: {}", id);
        return repairJobRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ремонтная работа с ID {} не найдена", id);
                    return new ResourceNotFoundException("Ремонтная работа с ID " + id + " не найдена");
                });
    }

    /**
     * Получает все ремонтные работы.
     *
     * @return список всех ремонтных работ
     */
    public List<RepairJob> getAllRepairJobs() {
        logger.info("Получение всех ремонтных работ");
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
        logger.info("Обновление ремонтной работы с ID: {}", id);
        RepairJob repairJob = repairJobRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ремонтная работа с ID {} не найдена", id);
                    return new ResourceNotFoundException("Ремонтная работа с ID " + id + " не найдена");
                });

        try {
            repairJob.setJobName(request.getJobName());
            repairJob.setIntervalInMileage(request.getIntervalInMileage());
            repairJob.setIntervalInHours(request.getIntervalInHours());
            repairJob.setIntervalInDays(request.getIntervalInDays());
            repairJob.setLastMileage(request.getLastMileage());
            repairJob.setLastJobDate(request.getLastJobDate());

            List<Part> updatedParts = processParts(request.getRequiredParts());
            repairJob.setRequiredParts(updatedParts);

            RepairJob updatedJob = repairJobRepository.save(repairJob);
            logger.info("Ремонтная работа с ID {} успешно обновлена", id);
            return updatedJob;
        } catch (Exception e) {
            logger.error("Ошибка при обновлении ремонтной работы с ID {}: {}", id, e.getMessage());
            throw new InvalidRequestException("Не удалось обновить ремонтную работу: " + e.getMessage());
        }
    }

    /**
     * Обрабатывает и сохраняет список запчастей, указанных в запросе.
     *
     * @param partRequests список данных о запчастях
     * @return список сохраненных запчастей
     */
    private List<Part> processParts(List<PartRequest> partRequests) {
        logger.info("Обработка запчастей: {}", partRequests);
        List<Part> parts = new ArrayList<>();

        for (PartRequest partRequest : partRequests) {
            Optional<Part> existingPart = partRepository.findByNameAndManufacturerAndPartNumber(
                    partRequest.getName(), partRequest.getManufacturer(), partRequest.getPartNumber());

            if (existingPart.isPresent()) {
                Part part = existingPart.get();
                part.setQuantity(part.getQuantity() + partRequest.getQuantity());
                partRepository.save(part);
                parts.add(part);
                logger.debug("Запчасть {} обновлена в базе данных", part.getName());
            } else {
                Part newPart = new Part();
                newPart.setName(partRequest.getName());
                newPart.setManufacturer(partRequest.getManufacturer());
                newPart.setPartNumber(partRequest.getPartNumber());
                newPart.setQuantity(partRequest.getQuantity());
                newPart.setVin(partRequest.getVin());
                partRepository.save(newPart);
                parts.add(newPart);
                logger.debug("Запчасть {} добавлена в базу данных", newPart.getName());
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
        logger.info("Получение ремонтных работ для автомобиля с серийным номером {} за период с {} по {}", serialNumber, startDate, endDate);
        return repairJobRepository.findAllBySerialNumberAndLastJobDateBetween(serialNumber, startDate, endDate);
    }
}
