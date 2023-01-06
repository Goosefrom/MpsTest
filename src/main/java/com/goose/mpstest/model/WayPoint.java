package com.goose.mpstest.model;

import lombok.Data;

@Data
public class WayPoint {
        private Double latitude;
        private Double longitude;
        private Double flightAltitude;
        private Double flightSpeed;

        public WayPoint(Double latitude, Double longitude, Double flightAltitude, Double flightSpeed) {
                this.latitude = latitude;
                this.longitude = longitude;
                this.flightAltitude = flightAltitude;
                this.flightSpeed = flightSpeed;
        }
}
