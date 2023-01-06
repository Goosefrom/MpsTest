package com.goose.mpstest.model;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Airplane {
        @Id
        private Long id;
        private AirplaneCharacteristics airplaneCharacteristics;
        private TemporaryPoint position;
        private List<Flight> flights;
}
