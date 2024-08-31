package com.garage.autoservice.controller;

import com.garage.autoservice.entity.Car;
import com.garage.autoservice.exception.GlobalExceptionHandler;
import com.garage.autoservice.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
@Import(GlobalExceptionHandler.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Car car;

    @BeforeEach
    void setUp() {

        car = new Car();
        car.setId(1L);
        car.setSerialNumber("1234567890");
        car.setEnterpriseNumber("123");
        car.setLicensePlate("ABC123");
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setVin("1HGCM82633A123456");
        car.setEngineType("Gasoline");
        car.setEngineNumber("ENG123456");
        car.setTransmissionType("Automatic");
        car.setTransmissionNumber("TR123456");
        car.setYearOfManufacture(2020);
        car.setMileage(50000L);
        car.setEngineHours(null);
        car.setCarType(Car.CarType.PASSENGER_CAR);
    }

    @Test
    void testCreateCar() throws Exception {
        when(carRepository.save(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.serialNumber").value(car.getSerialNumber()));
    }

    @Test
    void testCreateCarsBatch() throws Exception {
        List<Car> cars = Arrays.asList(car, car);

        when(carRepository.saveAll(any(List.class))).thenReturn(cars);

        mockMvc.perform(post("/api/cars/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cars)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(car.getId()));
    }

    @Test
    void testGetAllCars() throws Exception {
        List<Car> cars = Arrays.asList(car, car);

        when(carRepository.findAll()).thenReturn(cars);

        mockMvc.perform(get("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetCarById() throws Exception {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(get("/api/cars/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()));
    }



    @Test
    void testUpdateCar() throws Exception {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        car.setMake("Honda");

        mockMvc.perform(put("/api/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Honda"));
    }



    @Test
    void testDeleteCar() throws Exception {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(delete("/api/cars/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(carRepository, times(1)).delete(car);
    }


}
