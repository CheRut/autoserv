package com.garage.autoservice.service;

import com.garage.autoservice.entity.UsedFluid;
import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.repository.UsedFluidRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Сервис для работы с записями об использованных жидкостях.
 */
@Service
public class UsedFluidService {

    private static final Logger logger = LoggerFactory.getLogger(UsedFluidService.class);

    @Autowired
    private UsedFluidRepository usedFluidRepository;

    /**
     * Создает новую запись об использованной жидкости.
     *
     * @param usedFluid Запись об использованной жидкости
     * @return сохраненная запись
     */
    @Transactional
    public UsedFluid createUsedFluid(@Valid @NotNull UsedFluid usedFluid) {
        logger.info("Создание записи об использованной жидкости: {}", usedFluid);
        return usedFluidRepository.save(usedFluid);
    }

    /**
     * Получает все записи об использованных жидкостях.
     *
     * @return список всех записей
     */
    @Transactional(readOnly = true)
    public List<UsedFluid> getAllUsedFluids() {
        logger.info("Получение всех записей об использованных жидкостях");
        return usedFluidRepository.findAll();
    }

    /**
     * Обновляет запись об использованной жидкости.
     *
     * @param id идентификатор записи
     * @param usedFluid данные для обновления
     * @return обновленная запись
     * @throws ResourceNotFoundException если запись с указанным id не найдена
     */
    @Transactional
    public UsedFluid updateUsedFluid(@NotNull Long id, @Valid @NotNull UsedFluid usedFluid) {
        UsedFluid existingFluid = usedFluidRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Запись об использованной жидкости с ID {} не найдена", id);
                    return new ResourceNotFoundException("Запись об использованной жидкости с ID " + id + " не найдена");
                });

        logger.info("Обновление записи об использованной жидкости с ID: {}", id);

        existingFluid.setType(usedFluid.getType());
        existingFluid.setBrand(usedFluid.getBrand());
        existingFluid.setVolume(usedFluid.getVolume());
        existingFluid.setCardNumber(usedFluid.getCardNumber());

        UsedFluid updatedFluid = usedFluidRepository.save(existingFluid);
        logger.debug("Запись об использованной жидкости с ID {} успешно обновлена", id);
        return updatedFluid;
    }

    /**
     * Удаляет запись об использованной жидкости.
     *
     * @param id идентификатор записи
     * @throws ResourceNotFoundException если запись с указанным id не найдена
     */
    @Transactional
    public void deleteUsedFluid(@NotNull Long id) {
        UsedFluid usedFluid = usedFluidRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Запись об использованной жидкости с ID {} не найдена", id);
                    return new ResourceNotFoundException("Запись об использованной жидкости с ID " + id + " не найдена");
                });

        logger.info("Удаление записи об использованной жидкости с ID: {}", id);
        usedFluidRepository.delete(usedFluid);
        logger.debug("Запись об использованной жидкости с ID {} успешно удалена", id);
    }
}
