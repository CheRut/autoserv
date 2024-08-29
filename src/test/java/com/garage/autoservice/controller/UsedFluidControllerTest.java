package com.garage.autoservice.controller;

import com.garage.autoservice.entity.UsedFluid;
import com.garage.autoservice.service.UsedFluidService;
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

@WebMvcTest(UsedFluidController.class)
public class UsedFluidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsedFluidService usedFluidService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsedFluid usedFluid;

    @BeforeEach
    void setUp() {
        usedFluid = new UsedFluid();
        usedFluid.setId(1L);
        usedFluid.setType("Моторное масло");
        usedFluid.setBrand("Castrol");
        usedFluid.setVolume(4.0);
        usedFluid.setCardNumber("FLUID-001");
    }

    @Test
    void testCreateUsedFluid() throws Exception {
        when(usedFluidService.createUsedFluid(any(UsedFluid.class))).thenReturn(usedFluid);

        mockMvc.perform(post("/api/used-fluids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usedFluid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usedFluid.getId()))
                .andExpect(jsonPath("$.type").value(usedFluid.getType()));
    }

    @Test
    void testGetAllUsedFluids() throws Exception {
        when(usedFluidService.getAllUsedFluids()).thenReturn(List.of(usedFluid));

        mockMvc.perform(get("/api/used-fluids")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value(usedFluid.getType()));
    }

    @Test
    void testUpdateUsedFluid() throws Exception {
        when(usedFluidService.updateUsedFluid(Mockito.eq(1L), any(UsedFluid.class))).thenReturn(usedFluid);

        usedFluid.setType("Обновленная жидкость");

        mockMvc.perform(put("/api/used-fluids/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usedFluid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Обновленная жидкость"));
    }

    @Test
    void testDeleteUsedFluid() throws Exception {
        mockMvc.perform(delete("/api/used-fluids/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
