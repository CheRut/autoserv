package com.garage.autoservice.repository;

import com.garage.autoservice.entity.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PartRepositoryTest {

    @Autowired
    private PartRepository partRepository;

    private Part part;

    @BeforeEach
    public void setUp() {
        part = new Part();
        part.setName("Oil Filter");
        part.setManufacturer("Bosch");
        part.setPartNumber("OF123");
        part.setQuantity(50);
        part.setPrice(15.99);
        part.setVin("1HGCM82633A123456");  // Обязательное поле VIN
    }

    @Test
    public void testSavePart() {
        Part savedPart = partRepository.save(part);
        assertNotNull(savedPart.getId());
        assertEquals(part.getName(), savedPart.getName());
    }

    @Test
    public void testFindByName() {
        partRepository.save(part);
        List<Part> parts = partRepository.findByName("Oil Filter");
        assertFalse(parts.isEmpty());
        assertEquals(1, parts.size());
    }

    @Test
    public void testDeletePart() {
        Part savedPart = partRepository.save(part);
        partRepository.deleteById(savedPart.getId());
        assertFalse(partRepository.findById(savedPart.getId()).isPresent());
    }

    @Test
    public void testVinNotNullConstraint() {
        part.setVin(null); // Попытка сохранить Part без VIN
        assertThrows(DataIntegrityViolationException.class, () -> {
            partRepository.save(part);
        });
    }
}
