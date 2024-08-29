package com.garage.autoservice.service;

import com.garage.autoservice.entity.UsedParts;

import com.garage.autoservice.exception.ResourceNotFoundException;
import com.garage.autoservice.repository.UsedPartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с записями об использованных запчастях.
 */
@Service
public class UsedPartService {

    private static final Logger logger = LoggerFactory.getLogger(UsedPartService.class);

    @Autowired
    private UsedPartRepository usedPartRepository;

    /**
     * Создает новую запись об использованной запчасти.
     *
     * @param usedPart Запись об использованной запчасти
     * @return сохраненная запись
     */
    @Transactional
    public UsedParts createUsedPart(UsedParts usedPart) {
        logger.info("Создание записи об использованной запчасти: {}", usedPart);
        return usedPartRepository.save(usedPart);
    }

    /**
     * Получает все записи об использованных запчастях.
     *
     * @return список всех записей
     */
    @Transactional(readOnly = true)
    public List<UsedParts> getAllUsedParts() {
        logger.info("Получение всех записей об использованных запчастях");
        return usedPartRepository.findAll();
    }

    /**
     * Обновляет запись об использованной запчасти.
     *
     * @param id идентификатор записи
     * @param usedPart данные для обновления
     * @return обновленная запись
     */
    @Transactional
    public UsedParts updateUsedPart(Long id, UsedParts usedPart) {
        UsedParts existingPart = usedPartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись об использованной запчасти с ID " + id + " не найдена"));

        logger.info("Обновление записи об использованной запчасти с ID: {}", id);

        existingPart.setName(usedPart.getName());
        existingPart.setManufacturer(usedPart.getManufacturer());
        existingPart.setPartNumber(usedPart.getPartNumber());
        existingPart.setQuantity(usedPart.getQuantity());
        existingPart.setCardNumber(usedPart.getCardNumber());

        return usedPartRepository.save(existingPart);
    }

    /**
     * Удаляет запись об использованной запчасти.
     *
     * @param id идентификатор записи
     */
    @Transactional
    public void deleteUsedPart(Long id) {
        UsedParts usedParts = usedPartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись об использованной запчасти с ID " + id + " не найдена"));

        logger.info("Удаление записи об использованной запчасти с ID: {}", id);
        usedPartRepository.delete(usedParts);
    }
}
