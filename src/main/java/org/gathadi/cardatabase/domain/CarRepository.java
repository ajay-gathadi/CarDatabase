package org.gathadi.cardatabase.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    @Query("Select c from Car c where c.brand = ?1")
    List<Car> findByBrand(String brand);
}
