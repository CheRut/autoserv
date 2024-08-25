package com.garage.autoservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.repository.PartRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartController.class)
public class PartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartRepository partRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Part part;
    private PartRequest partRequest;

    @BeforeEach
    void setUp() {
        part = new Part();
        part.setId(1L);
        part.setName("Oil Filter");
        part.setManufacturer("Bosch");
        part.setPartNumber("OF123");
        part.setQuantity(50);
        part.setPrice(15.99);
        part.setVin("1HGCM82633A123456");

        partRequest = new PartRequest();
        partRequest.setName("Oil Filter");
        partRequest.setManufacturer("Bosch");
        partRequest.setPartNumber("OF123");
        partRequest.setQuantity(50);
        partRequest.setPrice(15.99);
        partRequest.setVin("1HGCM82633A123456");
    }

    @Test
    void testCreatePart() throws Exception {
        Part savedPart = new Part();
        savedPart.setId(1L);
        savedPart.setName(partRequest.getName());
        savedPart.setManufacturer(partRequest.getManufacturer());
        savedPart.setPartNumber(partRequest.getPartNumber());
        savedPart.setQuantity(partRequest.getQuantity());
        savedPart.setPrice(partRequest.getPrice());
        savedPart.setVin(partRequest.getVin());

        when(partRepository.save(any(Part.class))).thenReturn(savedPart);

        mockMvc.perform(post("/api/parts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedPart.getId()))
                .andExpect(jsonPath("$.name").value(savedPart.getName()));
    }

    @Test
    void testCreatePartsBatch() throws Exception {
        List<PartRequest> partRequests = Arrays.asList(partRequest, partRequest);
        Part savedPart1 = new Part();
        savedPart1.setId(1L);
        savedPart1.setName(partRequest.getName());
        savedPart1.setManufacturer(partRequest.getManufacturer());
        savedPart1.setPartNumber(partRequest.getPartNumber());
        savedPart1.setQuantity(partRequest.getQuantity());
        savedPart1.setPrice(partRequest.getPrice());
        savedPart1.setVin(partRequest.getVin());

        Part savedPart2 = new Part();
        savedPart2.setId(2L);
        savedPart2.setName(partRequest.getName());
        savedPart2.setManufacturer(partRequest.getManufacturer());
        savedPart2.setPartNumber(partRequest.getPartNumber());
        savedPart2.setQuantity(partRequest.getQuantity());
        savedPart2.setPrice(partRequest.getPrice());
        savedPart2.setVin(partRequest.getVin());

        when(partRepository.save(any(Part.class))).thenReturn(savedPart1, savedPart2);

        mockMvc.perform(post("/api/parts/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partRequests)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(savedPart1.getId()))
                .andExpect(jsonPath("$[0].name").value(savedPart1.getName()))
                .andExpect(jsonPath("$[1].id").value(savedPart2.getId()))
                .andExpect(jsonPath("$[1].name").value(savedPart2.getName()));
    }

    @Test
    void testGetAllParts() throws Exception {
        List<Part> parts = Arrays.asList(part, part);

        when(partRepository.findAll()).thenReturn(parts);

        mockMvc.perform(get("/api/parts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetPartById() throws Exception {
        when(partRepository.findById(1L)).thenReturn(Optional.of(part));

        mockMvc.perform(get("/api/parts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(part.getId()));
    }

    @Test
    void testUpdatePart() throws Exception {
        when(partRepository.findById(1L)).thenReturn(Optional.of(part));
        when(partRepository.save(any(Part.class))).thenReturn(part);

        part.setName("Air Filter");

        mockMvc.perform(put("/api/parts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(part)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Air Filter"));
    }

    @Test
    void testDeletePart() throws Exception {
        when(partRepository.findById(1L)).thenReturn(Optional.of(part));

        mockMvc.perform(delete("/api/parts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(partRepository, Mockito.times(1)).delete(part);
    }
}
