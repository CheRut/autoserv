package com.garage.autoservice.service;

import com.garage.autoservice.entity.MaintenanceRecord;
import com.garage.autoservice.repository.MaintenanceRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления записями о выполненных работах.
 */
@Service
public class MaintenanceRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceRecordService.class);

    private final MaintenanceRecordRepository maintenanceRecordRepository;

    @Autowired
    public MaintenanceRecordService(MaintenanceRecordRepository maintenanceRecordRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
    }

    /**
     * Получение всех записей о выполненных работах.
     *
     * @return список всех записей о выполненных работах
     */
    public List<MaintenanceRecord> findAll() {
        logger.info("Получение всех записей о выполненных работах");
        return maintenanceRecordRepository.findAll();
    }

    /**
     * Поиск записи о выполненной работе по ID.
     *
     * @param id идентификатор записи
     * @return найденная запись, если существует
     */
    public Optional<MaintenanceRecord> findById(Long id) {
        logger.info("Поиск записи о выполненной работе по ID: {}", id);
        return maintenanceRecordRepository.findById(id);
    }

    /**
     * Сохранение или обновление записи о выполненной работе.
     *
     * @param maintenanceRecord запись о выполненной работе
     * @return сохраненная запись
     */
    public MaintenanceRecord save(MaintenanceRecord maintenanceRecord) {
        logger.info("Сохранение записи о выполненной работе: {}", maintenanceRecord);
        return maintenanceRecordRepository.save(maintenanceRecord);
    }

    /**
     * Удаление записи о выполненной работе.
     *
     * @param maintenanceRecord запись о выполненной работе, которую нужно удалить
     */
    public void delete(MaintenanceRecord maintenanceRecord) {
        logger.info("Удаление записи о выполненной работе: {}", maintenanceRecord);
        maintenanceRecordRepository.delete(maintenanceRecord);
    }


}
