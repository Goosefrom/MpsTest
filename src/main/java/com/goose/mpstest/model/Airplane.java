package com.goose.mpstest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Airplane {
        @Id
        private Long id;
        private AirplaneCharacteristics characteristics;
        private TemporaryPoint position;
        private List<Flight> flights;

        public Airplane(Long id,
                        AirplaneCharacteristics characteristics,
                        TemporaryPoint position,
                        List<Flight> flights) {
                this.id = id;
                this.characteristics = characteristics;
                this.position = position;
                this.flights = flights;
        }
}
