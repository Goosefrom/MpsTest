package com.goose.mpstest.model;

import lombok.Data;

@Data
public class TemporaryPoint {
        private Double latitude;
        private Double longitude;
        private Double flightAltitude;
        private Double flightSpeed;
        private Double course;
}
