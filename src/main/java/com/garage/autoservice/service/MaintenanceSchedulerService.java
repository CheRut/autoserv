package com.garage.autoservice.service;

import com.garage.autoservice.entity.Car;
import com.garage.autoservice.entity.MaintenanceRecord;
import com.garage.autoservice.entity.PlannedMaintenance;
import com.garage.autoservice.repository.CarRepository;
import com.garage.autoservice.repository.MaintenanceRecordRepository;
import com.garage.autoservice.repository.PlannedMaintenanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Сервис для автоматического планирования технического обслуживания автомобилей на основе
 * записей о выполненных работах и указанных интервалов.
 */
@Service
public class MaintenanceSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceSchedulerService.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MaintenanceRecordRepository maintenanceRecordRepository;

    @Autowired
    private PlannedMaintenanceRepository plannedMaintenanceRepository;

    /**
     * Метод для запуска планирования технического обслуживания для всех автомобилей.
     */
    public void scheduleMaintenance() {
        logger.info("Запуск планирования технического обслуживания...");

        List<Car> cars = carRepository.findAll();
        for (Car car : cars) {
            List<MaintenanceRecord> records = maintenanceRecordRepository.findByCar(car);
            for (MaintenanceRecord record : records) {
                if (shouldScheduleNewJob(car, record)) {
                    scheduleNewJob(car, record.getJobName());
                }
            }
        }

        logger.info("Планирование технического обслуживания завершено.");
    }

    /**
     * Проверяет, нужно ли планировать новую работу для указанного автомобиля на основе
     * записей о предыдущих работах и интервалов.
     *
     * @param car    автомобиль, для которого проводится проверка
     * @param record запись о выполненной работе
     * @return true, если требуется запланировать новую работу, иначе false
     */
    private boolean shouldScheduleNewJob(Car car, MaintenanceRecord record) {
        Long currentMileage = car.getMileage(); // Измените тип на Long
        Long currentHours = car.getEngineHours() != null ? car.getEngineHours() : 0L;
        Date currentDate = new Date();

        if (record.getIntervalMileage() != null && currentMileage != null
                && currentMileage - record.getMileage() >= record.getIntervalMileage()) {
            return true;
        }

        if (record.getIntervalHours() != null && currentHours - record.getHours() >= record.getIntervalHours()) {
            return true;
        }

        if (record.getIntervalDays() != null) {
            long daysSinceLastJob = (currentDate.getTime() - record.getDate().getTime()) / (1000 * 60 * 60 * 24);
            if (daysSinceLastJob >= record.getIntervalDays()) {
                return true;
            }
        }

        // Если все интервалы пустые, работа выполнялась по необходимости, не планируем
        return false;
    }


    /**
     * Планирует новую работу для указанного автомобиля.
     *
     * @param car     автомобиль, для которого планируется работа
     * @param jobName наименование работы
     */
    private void scheduleNewJob(Car car, String jobName) {
        PlannedMaintenance plannedMaintenance = new PlannedMaintenance();
        plannedMaintenance.setCar(car);
        plannedMaintenance.setJobName(jobName);
        plannedMaintenance.setPlannedDate(new Date()); // Можно рассчитать дату на основе интервалов
        plannedMaintenanceRepository.save(plannedMaintenance);

        logger.info("Запланирована новая работа '{}' для автомобиля с VIN: {}", jobName, car.getVin());
    }
}
