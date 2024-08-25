package com.garage.autoservice.controller;

import com.garage.autoservice.entity.Car;
import com.garage.autoservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления операциями с автомобилями.
 * Предоставляет API для выполнения CRUD-операций с сущностью {@link Car}.
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    /**
     * Получить список всех автомобилей.
     *
     * @return список всех автомобилей
     */
    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Добавление одного автомобиля
     * @param car список автомобилей для создания
     * @return созданный автомобиль
     * */
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carRepository.save(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }


    /**
     * Создать новые автомобили.
     *
     * @param cars список автомобилей для создания
     * @return список созданных автомобилей
     */
    @PostMapping("/batch")
    public ResponseEntity<List<Car>> createCars(@RequestBody List<Car> cars) {
        List<Car> savedCars = carRepository.saveAll(cars);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCars);
    }

    /**
     * Получить автомобиль по идентификатору.
     *
     * @param id идентификатор автомобиля
     * @return объект {@link ResponseEntity}, содержащий автомобиль или статус 404, если автомобиль не найден
     */
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Обновить информацию об автомобиле.
     *
     * @param id идентификатор автомобиля для обновления
     * @param carDetails объект с обновленными данными автомобиля
     * @return объект {@link ResponseEntity}, содержащий обновленный автомобиль или статус 404, если автомобиль не найден
     */
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        return carRepository.findById(id)
                .map(car -> {
                    car.setSerialNumber(carDetails.getSerialNumber());
                    car.setEnterpriseNumber(carDetails.getEnterpriseNumber());
                    car.setLicensePlate(carDetails.getLicensePlate());
                    car.setMake(carDetails.getMake());
                    car.setModel(carDetails.getModel());
                    car.setVin(carDetails.getVin());
                    car.setEngineType(carDetails.getEngineType());
                    car.setEngineNumber(carDetails.getEngineNumber());
                    car.setTransmissionType(carDetails.getTransmissionType());
                    car.setTransmissionNumber(carDetails.getTransmissionNumber());
                    car.setYearOfManufacture(carDetails.getYearOfManufacture());
                    car.setMileage(carDetails.getMileage());
                    car.setEngineHours(carDetails.getEngineHours());
                    car.setCarType(carDetails.getCarType());
                    Car updatedCar = carRepository.save(car);
                    return ResponseEntity.ok(updatedCar);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Удалить автомобиль по идентификатору.
     *
     * @param id идентификатор автомобиля для удаления
     * @return объект {@link ResponseEntity} со статусом 200, если удаление успешно, или статус 404, если автомобиль не найден
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(car -> {
                    carRepository.delete(car);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
