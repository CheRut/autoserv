package com.garage.autoservice.service;

import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.exception.InvalidRequestException;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.repository.PartRepository;
import com.garage.autoservice.repository.RepairJobRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Создает новую ремонтную работу и сохраняет ее в базу данных.
     *
     * @param request данные для создания ремонтной работы.
     * @return созданная ремонтная работа.
     */
    @Transactional
    public RepairJob createRepairJob(@Valid RepairJobRequest request) {
        logger.debug("Создание новой ремонтной работы с данными: {}", request);

        RepairJob repairJob = new RepairJob();
        repairJob.setJobName(request.getJobName());
        repairJob.setIntervalInMileage(request.getIntervalInMileage());
        repairJob.setIntervalInHours(request.getIntervalInHours());
        repairJob.setIntervalInDays(request.getIntervalInDays());
        repairJob.setLastMileage(request.getLastMileage());
        repairJob.setLastJobDate(request.getLastJobDate());
        repairJob.setSerialNumber(request.getSerialNumber());

        List<Part> parts = request.getRequiredParts().stream()
                .map(this::createOrUpdatePart)
                .collect(Collectors.toList());

        repairJob.setRequiredParts(parts);

        try {
            RepairJob savedJob = repairJobRepository.save(repairJob);
            logger.info("Ремонтная работа успешно сохранена с ID: {}", savedJob.getId());
            return savedJob;
        } catch (Exception e) {
            logger.error("Ошибка при сохранении ремонтной работы: {}", e.getMessage(), e);
            throw new IllegalStateException("Не удалось сохранить ремонтную работу.", e);
        }
    }

    /**
     * Создает несколько ремонтных работ и сохраняет их в базу данных.
     *
     * @param requests список запросов на создание ремонтных работ.
     * @return список созданных ремонтных работ.
     */
    @Transactional
    public List<RepairJob> createRepairJobsBatch(@Valid List<RepairJobRequest> requests) {
        return requests.stream()
                .map(this::createRepairJob)
                .collect(Collectors.toList());
    }

    /**
     * Получает ремонтную работу по идентификатору.
     *
     * @param id идентификатор ремонтной работы
     * @return найденная ремонтная работа
     */
    @Transactional(readOnly = true)
    public RepairJob getRepairJobById(@NotNull Long id) {
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
    @Transactional(readOnly = true)
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
     */
    @Transactional
    public RepairJob updateRepairJob(@NotNull Long id, @Valid RepairJobRequest request) {
        logger.info("Обновление ремонтной работы с ID: {}", id);
        RepairJob repairJob = repairJobRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ремонтная работа с ID {} не найдена", id);
                    return new ResourceNotFoundException("Ремонтная работа с ID " + id + " не найдена");
                });

        repairJob.setJobName(request.getJobName());
        repairJob.setIntervalInMileage(request.getIntervalInMileage());
        repairJob.setIntervalInHours(request.getIntervalInHours());
        repairJob.setIntervalInDays(request.getIntervalInDays());
        repairJob.setLastMileage(request.getLastMileage());
        repairJob.setLastJobDate(request.getLastJobDate());

        List<Part> updatedParts = request.getRequiredParts().stream()
                .map(this::createOrUpdatePart)
                .collect(Collectors.toList());

        repairJob.setRequiredParts(updatedParts);

        try {
            RepairJob updatedJob = repairJobRepository.save(repairJob);
            logger.info("Ремонтная работа с ID {} успешно обновлена", id);
            return updatedJob;
        } catch (Exception e) {
            logger.error("Ошибка при обновлении ремонтной работы с ID {}: {}", id, e.getMessage(), e);
            throw new InvalidRequestException("Не удалось обновить ремонтную работу: " + e.getMessage());
        }
    }

    /**
     * Обрабатывает и сохраняет список запчастей, указанных в запросе.
     *
     * @param partRequests список данных о запчастях
     * @return список сохраненных запчастей
     */
    private Part createOrUpdatePart(@Valid PartRequest partRequest) {
        logger.debug("Обработка запчасти: {}", partRequest);
        Optional<Part> existingPart = partRepository.findByNameAndManufacturerAndPartNumber(
                partRequest.getName(), partRequest.getManufacturer(), partRequest.getPartNumber());

        Part part;
        if (existingPart.isPresent()) {
            part = existingPart.get();
            part.setQuantity(part.getQuantity() + partRequest.getQuantity());
        } else {
            part = new Part();
            part.setName(partRequest.getName());
            part.setManufacturer(partRequest.getManufacturer());
            part.setPartNumber(partRequest.getPartNumber());
            part.setQuantity(partRequest.getQuantity());
            part.setCardNumber(partRequest.getCardNumber());
            part.setVin(partRequest.getVin());
        }

        Part savedPart = partRepository.save(part);
        logger.debug("Запчасть {} сохранена в базе данных с ID {}", savedPart.getName(), savedPart.getId());
        return savedPart;
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
    public List<RepairJob> getJobsForVehicleInPeriod(@NotNull String serialNumber,
                                                     @NotNull LocalDate startDate,
                                                     @NotNull LocalDate endDate) {
        logger.info("Получение ремонтных работ для автомобиля с серийным номером {} за период с {} по {}",
                serialNumber, startDate, endDate);
        return repairJobRepository.findAllBySerialNumberAndLastJobDateBetween(serialNumber, startDate, endDate);
    }
}
