package com.garage.autoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.dto.RepairJobRequest;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.entity.RepairJob;
import com.garage.autoservice.repository.PartRepository;
import com.garage.autoservice.repository.RepairJobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepairJobController.class)
public class RepairJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepairJobRepository repairJobRepository;

    @MockBean
    private PartRepository partRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private RepairJob repairJob;
    private RepairJobRequest repairJobRequest;
    private Part part1;
    private Part part2;

    @BeforeEach
    void setUp() {
        part1 = new Part();
        part1.setId(1L);
        part1.setName("Oil Filter");
        part1.setManufacturer("Bosch");
        part1.setPartNumber("OF123");
        part1.setQuantity(1);
        part1.setPrice(15.99);
        part1.setVin("1HGCM82633A123456");

        part2 = new Part();
        part2.setId(2L);
        part2.setName("Engine Oil");
        part2.setManufacturer("Shell");
        part2.setPartNumber("EO456");
        part2.setQuantity(5);
        part2.setPrice(9.99);
        part2.setVin("1HGCM82633A123456");

        repairJob = new RepairJob();
        repairJob.setId(1L);
        repairJob.setJobName("Oil Change");
        repairJob.setIntervalInDays(365);
        repairJob.setIntervalInMileage(10000);
        repairJob.setRequiredParts(Arrays.asList(part1, part2));
        repairJob.setPartQuantities(Arrays.asList(1, 5));

        PartRequest partRequest1 = new PartRequest();
        partRequest1.setName("Oil Filter");
        partRequest1.setManufacturer("Bosch");
        partRequest1.setPartNumber("OF123");
        partRequest1.setQuantity(1);
        partRequest1.setPrice(15.99);
        partRequest1.setVin("1HGCM82633A123456");

        PartRequest partRequest2 = new PartRequest();
        partRequest2.setName("Engine Oil");
        partRequest2.setManufacturer("Shell");
        partRequest2.setPartNumber("EO456");
        partRequest2.setQuantity(5);
        partRequest2.setPrice(9.99);
        partRequest2.setVin("1HGCM82633A123456");

        repairJobRequest = new RepairJobRequest();
        repairJobRequest.setJobName("Oil Change");
        repairJobRequest.setIntervalInDays(365);
        repairJobRequest.setIntervalInMileage(10000);
        repairJobRequest.setRequiredParts(Arrays.asList(partRequest1, partRequest2));
        repairJobRequest.setPartQuantities(Arrays.asList(1, 5));
    }

    @Test
    void testCreateRepairJobsBatch() throws Exception {
        when(partRepository.save(any(Part.class))).thenReturn(part1, part2);
        when(repairJobRepository.save(any(RepairJob.class))).thenReturn(repairJob);

        mockMvc.perform(post("/api/repair-jobs/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(repairJobRequest))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].jobName").value("Oil Change"))
                .andExpect(jsonPath("$[0].requiredParts.length()").value(2))
                .andExpect(jsonPath("$[0].requiredParts[0].name").value("Oil Filter"))
                .andExpect(jsonPath("$[0].requiredParts[1].name").value("Engine Oil"));
    }
}
