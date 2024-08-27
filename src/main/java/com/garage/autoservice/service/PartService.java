package com.garage.autoservice.service;

import com.garage.autoservice.entity.Part;
import com.garage.autoservice.exception.InvalidRequestException;
import com.garage.autoservice.repository.PartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для проверки наличия запчастей.
 */
@Service
public class PartService {

    private static final Logger logger = LoggerFactory.getLogger(PartService.class);

    @Autowired
    private PartRepository partRepository;

    /**
     * Проверяет, доступны ли все запчасти из списка.
     *
     * @param parts список запчастей
     * @return true, если все запчасти доступны, иначе false
     */
    public boolean arePartsAvailable(List<Part> parts) {
        logger.info("Проверка доступности запчастей: {}", parts);

        for (Part part : parts) {
            Optional<Part> foundPart = partRepository.findByVin(part.getVin());

            if (foundPart.isEmpty()) {
                logger.warn("Запчасть с VIN {} не найдена в базе данных", part.getVin());
                return false;
            }

            if (foundPart.get().getQuantity() < part.getQuantity()) {
                logger.warn("Недостаточное количество запчасти: {}. Требуется: {}, доступно: {}",
                        part.getName(), part.getQuantity(), foundPart.get().getQuantity());
                return false;
            }
        }

        logger.info("Все запчасти доступны.");
        return true;
    }
}
