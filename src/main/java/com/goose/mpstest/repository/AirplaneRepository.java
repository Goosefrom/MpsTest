package com.goose.mpstest.repository;

import com.goose.mpstest.model.Airplane;
import com.goose.mpstest.model.AirplaneCharacteristics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirplaneRepository extends MongoRepository<Airplane, Long> {
    public Airplane findByCharacteristics(AirplaneCharacteristics airplaneCharacteristics);
}
