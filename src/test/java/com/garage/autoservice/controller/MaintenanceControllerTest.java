package com.garage.autoservice.controller;

import com.garage.autoservice.entity.Car;
import com.garage.autoservice.entity.MaintenanceRecord;
import com.garage.autoservice.entity.PlannedMaintenance;
import com.garage.autoservice.repository.MaintenanceRecordRepository;
import com.garage.autoservice.repository.PlannedMaintenanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaintenanceController.class)
public class MaintenanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaintenanceRecordRepository maintenanceRecordRepository;

    @MockBean
    private PlannedMaintenanceRepository plannedMaintenanceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Car car;
    private MaintenanceRecord record;
    private PlannedMaintenance plannedMaintenance;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId(1L);
        car.setVin("1HGCM82633A123456");
        car.setMake("Toyota");

        record = new MaintenanceRecord();
        record.setId(1L);
        record.setCar(car);
        record.setJobName("Oil Change");
        record.setMileage(10000);
        record.setHours(500);
        record.setDate(new Date());
        record.setVin(car.getVin());

        plannedMaintenance = new PlannedMaintenance();
        plannedMaintenance.setId(1L);
        plannedMaintenance.setCar(car);
        plannedMaintenance.setJobName("Brake Check");
        plannedMaintenance.setPlannedDate(new Date());
    }

    @Test
    void testGetAllMaintenanceRecords() throws Exception {
        List<MaintenanceRecord> records = Arrays.asList(record);
        when(maintenanceRecordRepository.findAll()).thenReturn(records);

        mockMvc.perform(get("/api/maintenance/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].jobName").value(record.getJobName()));
    }

    @Test
    void testGetMaintenanceRecordsByVin() throws Exception {
        when(maintenanceRecordRepository.findByVin("1HGCM82633A123456")).thenReturn(Arrays.asList(record));

        mockMvc.perform(get("/api/maintenance/records/1HGCM82633A123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].jobName").value(record.getJobName()));
    }

//    @Test
//    void testGetMaintenanceRecordsByVin_NotFound() throws Exception {
//        when(maintenanceRecordRepository.findByVin("1HGCM82633A123456")).thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/api/maintenance/records/1HGCM82633A123456")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

    @Test
    void testAddMaintenanceRecord() throws Exception {
        when(maintenanceRecordRepository.save(any(MaintenanceRecord.class))).thenReturn(record);

        mockMvc.perform(post("/api/maintenance/records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jobName").value(record.getJobName()));
    }

    @Test
    void testGetAllPlannedMaintenance() throws Exception {
        List<PlannedMaintenance> planned = Arrays.asList(plannedMaintenance);
        when(plannedMaintenanceRepository.findAll()).thenReturn(planned);

        mockMvc.perform(get("/api/maintenance/planned")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].jobName").value(plannedMaintenance.getJobName()));
    }

    @Test
    void testScheduleMaintenance() throws Exception {
        when(plannedMaintenanceRepository.save(any(PlannedMaintenance.class))).thenReturn(plannedMaintenance);

        mockMvc.perform(post("/api/maintenance/planned")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plannedMaintenance)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jobName").value(plannedMaintenance.getJobName()));
    }
}
