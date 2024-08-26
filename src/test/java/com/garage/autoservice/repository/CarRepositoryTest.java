package com.garage.autoservice.repository;

import com.garage.autoservice.entity.Car;
import com.garage.autoservice.entity.Car.CarType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void testFindByVin() {
        Car car = new Car();
        car.setSerialNumber("123456");
        car.setEnterpriseNumber("ENT-001");
        car.setLicensePlate("ABC-123");
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setVin("1HGCM82633A123456");
        car.setEngineType("Petrol");
        car.setEngineNumber("ENG12345");
        car.setTransmissionType("Automatic");
        car.setTransmissionNumber("TRN67890");
        car.setYearOfManufacture(2020);
        car.setMileage(50000L);
        car.setCarType(CarType.PASSENGER_CAR);

        carRepository.save(car);

        Optional<Car> foundCar = carRepository.findByVin("1HGCM82633A123456");
        assertThat(foundCar).isPresent();
        assertThat(foundCar.get().getMake()).isEqualTo("Toyota");
    }

    @Test
    public void testSaveCar() {
        Car car = new Car();
        car.setSerialNumber("654321");
        car.setEnterpriseNumber("ENT-002");
        car.setLicensePlate("XYZ-987");
        car.setMake("Honda");
        car.setModel("Accord");
        car.setVin("1HGCM82633A987654");
        car.setEngineType("Diesel");
        car.setEngineNumber("ENG54321");
        car.setTransmissionType("Manual");
        car.setTransmissionNumber("TRN09876");
        car.setYearOfManufacture(2019);
        car.setMileage(40000L);
        car.setCarType(CarType.PASSENGER_CAR);

        Car savedCar = carRepository.save(car);
        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getId()).isNotNull();

        // Дополнительная проверка, чтобы убедиться, что объект сохранен в базе данных
        Optional<Car> foundCar = carRepository.findById(savedCar.getId());
        assertThat(foundCar).isPresent();
        assertThat(foundCar.get().getVin()).isEqualTo("1HGCM82633A987654");
    }

    @Test
    public void testDeleteCar() {
        Car car = new Car();
        car.setSerialNumber("111111");
        car.setEnterpriseNumber("ENT-003");
        car.setLicensePlate("DEF-456");
        car.setMake("Ford");
        car.setModel("Focus");
        car.setVin("1HGCM82633A111111");
        car.setEngineType("Hybrid");
        car.setEngineNumber("ENG11111");
        car.setTransmissionType("Automatic");
        car.setTransmissionNumber("TRN11111");
        car.setYearOfManufacture(2018);
        car.setMileage(30000L);
        car.setCarType(CarType.PASSENGER_CAR);

        Car savedCar = carRepository.save(car);
        carRepository.delete(savedCar);

        Optional<Car> foundCar = carRepository.findByVin("1HGCM82633A111111");
        assertThat(foundCar).isEmpty();

        // Дополнительная проверка, чтобы убедиться, что объект действительно удален
        Optional<Car> foundDeletedCar = carRepository.findById(savedCar.getId());
        assertThat(foundDeletedCar).isEmpty();
    }
}
