package com.garage.autoservice.repository;

import com.garage.autoservice.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления сущностями {@link Part}.
 * Обеспечивает методы для выполнения CRUD-операций с запчастями в базе данных.
 */
@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    /**
     * Метод для поиска запчастей по их названию.
     *
     * @param name название запчасти
     * @return список запчастей с указанным названием
     */
    Optional<Part> findByName(String name);

    /**
     * Метод для поиска запчасти по VIN-коду.
     *
     * @param vin VIN-код автомобиля
     * @return найденная запчасть, если такая существует
     */
    Optional<Part> findByVin(String vin);

    /**
     * Метод для поиска запчасти по имени, производителю и номеру партии.
     *
     * @param name название запчасти
     * @param manufacturer производитель запчасти
     * @param partNumber номер партии
     * @return найденная запчасть, если такая существует
     */
    @Query("SELECT p FROM Part p WHERE p.name = :name AND p.manufacturer = :manufacturer AND p.partNumber = :partNumber")
    Optional<Part> findByNameAndManufacturerAndPartNumber(@Param("name") String name,
                                                          @Param("manufacturer") String manufacturer,
                                                          @Param("partNumber") String partNumber);

    Optional<Part> findByCardNumber(Integer cardNumber);
}
