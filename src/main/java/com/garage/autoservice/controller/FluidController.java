package com.garage.autoservice.controller;

import com.garage.autoservice.entity.Fluid;
import com.garage.autoservice.repository.FluidRepository;
import com.garage.autoservice.service.FluidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST-контроллер для управления сущностью Fluid.
 */
@RestController
@RequestMapping("/api/fluids")
public class FluidController {

    @Autowired
    private FluidService fluidService;

    @Autowired
    private FluidRepository fluidRepository;
    /**
     * Получение всех записей о жидкостях.
     *
     * @return список всех жидкостей.
     */
    @GetMapping
    public List<Fluid> getAllFluids() {
        return fluidService.findAll();
    }

    /**
     * Получение записи о жидкости по ID.
     *
     * @param id идентификатор жидкости.
     * @return запись о жидкости или ошибка 404, если запись не найдена.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Fluid> getFluidById(@PathVariable Long id) {
        Optional<Fluid> fluid = fluidService.findById(id);
        return fluid.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Создание новой записи о жидкости.
     *
     * @param fluid объект жидкости.
     * @return созданная запись.
     */
    @PostMapping
    public Fluid createFluid(@RequestBody Fluid fluid) {
        return fluidRepository.save(fluid);
    }

    /**
     * Обновление записи о жидкости по ID.
     *
     * @param id идентификатор жидкости.
     * @param fluid обновленный объект жидкости.
     * @return обновленная запись или ошибка 404, если запись не найдена.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Fluid> updateFluid(@PathVariable Long id, @RequestBody Fluid fluid) {
        Optional<Fluid> existingFluid = fluidService.findById(id);
        if (existingFluid.isPresent()) {
            fluid.setId(id);
            Fluid updatedFluid = createFluid(fluid);
            return ResponseEntity.ok(updatedFluid);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удаление записи о жидкости по ID.
     *
     * @param id идентификатор жидкости.
     * @return HTTP статус 204 или 404, если запись не найдена.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFluid(@PathVariable Long id) {
        if (fluidService.existsById(id)) {
            fluidService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
