package com.garage.autoservice.controller;

import com.garage.autoservice.entity.*;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        // Создание объекта Car
        Car car = new Car();

// Установка значений для всех необходимых полей
        car.setSerialNumber("12345ABC");
        car.setEnterpriseNumber("98765XYZ");
        car.setLicensePlate("AB123CD");
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setEngineType("Petrol");
        car.setEngineNumber("ENG123456789");
        car.setTransmissionType("Automatic");
        car.setTransmissionNumber("TRN123456789");
        car.setYearOfManufacture(2020);
        car.setVin("1HGCM82633A123456");
        car.setMileage(25000L);
        car.setEngineHours(1200L);
        car.setCarType(Car.CarType.PASSENGER_CAR);
        car.setLastMileage(24000L);
        car.setLastServiceDate(LocalDate.now().minusMonths(6));

// Создание и установка списка выполненных и запланированных работ (если применимо)
        List<MaintenanceRecord> maintenanceRecords = new ArrayList<>();
// Добавьте необходимые объекты MaintenanceRecord в список
        car.setMaintenanceRecords(maintenanceRecords);

// Создание и установка списка запчастей, связанных с автомобилем (если применимо)
        List<Part> parts = new ArrayList<>();
// Добавьте необходимые объекты Part в список
        car.setParts(parts);

        MaintenanceRecord maintenanceRecord = new MaintenanceRecord();
// Установка значений для всех необходимых полей
        maintenanceRecord.setCar(car); // Установите объект Car в соответствии с вашей логикой
        maintenanceRecord.setVin("1HGCM82633A123456");
        maintenanceRecord.setJobName("Oil Change");
        maintenanceRecord.setMileage(15000);
        maintenanceRecord.setHours(500);
        maintenanceRecord.setDate(new Date());

// Создание и установка списка использованных запчастей (если применимо)
        List<UsedParts> usedPartsList = new ArrayList<>();
// Добавьте необходимые объекты UsedParts в список
        maintenanceRecord.setUsedParts(usedPartsList);

// Создание и установка списка использованных жидкостей (если применимо)
        List<UsedFluid> usedFluidsList = new ArrayList<>();
// Добавьте необходимые объекты UsedFluid в список
        maintenanceRecord.setUsedFluids(usedFluidsList);

// Установка интервалов для планирования следующей работы
        maintenanceRecord.setIntervalMileage(10000);
        maintenanceRecord.setIntervalHours(500);
        maintenanceRecord.setIntervalDays(180);


        usedPart = new UsedParts();
        usedPart.setId(1L);
        usedPart.setName("Масляный фильтр");
        usedPart.setManufacturer("Bosch");
        usedPart.setPartNumber("F026407123");
        usedPart.setQuantity(1);
        usedPart.setCardNumber("PART-001");
        usedPart.setMaintenanceRecord(maintenanceRecord);
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
