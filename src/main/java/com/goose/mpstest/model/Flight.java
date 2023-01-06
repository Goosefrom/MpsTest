package com.goose.mpstest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Flight {
        private Long number;
        private List<WayPoint> wayPoints;
        private List<TemporaryPoint> passedPoints;

        public Flight(Long number, List<WayPoint> wayPoints, List<TemporaryPoint> passedPoints) {
                this.number = number;
                this.wayPoints = wayPoints;
                this.passedPoints = passedPoints;
        }
}
