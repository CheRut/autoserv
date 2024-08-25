package com.garage.autoservice.controller;

import com.garage.autoservice.dto.PartRequest;
import com.garage.autoservice.entity.Car;
import com.garage.autoservice.entity.Part;
import com.garage.autoservice.repository.CarRepository;
import com.garage.autoservice.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST-контроллер для управления операциями с запчастями.
 * Предоставляет API для выполнения CRUD-операций с сущностью {@link Part}.
 */
@RestController
@RequestMapping("/api/parts")
public class PartController {

    @Autowired
    private PartRepository partRepository;

//    @Autowired
//    private CarRepository carRepository;

    /**
     * Получить список всех запчастей.
     *
     * @return список всех запчастей
     */
    @GetMapping
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    /**
     * Создать новую запчасть.
     *
     * @param request запрос с данными запчасти для создания
     * @return созданная запчасть
     */
    @PostMapping
    public Part createPart(@RequestBody PartRequest request) {
        Part part = new Part();
        part.setName(request.getName());
        part.setManufacturer(request.getManufacturer());
        part.setPartNumber(request.getPartNumber());
        part.setQuantity(request.getQuantity());
        part.setPrice(request.getPrice());
        part.setVin(request.getVin());

        return partRepository.save(part);
    }

        /**
         * Создать новые запчасти и связать их с автомобилями по VIN-коду.
         *
         * @param requests список запросов на создание запчастей
         * @return список созданных запчастей
         * @throws IllegalArgumentException если автомобиль с указанным VIN не найден
         */
    @PostMapping("/batch")
    public List<Part> createParts(@RequestBody List<PartRequest> requests) {
        return requests.stream().map(request -> {
            Part part = new Part();
            part.setName(request.getName());
            part.setManufacturer(request.getManufacturer());
            part.setPartNumber(request.getPartNumber());
            part.setQuantity(request.getQuantity());
            part.setPrice(request.getPrice());
            part.setVin(request.getVin());

            Part savedPart = partRepository.save(part);
            return savedPart;
        }).collect(Collectors.toList());
    }

    /**
     * Получить запчасть по идентификатору.
     *
     * @param id идентификатор запчасти
     * @return объект {@link ResponseEntity}, содержащий запчасть или статус 404, если запчасть не найдена
     */
    @GetMapping("/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable Long id) {
        return partRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Обновить информацию о запчасти.
     *
     * @param id идентификатор запчасти для обновления
     * @param partDetails объект с обновленными данными запчасти
     * @return объект {@link ResponseEntity}, содержащий обновленную запчасть или статус 404, если запчасть не найдена
     * @throws IllegalArgumentException если автомобиль с указанным VIN не найден или тип автомобиля не соответствует
     */
    @PutMapping("/{id}")
    public ResponseEntity<Part> updatePart(@PathVariable Long id, @RequestBody Part partDetails) {
        return partRepository.findById(id)
                .map(part -> {
                    part.setName(partDetails.getName());
                    part.setManufacturer(partDetails.getManufacturer());
                    part.setPartNumber(partDetails.getPartNumber());
                    part.setQuantity(partDetails.getQuantity());
                    part.setPrice(partDetails.getPrice());

                    if (partDetails.getVin() != null) {
                        part.setVin(partDetails.getVin());
                    }

                    Part updatedPart = partRepository.save(part);
                    return ResponseEntity.ok(updatedPart);
                }).orElse(ResponseEntity.notFound().build());
    }



    /**
     * Удалить запчасть по идентификатору.
     *
     * @param id идентификатор запчасти для удаления
     * @return объект {@link ResponseEntity} со статусом 200, если удаление успешно, или статус 404, если запчасть не найдена
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable Long id) {
        return partRepository.findById(id)
                .map(part -> {
                    partRepository.delete(part);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
