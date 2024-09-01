package com.garage.autoservice.controller;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.service.RepairJobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

class RepairJobControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RepairJobService repairJobService;

    @InjectMocks
    private RepairJobController repairJobController;

    private ObjectMapper objectMapper;
    private RepairJob repairJob;
    private RepairJobRequest repairJobRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repairJobController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Добавлено

        repairJob = new RepairJob();
        repairJob.setId(1L);
        repairJob.setJobName("Oil Change");
        repairJob.setIntervalInMileage(10000L);
        repairJob.setIntervalInHours(500L);
        repairJob.setIntervalInDays(365L);
        repairJob.setLastMileage(5000L);
        repairJob.setLastJobDate(LocalDate.now().minusDays(180));
        repairJob.setSerialNumber("VIN123");

        PartRequest part1 = new PartRequest();
        part1.setName("Oil Filter");
        part1.setManufacturer("ACME Corp");
        part1.setPartNumber("OF123");
        part1.setQuantity(1);
        part1.setCardNumber(4100);
        part1.setVin("VIN123");

        PartRequest part2 = new PartRequest();
        part2.setName("Air Filter");
        part2.setManufacturer("ACME Corp");
        part2.setPartNumber("AF456");
        part2.setQuantity(1);
        part2.setCardNumber(4101);
        part2.setVin("VIN123");

        List<PartRequest> requiredParts = new ArrayList<>();
        requiredParts.add(part1);
        requiredParts.add(part2);
        repairJobRequest = new RepairJobRequest();
        repairJobRequest.setJobName("Oil Change");
        repairJobRequest.setIntervalInMileage(10000L);
        repairJobRequest.setIntervalInHours(500L);
        repairJobRequest.setIntervalInDays(365L);
        repairJobRequest.setLastMileage(5000L);
        repairJobRequest.setLastJobDate(LocalDate.now().minusDays(180));
        repairJobRequest.setSerialNumber("VIN123");
        repairJobRequest.setRequiredParts(requiredParts); // предполагая, что детали не требуются
    }

    @Test
    void createRepairJob_ShouldReturnCreatedRepairJob() throws Exception {
        given(repairJobService.createRepairJob(any(RepairJobRequest.class))).willReturn(repairJob);

        mockMvc.perform(post("/api/repair-jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repairJobRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(repairJob.getId().intValue())))
                .andExpect(jsonPath("$.jobName", is(repairJob.getJobName())));

        verify(repairJobService, times(1)).createRepairJob(any(RepairJobRequest.class));
    }

    @Test
    void createRepairJob_ShouldReturnServerError_WhenExceptionThrown() throws Exception {
        given(repairJobService.createRepairJob(any(RepairJobRequest.class)))
                .willThrow(new IllegalStateException("Error saving"));

        mockMvc.perform(post("/api/repair-jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repairJobRequest)))
                .andExpect(status().isInternalServerError());

        verify(repairJobService, times(1)).createRepairJob(any(RepairJobRequest.class));
    }

    @Test
    void getRepairJobById_ShouldReturnRepairJob_WhenIdExists() throws Exception {
        given(repairJobService.getRepairJobById(anyLong())).willReturn(repairJob);

        mockMvc.perform(get("/api/repair-jobs/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(repairJob.getId().intValue())))
                .andExpect(jsonPath("$.jobName", is(repairJob.getJobName())));

        verify(repairJobService, times(1)).getRepairJobById(1L);
    }

    @Test
    void getRepairJobById_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        given(repairJobService.getRepairJobById(anyLong())).willThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/repair-jobs/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not found"));

        verify(repairJobService, times(1)).getRepairJobById(1L);
    }

    @Test
    void updateRepairJob_ShouldReturnUpdatedRepairJob() throws Exception {
        // Настройка мока
        given(repairJobService.updateRepairJob(anyLong(), any(RepairJobRequest.class))).willReturn(repairJob);

        // Выполнение запроса и проверка
        mockMvc.perform(put("/api/repair-jobs/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repairJobRequest)))
                .andDo(result -> {
                    System.out.println("Response Status: " + result.getResponse().getStatus());
                    System.out.println("Response Content: " + result.getResponse().getContentAsString());
                })
                .andExpect(status().isOk()) // Ожидаем, что будет 200 OK
                .andExpect(jsonPath("$.id", is(repairJob.getId().intValue())))
                .andExpect(jsonPath("$.jobName", is(repairJob.getJobName())));

        // Проверка вызова сервиса
        verify(repairJobService, times(1)).updateRepairJob(anyLong(), any(RepairJobRequest.class));
    }

    @Test
    void getAllRepairJobs_ShouldReturnListOfRepairJobs() throws Exception {
        given(repairJobService.getAllRepairJobs()).willReturn(Collections.singletonList(repairJob));

        mockMvc.perform(get("/api/repair-jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(repairJob.getId().intValue())))
                .andExpect(jsonPath("$[0].jobName", is(repairJob.getJobName())));

        verify(repairJobService, times(1)).getAllRepairJobs();
    }

    @Test
    void getJobsForVehicleInPeriod_ShouldReturnListOfRepairJobs() throws Exception {
        given(repairJobService.getJobsForVehicleInPeriod(anyString(), any(LocalDate.class), any(LocalDate.class)))
                .willReturn(Collections.singletonList(repairJob));

        mockMvc.perform(get("/api/repair-jobs/by-period")
                        .param("serialNumber", "VIN123")
                        .param("startDate", "2023-09-01")
                        .param("endDate", "2024-08-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(repairJob.getId().intValue())))
                .andExpect(jsonPath("$[0].jobName", is(repairJob.getJobName())));

        verify(repairJobService, times(1)).getJobsForVehicleInPeriod(anyString(), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void getJobsForVehicleInPeriod_ShouldReturnEmptyList_WhenNoJobsExist() throws Exception {
        given(repairJobService.getJobsForVehicleInPeriod(anyString(), any(LocalDate.class), any(LocalDate.class)))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/repair-jobs/by-period")
                        .param("serialNumber", "VIN123")
                        .param("startDate", "2023-09-01")
                        .param("endDate", "2024-08-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(repairJobService, times(1)).getJobsForVehicleInPeriod(anyString(), any(LocalDate.class), any(LocalDate.class));
    }
}
