package com.goose.mpstest.service;

import com.goose.mpstest.model.Airplane;
import com.goose.mpstest.model.Flight;
import com.goose.mpstest.model.WayPoint;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AirplaneService {

    Airplane getAirplane(Long id);

    Flight getFlight(Long id, List<WayPoint> wayPoints) throws Exception;

    Airplane addAirplane(Airplane airplane);
}
