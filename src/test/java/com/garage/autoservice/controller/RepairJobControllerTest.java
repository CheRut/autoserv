package com.garage.autoservice.controller;

import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.repository.RepairJobRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ActiveProfiles("test")
@WebMvcTest(RepairJobController.class)
public class RepairJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepairJobRepository repairJobRepository;

    @Test
    void createRepairJobTest() throws Exception {
        RepairJob repairJob = new RepairJob();
        repairJob.setId(1L);
        repairJob.setJobName("Oil Change");
        repairJob.setIntervalInDays(180);
        repairJob.setIntervalInMileage(10000);

        Mockito.when(repairJobRepository.save(Mockito.any(RepairJob.class))).thenReturn(repairJob);

        String json = "{\"jobName\": \"Oil Change\", \"intervalInDays\": 180, \"intervalInMileage\": 10000}";

        mockMvc.perform(post("/api/repair-jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobName").value("Oil Change"))
                .andExpect(jsonPath("$.intervalInDays").value(180))
                .andExpect(jsonPath("$.intervalInMileage").value(10000));
    }

    @Test
    void createRepairJobsBatchTest() throws Exception {
        RepairJob repairJob1 = new RepairJob(1L, "Oil Change", 180, 10000);
        RepairJob repairJob2 = new RepairJob(2L, "Brake Inspection", 365, 20000);

        Mockito.when(repairJobRepository.saveAll(Mockito.anyList())).thenReturn(Arrays.asList(repairJob1, repairJob2));

        String json = "[{\"jobName\": \"Oil Change\", \"intervalInDays\": 180, \"intervalInMileage\": 10000}," +
                "{\"jobName\": \"Brake Inspection\", \"intervalInDays\": 365, \"intervalInMileage\": 20000}]";

        mockMvc.perform(post("/api/repair-jobs/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].jobName").value("Oil Change"))
                .andExpect(jsonPath("$[1].jobName").value("Brake Inspection"));
    }

    @Test
    void getAllRepairJobsTest() throws Exception {
        RepairJob repairJob1 = new RepairJob(1L, "Oil Change", 180, 10000);
        RepairJob repairJob2 = new RepairJob(2L, "Brake Inspection", 365, 20000);

        Mockito.when(repairJobRepository.findAll()).thenReturn(Arrays.asList(repairJob1, repairJob2));

        mockMvc.perform(get("/api/repair-jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].jobName").value("Oil Change"))
                .andExpect(jsonPath("$[1].jobName").value("Brake Inspection"));
    }

    @Test
    void getRepairJobByIdTest() throws Exception {
        RepairJob repairJob = new RepairJob(1L, "Oil Change", 180, 10000);

        Mockito.when(repairJobRepository.findById(1L)).thenReturn(Optional.of(repairJob));

        mockMvc.perform(get("/api/repair-jobs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobName").value("Oil Change"))
                .andExpect(jsonPath("$.intervalInDays").value(180))
                .andExpect(jsonPath("$.intervalInMileage").value(10000));
    }

    @Test
    void updateRepairJobTest() throws Exception {
        RepairJob repairJob = new RepairJob(1L, "Oil Change", 180, 10000);

        Mockito.when(repairJobRepository.findById(1L)).thenReturn(Optional.of(repairJob));
        Mockito.when(repairJobRepository.save(Mockito.any(RepairJob.class))).thenReturn(repairJob);

        String json = "{\"jobName\": \"Brake Inspection\", \"intervalInDays\": 365, \"intervalInMileage\": 20000}";

        mockMvc.perform(put("/api/repair-jobs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobName").value("Brake Inspection"))
                .andExpect(jsonPath("$.intervalInDays").value(365))
                .andExpect(jsonPath("$.intervalInMileage").value(20000));
    }

    @Test
    void deleteRepairJobTest() throws Exception {
        RepairJob repairJob = new RepairJob(1L, "Oil Change", 180, 10000);

        Mockito.when(repairJobRepository.findById(1L)).thenReturn(Optional.of(repairJob));

        mockMvc.perform(delete("/api/repair-jobs/1"))
                .andExpect(status().isOk());

        Mockito.verify(repairJobRepository, Mockito.times(1)).delete(repairJob);
    }
}
