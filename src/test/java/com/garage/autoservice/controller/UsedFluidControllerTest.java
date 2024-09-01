package com.garage.autoservice.controller;

import com.garage.autoservice.entity.*;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        usedFluid = new UsedFluid();
        usedFluid.setId(1L);
        usedFluid.setType("Моторное масло");
        usedFluid.setBrand("Castrol");
        usedFluid.setVolume(4.0);
        usedFluid.setCardNumber("FLUID-001");
//        usedFluid.setMaintenanceRecord(maintenanceRecord);
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
