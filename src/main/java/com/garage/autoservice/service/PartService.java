package com.garage.autoservice.service;

import com.garage.autoservice.entity.Part;
import com.garage.autoservice.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для проверки наличия запчастей.
 */
@Service
public class PartService {

    @Autowired
    private PartRepository partRepository;

    /**
     * Проверяет, доступны ли все необходимые запчасти.
     *
     * @param requiredParts список требуемых запчастей
     * @return true, если все запчасти доступны; иначе false
     */
    /**
     * Проверяет, доступны ли все запчасти из списка.
     *
     * @param parts список запчастей
     * @return true, если все запчасти доступны, иначе false
     */
    public boolean arePartsAvailable(List<Part> parts) {
        for (Part part : parts) {
            Optional<Part> foundPart = partRepository.findByVin(part.getVin());
            if (foundPart.isEmpty() || foundPart.get().getQuantity() < part.getQuantity()) {
                return false;
            }
        }
        return true;
    }
}
