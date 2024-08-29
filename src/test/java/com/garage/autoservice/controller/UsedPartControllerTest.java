package com.garage.autoservice.controller;

import com.garage.autoservice.entity.UsedParts;
import com.garage.autoservice.service.UsedPartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsedPartController.class)
public class UsedPartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsedPartService usedPartService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsedParts usedPart;

    @BeforeEach
    void setUp() {
        usedPart = new UsedParts();
        usedPart.setId(1L);
        usedPart.setName("Масляный фильтр");
        usedPart.setManufacturer("Bosch");
        usedPart.setPartNumber("F026407123");
        usedPart.setQuantity(1);
        usedPart.setCardNumber("PART-001");
    }

    @Test
    void testCreateUsedPart() throws Exception {
        when(usedPartService.createUsedPart(any(UsedParts.class))).thenReturn(usedPart);

        mockMvc.perform(post("/api/used-parts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usedPart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usedPart.getId()))
                .andExpect(jsonPath("$.name").value(usedPart.getName()));
    }

    @Test
    void testGetAllUsedParts() throws Exception {
        when(usedPartService.getAllUsedParts()).thenReturn(List.of(usedPart));

        mockMvc.perform(get("/api/used-parts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(usedPart.getName()));
    }

    @Test
    void testUpdateUsedPart() throws Exception {
        when(usedPartService.updateUsedPart(Mockito.eq(1L), any(UsedParts.class))).thenReturn(usedPart);

        usedPart.setName("Обновленный фильтр");

        mockMvc.perform(put("/api/used-parts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usedPart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Обновленный фильтр"));
    }

    @Test
    void testDeleteUsedPart() throws Exception {
        mockMvc.perform(delete("/api/used-parts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
