package com.garage.autoservice.service;

import com.garage.autoservice.entity.Car;
import com.garage.autoservice.entity.MaintenanceRecord;
import com.garage.autoservice.entity.PlannedMaintenance;
import com.garage.autoservice.repository.CarRepository;
import com.garage.autoservice.repository.MaintenanceRecordRepository;
import com.garage.autoservice.repository.PlannedMaintenanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MaintenanceSchedulerServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private MaintenanceRecordRepository maintenanceRecordRepository;

    @Mock
    private PlannedMaintenanceRepository plannedMaintenanceRepository;

    @InjectMocks
    private  MaintenanceSchedulerService maintenanceSchedulerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void scheduleMaintenance_shouldScheduleNewJobs() {
        Car car = new Car();
        car.setMileage(120000L);
        car.setEngineHours(3000L);

        MaintenanceRecord record = new MaintenanceRecord();
        record.setMileage(110000);
        record.setHours(2900);
        record.setDate(new Date(System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000L)); // 10 дней назад
        record.setIntervalMileage(5000);
        record.setIntervalHours(200);
        record.setIntervalDays(7);

        when(carRepository.findAll()).thenReturn(List.of(car));
        when(maintenanceRecordRepository.findByCar(car)).thenReturn(List.of(record));

        maintenanceSchedulerService.scheduleMaintenance();

        verify(plannedMaintenanceRepository, times(1)).save(any(PlannedMaintenance.class));
    }
}
