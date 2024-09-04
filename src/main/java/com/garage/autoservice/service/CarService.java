package com.garage.autoservice.service;

import com.garage.autoservice.entity.Car;
import com.garage.autoservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс для управления сущностью Car.
 * Предоставляет методы для сохранения, удаления и получения данных о автомобилях.
 */
@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Сохраняет или обновляет информацию о автомобиле в базе данных.
     *
     * @param car объект Car для сохранения
     * @return сохраненный объект Car
     */
    public Car save(Car car) {
        return carRepository.save(car);
    }

    /**
     * Удаляет информацию о автомобиле из базы данных.
     *
     * @param car объект Car для удаления
     */
    public void delete(Car car) {
        carRepository.delete(car);
    }

    /**
     * Получает список всех автомобилей из базы данных.
     *
     * @return список объектов Car
     */
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    /**
     * Ищет автомобиль по его идентификатору.
     *
     * @param id идентификатор автомобиля
     * @return объект Optional с автомобилем, если он найден
     */
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    public Optional<Car> findByEnterpriseNumber(String enterpriseNumber) {
        return carRepository.findByEnterpriseNumber(enterpriseNumber);
    }

    public Optional<Car> findBySerialNumber(String serialNumber) {
        return carRepository.findByEnterpriseNumber(serialNumber);
    }

    /**
     * Метод для получения всех автомобилей из базы данных.
     * @return список всех автомобилей
     */
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }
}
