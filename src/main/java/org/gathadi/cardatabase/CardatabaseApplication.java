package org.gathadi.cardatabase;

import jakarta.transaction.Transactional;
import org.gathadi.cardatabase.domain.Car;
import org.gathadi.cardatabase.domain.CarRepository;
import org.gathadi.cardatabase.domain.Owner;
import org.gathadi.cardatabase.domain.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CardatabaseApplication.class);

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;

    public CardatabaseApplication(CarRepository carRepository, OwnerRepository ownerRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CardatabaseApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner("Ajay", "Gathadi");
        Owner owner2 = new Owner("Deepak","Gathadi");
        ownerRepository.saveAll(Arrays.asList(owner1, owner2));

        carRepository.save(new Car("Ford", "Mustang", "Royal Blue", "MH-05-AJ-9995", 2024, 22000000, owner1));
        carRepository.save(new Car("Jaguar","F-Type","Olive Green","MH-05-AJ-9995",2025, 25000000,owner1));
        carRepository.save(new Car("Mercedes","Maybach","White","MH-05-AJ-9995",2025, 30000000,owner2));
        carRepository.save(new Car("Aston Martin", "DB11", "Olive Green", "MH-05-AJ-9995", 2025, 35000000,owner2));

        for(Car car : carRepository.findAll()){
            logger.info("Brand: {}, Model: {}, Color: {}, RegisterNumber: {}, ModelYear: {}, Price: {}",
                    car.getBrand(), car.getModel(), car.getColor(), car.getRegisterNumber(), car.getModelYear(),
                    car.getPrice());
        }
    }
}
