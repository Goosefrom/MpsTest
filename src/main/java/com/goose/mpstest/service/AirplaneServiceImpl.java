package com.goose.mpstest.service;

import com.goose.mpstest.config.PlaneCalculation;
import com.goose.mpstest.model.*;
import com.goose.mpstest.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AirplaneServiceImpl implements AirplaneService {

    @Autowired
    private AirplaneRepository repository;

    @Autowired
    private PlaneCalculation planeCalculation;

    @Override
    public Airplane getAirplane(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Flight getFlight(Long id, List<WayPoint> wayPoints) throws Exception {
        Airplane airplane = repository.findById(id).orElseThrow();

        List<TemporaryPoint> temporaryPoints = planeCalculation
                .calculateRoute(airplane.getAirplaneCharacteristics(), wayPoints);

        Flight lastFlight = new Flight((long) airplane.getFlights().size(), wayPoints, temporaryPoints);
        List<Flight> flights = airplane.getFlights();
        flights.add(lastFlight);
        airplane.setPosition(temporaryPoints.get(temporaryPoints.size() - 1));
        airplane.setFlights(flights);
        repository.save(airplane);

        return lastFlight;
    }

    @Override
    public Airplane addAirplane(Airplane airplane) {
        return repository.save(airplane);
    }
}
