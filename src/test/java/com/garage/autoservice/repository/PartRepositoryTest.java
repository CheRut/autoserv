package com.garage.autoservice.repository;

import com.garage.autoservice.entity.Part;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PartRepositoryTest {

    @Autowired
    private PartRepository partRepository;

    @Test
    public void testFindByName() {
        // Создаем и сохраняем объект Part с ожидаемыми значениями
        Part part = new Part();
        part.setName("Brake Pad");
        part.setManufacturer("Brembo");  // Убедитесь, что значение "Brembo" установлено
        part.setPartNumber("BP123");
        part.setQuantity(10);
        part.setPrice(50.0);

        partRepository.save(part);

        // Выполняем поиск по имени
        List<Part> foundParts = partRepository.findByName("Brake Pad");

        // Проверяем, что найденный объект имеет ожидаемое значение
        assertThat(foundParts).isNotEmpty();
        assertThat(foundParts.get(0).getManufacturer()).isEqualTo("Brembo");  // Ожидаемое значение
    }



    @Test
    public void testSavePart() {
        Part part = new Part();
        part.setName("Oil Filter");
        part.setManufacturer("Bosch");
        part.setPartNumber("OF567");
        part.setQuantity(15);
        part.setPrice(25.0);

        Part savedPart = partRepository.save(part);
        assertThat(savedPart).isNotNull();
        assertThat(savedPart.getId()).isNotNull();
    }

    @Test
    public void testDeletePart() {
        Part part = new Part();
        part.setName("Air Filter");
        part.setManufacturer("Mann");
        part.setPartNumber("AF890");
        part.setQuantity(5);
        part.setPrice(20.0);

        Part savedPart = partRepository.save(part);
        partRepository.delete(savedPart);

        List<Part> foundParts = partRepository.findByName("Air Filter");
        assertThat(foundParts).isEmpty();
    }
}
