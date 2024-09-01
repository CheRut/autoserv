package com.garage.autoservice.service;

import com.garage.autoservice.entity.Part;
import com.garage.autoservice.exception.InvalidRequestException;
import com.garage.autoservice.repository.PartRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления и проверки наличия запчастей.
 */
@Service
public class PartService {

    private static final Logger logger = LoggerFactory.getLogger(PartService.class);

    @Autowired
    private PartRepository partRepository;



    /**
     * Возвращает все запчасти из базы данных.
     *
     * @return список запчастей
     */
    public List<Part> findAll() {
        return partRepository.findAll();
    }
    /**
     * Сохраняет или обновляет запчасть в базе данных.
     *
     * @param part запчасть для сохранения или обновления
     */
    public void save(Part part) {
        partRepository.save(part);
    }

    /**
     * Удаляет запчасть из базы данных.
     *
     * @param part запчасть для удаления
     */
    public void delete(Part part) {
        partRepository.delete(part);
    }

    /**
     * Проверяет, доступны ли все запчасти из списка.
     *
     * @param parts список запчастей
     * @return true, если все запчасти доступны, иначе false
     */
    public boolean arePartsAvailable(@Valid List<Part> parts) {
        logger.debug("Проверка доступности запчастей: {}", parts);

        for (Part part : parts) {
            @NotEmpty(message = "VIN не может быть пустым")
            String vin = part.getVin();
            Optional<Part> foundPart = partRepository.findByVin(vin);

            if (foundPart.isEmpty()) {
                logger.warn("Запчасть с VIN {} не найдена в базе данных", vin);
                throw new InvalidRequestException("Запчасть с VIN " + vin + " не найдена");
            }

            @Positive(message = "Количество запчастей должно быть положительным числом")
            int quantity = part.getQuantity();

            if (foundPart.get().getQuantity() < quantity) {
                logger.warn("Недостаточное количество запчасти: {}. Требуется: {}, доступно: {}",
                        part.getName(), quantity, foundPart.get().getQuantity());
                return false;
            }

            logger.debug("Запчасть {} с VIN {} доступна в количестве {}",
                    part.getName(), vin, foundPart.get().getQuantity());
        }

        logger.debug("Все запчасти доступны.");
        return true;
    }

    public Optional<Part> findByCardNumber(int cardNumber) {
        return partRepository.findByCardNumber(cardNumber);
    }
}
