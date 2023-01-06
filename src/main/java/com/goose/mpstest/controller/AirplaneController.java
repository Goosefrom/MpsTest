package com.goose.mpstest.controller;

import com.goose.mpstest.model.Airplane;
import com.goose.mpstest.model.Flight;
import com.goose.mpstest.model.WayPoint;
import com.goose.mpstest.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("airplane")
public class AirplaneController {

    @Autowired
    private AirplaneService airplaneService;

    @GetMapping("/{id}")
    public Airplane getAirplane(@PathVariable Long id) {
        return airplaneService.getAirplane(id);
    }

    @PostMapping("/{id}/flight")
    public Flight getFlight(@PathVariable Long id, @RequestBody List<WayPoint> wayPoints) throws Exception {
        return airplaneService.getFlight(id, wayPoints);
    }

    @PostMapping()
    public Airplane addAirplane(@RequestBody Airplane airplane) {
        return airplaneService.addAirplane(airplane);
    }
}
