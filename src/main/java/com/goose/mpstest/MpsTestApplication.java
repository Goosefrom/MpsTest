package com.goose.mpstest;

import com.goose.mpstest.model.Airplane;
import com.goose.mpstest.model.AirplaneCharacteristics;
import com.goose.mpstest.model.TemporaryPoint;
import com.goose.mpstest.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static java.lang.Math.PI;

@SpringBootApplication
public class MpsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpsTestApplication.class, args);
    }

    @Autowired
    public AirplaneRepository repository;

    @Bean
    public void addAirplanesOnStart() {
        if (!repository.findAll().isEmpty()) {
            Airplane airplane1 = new Airplane(1L,
                    new AirplaneCharacteristics(1.0, 0.1, 1.0, PI/2),
                    new TemporaryPoint(),
                    List.of());
            Airplane airplane2 = new Airplane(2L,
                    new AirplaneCharacteristics(6.0, 1.1, 0.5, PI/6),
                    new TemporaryPoint(),
                    List.of());
            Airplane airplane3 = new Airplane(3L,
                    new AirplaneCharacteristics(100.0, 7.5, 0.7, PI/3),
                    new TemporaryPoint(),
                    List.of());

            repository.saveAll(List.of(airplane1, airplane2, airplane3));
        }
    }

}
