package com.goose.mpstest.model;

import lombok.Data;

@Data
public class AirplaneCharacteristics {
        private Double maxSpeed;
        private Double maxAcceleration;
        private Double rateAltitudeChange;
        private Double rateCourseChange;

        public AirplaneCharacteristics(Double maxSpeed,
                                       Double maxAcceleration,
                                       Double rateAltitudeChange,
                                       Double rateCourseChange) {
                this.maxSpeed = maxSpeed;
                this.maxAcceleration = maxAcceleration;
                this.rateAltitudeChange = rateAltitudeChange;
                this.rateCourseChange = rateCourseChange;
        }
}
