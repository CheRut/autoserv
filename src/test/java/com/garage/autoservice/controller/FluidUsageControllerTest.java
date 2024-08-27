package com.garage.autoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garage.autoservice.dto.FluidUsageRequest;
import com.garage.autoservice.entity.FluidUsage;
import com.garage.autoservice.service.FluidUsageService;
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

@WebMvcTest(FluidUsageController.class)
public class FluidUsageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FluidUsageService fluidUsageService;

    @Autowired
    private ObjectMapper objectMapper;

    private FluidUsage fluidUsage;
    private FluidUsageRequest fluidUsageRequest;

    @BeforeEach
    void setUp() {
        fluidUsage = new FluidUsage();
        fluidUsage.setId(1L);
        fluidUsage.setSerialNumber("123ABC");
        fluidUsage.setDate(LocalDate.now());
        fluidUsage.setFluidVolume(5.0);
        fluidUsage.setFluidType("Engine Oil");
        fluidUsage.setFluidBrand("Castrol");
        fluidUsage.setMileage(12000);

        fluidUsageRequest = new FluidUsageRequest();
        fluidUsageRequest.setSerialNumber("123ABC");
        fluidUsageRequest.setDate(LocalDate.now());
        fluidUsageRequest.setFluidVolume(5.0);
        fluidUsageRequest.setFluidType("Engine Oil");
        fluidUsageRequest.setFluidBrand("Castrol");
        fluidUsageRequest.setMileage(12000L);
    }

    @Test
    void testCreateFluidUsage() throws Exception {
        when(fluidUsageService.createFluidUsage(any(FluidUsageRequest.class))).thenReturn(fluidUsage);

        mockMvc.perform(post("/api/fluid-usage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fluidUsageRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fluidUsage.getId()))
                .andExpect(jsonPath("$.serialNumber").value(fluidUsage.getSerialNumber()));
    }

    @Test
    void testGetAllFluidUsageRecords() throws Exception {
        List<FluidUsage> fluidUsages = Arrays.asList(fluidUsage, fluidUsage);

        when(fluidUsageService.getAllFluidUsageRecords()).thenReturn(fluidUsages);

        mockMvc.perform(get("/api/fluid-usage")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetFluidUsageById() throws Exception {
        when(fluidUsageService.getFluidUsageById(anyLong())).thenReturn(java.util.Optional.of(fluidUsage));

        mockMvc.perform(get("/api/fluid-usage/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fluidUsage.getId()));
    }

    @Test
    void testUpdateFluidUsage() throws Exception {
        // Здесь нужно убедиться, что fluidUsage корректно отражает обновления
        fluidUsage.setFluidType("Coolant");
        when(fluidUsageService.updateFluidUsage(anyLong(), any(FluidUsageRequest.class))).thenReturn(fluidUsage);

        mockMvc.perform(put("/api/fluid-usage/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fluidUsageRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fluidType").value("Coolant"));
    }


    @Test
    void testDeleteFluidUsage() throws Exception {
        Mockito.doNothing().when(fluidUsageService).deleteFluidUsage(anyLong());

        mockMvc.perform(delete("/api/fluid-usage/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(fluidUsageService, Mockito.times(1)).deleteFluidUsage(1L);
    }
}
