package com.garage.autoservice.controller;

import com.garage.autoservice.entity.Car;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST-контроллер для управления операциями с автомобилями.
 * Предоставляет API для выполнения CRUD-операций с сущностью {@link Car}.
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);
    @Autowired
    private CarRepository carRepository;

    /**
     * Получить список всех автомобилей.
     *
     * @return список всех автомобилей
     */
    @GetMapping
    public List<Car> getAllCars() {
        logger.info("Запрос на получение списка всех автомобилей");
        return carRepository.findAll();
    }

    /**
     * Добавление одного автомобиля
     * @param car список автомобилей для создания
     * @return созданный автомобиль
     * */
    @PostMapping
    public ResponseEntity<Car> createCar(@Valid @RequestBody Car car) {
        logger.info("Создание нового автомобиля с VIN: {}", car.getVin());
        Car savedCar = carRepository.save(car);
        logger.debug("Автомобиль создан: {}", savedCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    /**
     * Создать новые автомобили.
     *
     * @param cars список автомобилей для создания
     * @return список созданных автомобилей
     */
    @PostMapping("/batch")
    public ResponseEntity<List<Car>> createCars(@Valid @RequestBody List<Car> cars) {
        logger.info("Создание нескольких автомобилей, количество: {}", cars.size());
        List<Car> savedCars = carRepository.saveAll(cars);
        logger.debug("Автомобили созданы: {}", savedCars);
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
        logger.info("Запрос на получение автомобиля с ID: {}", id);
        Car car = carRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Автомобиль с ID {} не найден", id);
                    return new ResourceNotFoundException("Автомобиль с ID " + id + " не найден");
                });
        return ResponseEntity.ok(car);
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
        logger.info("Запрос на обновление автомобиля с ID: {}", id);
        Car updatedCar = carRepository.findById(id)
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
                    Car savedCar = carRepository.save(car);
                    logger.debug("Автомобиль обновлен: {}", savedCar);
                    return savedCar;
                }).orElseThrow(() -> {
                    logger.error("Автомобиль с ID {} не найден для обновления", id);
                    return new ResourceNotFoundException("Автомобиль с ID " + id + " не найден");
                });
        return ResponseEntity.ok(updatedCar);
    }

    /**
     * Удалить автомобиль по идентификатору.
     *
     * @param id идентификатор автомобиля для удаления
     * @return объект {@link ResponseEntity} со статусом 200, если удаление успешно, или статус 404, если автомобиль не найден
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        logger.info("Запрос на удаление автомобиля с ID: {}", id);
        return carRepository.findById(id)
                .map(car -> {
                    carRepository.delete(car);
                    logger.debug("Автомобиль с ID {} успешно удален", id);
                    return ResponseEntity.ok().<Void>build();  // Возвращаем успешный ответ
                })
                .orElseThrow(() -> {
                    logger.error("Автомобиль с ID {} не найден для удаления", id);
                    return new ResourceNotFoundException("Автомобиль с ID " + id + " не найден");
                });
    }

}
