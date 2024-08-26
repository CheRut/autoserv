package com.garage.autoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.service.RepairJobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RepairJobController.class)
public class RepairJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepairJobService repairJobService;

    @Autowired
    private ObjectMapper objectMapper;

    private RepairJob repairJob;

    @BeforeEach
    void setUp() {
        repairJob = new RepairJob();
        repairJob.setId(1L);
        repairJob.setJobName("Oil Change");
        repairJob.setIntervalInDays(365L);
        repairJob.setIntervalInMileage(10000L);
        repairJob.setIntervalInHours(1000L);
        repairJob.setLastJobDate(LocalDate.now().minusDays(30));
        repairJob.setLastMileage(9000L);
    }

    @Test
    void testCreateRepairJob() throws Exception {
        RepairJobRequest repairJobRequest = new RepairJobRequest();
        repairJobRequest.setJobName("Oil Change");
        repairJobRequest.setIntervalInDays(365);
        repairJobRequest.setIntervalInMileage(10000);
        repairJobRequest.setIntervalInHours(1000);
        repairJobRequest.setLastJobDate(LocalDate.now().minusDays(30));
        repairJobRequest.setLastMileage(9000);

        when(repairJobService.createRepairJob(any(RepairJobRequest.class))).thenReturn(repairJob);

        mockMvc.perform(post("/api/repair-jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repairJobRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(repairJob.getId()))
                .andExpect(jsonPath("$.jobName").value(repairJob.getJobName()));
    }

    @Test
    void testCreateRepairJobsBatch() throws Exception {
        RepairJobRequest repairJobRequest = new RepairJobRequest();
        repairJobRequest.setJobName("Oil Change");
        repairJobRequest.setIntervalInDays(365);
        repairJobRequest.setIntervalInMileage(10000);
        repairJobRequest.setIntervalInHours(1000);
        repairJobRequest.setLastJobDate(LocalDate.now().minusDays(30));
        repairJobRequest.setLastMileage(9000);

        List<RepairJobRequest> repairJobRequests = Arrays.asList(repairJobRequest, repairJobRequest);
        List<RepairJob> repairJobs = Arrays.asList(repairJob, repairJob);

        when(repairJobService.createRepairJobsBatch(any(List.class))).thenReturn(repairJobs);

        mockMvc.perform(post("/api/repair-jobs/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repairJobRequests)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(repairJob.getId()))
                .andExpect(jsonPath("$[1].id").value(repairJob.getId()));
    }

    @Test
    void testUpdateRepairJob() throws Exception {
        RepairJobRequest repairJobRequest = new RepairJobRequest();
        repairJobRequest.setJobName("Oil Change");
        repairJobRequest.setIntervalInDays(365);
        repairJobRequest.setIntervalInMileage(10000);
        repairJobRequest.setIntervalInHours(1000);
        repairJobRequest.setLastJobDate(LocalDate.now().minusDays(30));
        repairJobRequest.setLastMileage(9000);

        when(repairJobService.updateRepairJob(anyLong(), any(RepairJobRequest.class))).thenReturn(repairJob);

        mockMvc.perform(put("/api/repair-jobs/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(repairJobRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(repairJob.getId()))
                .andExpect(jsonPath("$.jobName").value(repairJob.getJobName()));
    }
}
