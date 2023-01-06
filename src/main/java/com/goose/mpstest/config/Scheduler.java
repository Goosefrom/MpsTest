package com.goose.mpstest.config;

import com.goose.mpstest.model.Airplane;
import com.goose.mpstest.model.Flight;
import com.goose.mpstest.model.TemporaryPoint;
import com.goose.mpstest.model.WayPoint;
import com.goose.mpstest.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableScheduling
public class Scheduler {

    private AirplaneRepository airplaneRepository;


    private PlaneCalculation planeCalculation;

    @Autowired
    public Scheduler(AirplaneRepository airplaneRepository,
                     PlaneCalculation planeCalculation) {
        this.airplaneRepository = airplaneRepository;
        this.planeCalculation = planeCalculation;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleFirstFlight() throws Exception {
        WayPoint p1 = new WayPoint(0.0, 0.0, 0.0,0.0);
        WayPoint p2 = new WayPoint(1.1, 1.1, 1.1, 1.1);
        WayPoint p3 = new WayPoint(3.5, 1.1, 0.0, 5.4);
        List<WayPoint> wayPoints = new ArrayList<>(List.of(p1, p2, p3));

        Optional<Airplane> optAirplane = airplaneRepository.findById(1L);
        calculate(optAirplane, wayPoints);
    }

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleSecondFlight() throws Exception {
        WayPoint p1 = new WayPoint(1.0, 0.0, 3.0,0.0);
        WayPoint p2 = new WayPoint(1.1, 1.1, 1.1, 1.1);
        WayPoint p3 = new WayPoint(3.5, 5.1, 7.0, 5.4);
        WayPoint p4 = new WayPoint(8.6, 4.9, 18.3, 10.3);

        List<WayPoint> wayPoints = new ArrayList<>(List.of(p1, p2, p3, p4));

        Optional<Airplane> optAirplane = airplaneRepository.findById(2L);
        calculate(optAirplane, wayPoints);

    }

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleThirdFlight() throws Exception {
        WayPoint p1 = new WayPoint(8.0, 98.0, 2.0,0.0);
        WayPoint p2 = new WayPoint(1.1, 1.1, 1.1, 1.1);
        WayPoint p3 = new WayPoint(34.5, 52.1, 7.0, 54.4);
        WayPoint p4 = new WayPoint(32.5, 90.0, 90.0, 3.1);
        WayPoint p5 = new WayPoint(1.8, 0.0, 200.0, 13.3);

        List<WayPoint> wayPoints = new ArrayList<>(List.of(p1, p2, p3, p4, p5));

        Optional<Airplane> optAirplane = airplaneRepository.findById(3L);
        calculate(optAirplane, wayPoints);

    }

    private void calculate(Optional<Airplane> optAirplane, List<WayPoint> wayPoints) throws Exception {
        if (optAirplane.isPresent()) {
            Airplane airplane = optAirplane.get();
            List<Flight> flights = airplane.getFlights();
            int flightsNumber = flights.size();
            System.out.println("Number of flights: " + flightsNumber);
            for (Flight flight : flights) {
                System.out.println("Flight N:" + flight.getNumber() +
                        " , flight time(s): " + flight.getPassedPoints().size());
            }

            List<TemporaryPoint> results = planeCalculation
                    .calculateRoute(optAirplane.get().getAirplaneCharacteristics(), wayPoints);

            Flight flight = new Flight((long) flights.size(), wayPoints, results);
            flights.add(flight);
            airplane.setFlights(flights);
            airplane.setPosition(results.get(results.size() - 1));

            airplaneRepository.save(airplane);
        }

    }
}
