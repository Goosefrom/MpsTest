package com.goose.mpstest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Airplane {
        @Id
        private Long id;
        private AirplaneCharacteristics airplaneCharacteristics;
        private TemporaryPoint position;
        private List<Flight> flights;

        public Airplane(Long id,
                        AirplaneCharacteristics airplaneCharacteristics,
                        TemporaryPoint position,
                        List<Flight> flights) {
                this.id = id;
                this.airplaneCharacteristics = airplaneCharacteristics;
                this.position = position;
                this.flights = flights;
        }
}
