package com.garage.autoservice.controller;

import com.garage.autoservice.entity.MaintenanceRecord;
import com.garage.autoservice.entity.PlannedMaintenance;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.repository.MaintenanceRecordRepository;
import com.garage.autoservice.repository.PlannedMaintenanceRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@Api(tags = "Maintenance API", description = "Operations related to vehicle maintenance")
public class MaintenanceController {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceController.class);

    @Autowired
    private MaintenanceRecordRepository maintenanceRecordRepository;

    @Autowired
    private PlannedMaintenanceRepository plannedMaintenanceRepository;

    /**
     * Получить все записи о проведенных техобслуживаниях.
     *
     * @return список записей о техобслуживаниях
     */
    @GetMapping("/records")
    @ApiOperation(value = "Get all maintenance records", notes = "Retrieve all maintenance records for vehicles.")
    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        logger.info("Получение всех записей о техобслуживаниях");
        return maintenanceRecordRepository.findAll();
    }

    /**
     * Получить записи о техобслуживаниях для конкретного автомобиля по VIN.
     *
     * @param vin VIN-код автомобиля
     * @return список записей о техобслуживаниях
     * @throws ResourceNotFoundException если записи не найдены
     */
    @GetMapping("/records/{vin}")
    @ApiOperation(value = "Get maintenance records by VIN", notes = "Retrieve maintenance records for a specific vehicle by VIN.")
    public ResponseEntity<List<MaintenanceRecord>> getMaintenanceRecordsByVin(@PathVariable String vin) {
        logger.info("Получение записей о техобслуживании для VIN: {}", vin);
        List<MaintenanceRecord> records = maintenanceRecordRepository.findByVin(vin);
        if (records.isEmpty()) {
            logger.error("Записи о техобслуживании для VIN: {} не найдены", vin);
            throw new ResourceNotFoundException("Записи о техобслуживании для VIN: " + vin + " не найдены");
        }
        return ResponseEntity.ok(records);
    }

    /**
     * Добавить новую запись о техобслуживании.
     *
     * @param record данные о техобслуживании
     * @return добавленная запись о техобслуживании
     */
    @PostMapping("/records")
    @ApiOperation(value = "Add a new maintenance record", notes = "Add a new maintenance record for a vehicle.")
    public ResponseEntity<MaintenanceRecord> addMaintenanceRecord(@RequestBody MaintenanceRecord record) {
        logger.info("Добавление новой записи о техобслуживании для VIN: {}", record.getVin());
        MaintenanceRecord savedRecord = maintenanceRecordRepository.save(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
    }

    /**
     * Получить все запланированные работы по ТО.
     *
     * @return список запланированных работ
     */
    @GetMapping("/planned")
    @ApiOperation(value = "Get all planned maintenance tasks", notes = "Retrieve all planned maintenance tasks for vehicles.")
    public List<PlannedMaintenance> getAllPlannedMaintenance() {
        logger.info("Получение всех запланированных работ по ТО");
        return plannedMaintenanceRepository.findAll();
    }

    /**
     * Запланировать новую работу по ТО.
     *
     * @param maintenance данные о планируемой работе
     * @return добавленная запись о планируемой работе
     */
    @PostMapping("/planned")
    @ApiOperation(value = "Schedule a new maintenance task", notes = "Create a new maintenance task for a vehicle.")
    public ResponseEntity<PlannedMaintenance> scheduleMaintenance(@RequestBody PlannedMaintenance maintenance) {
        logger.info("Запланирована новая работа '{}' для автомобиля с VIN: {}", maintenance.getJobName(), maintenance.getCar().getVin());
        PlannedMaintenance savedMaintenance = plannedMaintenanceRepository.save(maintenance);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMaintenance);
    }
}
