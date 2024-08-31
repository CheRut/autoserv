package com.garage.autoservice.repository;

import com.garage.autoservice.entity.Part;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

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
        part.setCardNumber(1599);
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
        Optional<Part> partOptional = partRepository.findByName("Oil Filter");

        assertTrue(partOptional.isPresent());
        assertEquals("Oil Filter", partOptional.get().getName());
    }


    @Test
    public void testDeletePart() {
        Part savedPart = partRepository.save(part);
        partRepository.deleteById(savedPart.getId());
        assertFalse(partRepository.findById(savedPart.getId()).isPresent());
    }

    @Test
    void testVinNotNullConstraint() {
        Part part = new Part();
        part.setName("Test Part");
        part.setManufacturer("Test Manufacturer");
        // part.setVin(null);  // Оставляем VIN пустым для теста

        assertThrows(ConstraintViolationException.class, () -> {
            partRepository.save(part);
        });
    }
}
