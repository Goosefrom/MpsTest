package com.goose.mpstest.model;

import lombok.Data;

@Data
public class AirplaneCharacteristics {
        private Double maxSpeed;
        private Double maxAcceleration;
        private Double rateAltitudeChange;
        private Double rateCourseChange;
}
