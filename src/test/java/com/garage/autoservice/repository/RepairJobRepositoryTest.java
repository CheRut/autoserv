package com.garage.autoservice.repository;

import com.garage.autoservice.entity.RepairJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Аннотация для запуска Spring Boot контекста во время теста
@SpringBootTest
public class RepairJobRepositoryTest {

    @Autowired
    private RepairJobRepository repairJobRepository;

    @Test
    public void testSaveAndFind() {
        // Создание нового объекта RepairJob
        RepairJob repairJob = new RepairJob();
        repairJob.setJobName("Test Job");
        repairJob.setSerialNumber("123ABC");

        // Сохранение RepairJob в базу данных
        RepairJob savedJob = repairJobRepository.save(repairJob);

        // Проверка, что ID был сгенерирован и объект был сохранен
        assertNotNull(savedJob.getId());
        assertEquals("Test Job", savedJob.getJobName());

        // Попытка найти объект по ID
        Optional<RepairJob> foundJob = repairJobRepository.findById(savedJob.getId());
        assertTrue(foundJob.isPresent());
        assertEquals("Test Job", foundJob.get().getJobName());
    }
}
