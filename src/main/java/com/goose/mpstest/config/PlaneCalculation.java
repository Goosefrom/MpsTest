package com.goose.mpstest.config;

import com.goose.mpstest.exception.WaypointException;
import com.goose.mpstest.model.Airplane;
import com.goose.mpstest.model.AirplaneCharacteristics;
import com.goose.mpstest.model.TemporaryPoint;
import com.goose.mpstest.model.WayPoint;
import com.goose.mpstest.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

import static java.lang.Math.*;

@Configuration
public class PlaneCalculation {

    public double[][] minAltitude = {{1, 1, 0, 0, 1, 2, 1, 1, 0 ,3}, {0, 1, 2, 3, 2, 1, 2, 1, 0, 0}};

    public List<TemporaryPoint> calculateRoute(AirplaneCharacteristics airplaneCharacteristics,
                                               List<WayPoint> wayPoints) throws Exception {
        List<TemporaryPoint> results = new ArrayList<>();

        if(wayPoints.stream().noneMatch(wayPoint ->
                    wayPoint.getFlightSpeed() > airplaneCharacteristics.getMaxSpeed())) {

            for(int i = 1; i < wayPoints.size(); i++) {
                    //first cords
                    Double x0 = wayPoints.get(i-1).getLatitude();
                    Double y0 = wayPoints.get(i-1).getLongitude();
                    Double z0 = wayPoints.get(i-1).getFlightAltitude();
                    Double v0 = wayPoints.get(i-1).getFlightSpeed();
                    //second cords
                    Double x1 = wayPoints.get(i).getLatitude();
                    Double y1 = wayPoints.get(i).getLongitude();
                    Double z1 = wayPoints.get(i).getFlightAltitude();
                    Double v1 = wayPoints.get(i).getFlightSpeed();

                    //way
                    Double pathX = x1 - x0;
                    Double pathY = y1 - y0;
                    Double pathZ = z1 - z0;

                    Double path = sqrt(pow(pathX, 2) + pow(pathY, 2) + pow(pathZ, 2));
                    //time
                    Double t = 2 * path / (v1 + v0);

                    //corner`s cos between path and OX, OY, OZ
                    Double cosA = pathX / path;
                    Double cosB = pathY / path;
                    Double cosC = pathZ / path;

                    //speed for OX, OY, OZ
                    Double v0X = v0 * cosA;
                    Double v1X = v1 * cosA;

                    Double v0Y = v0 * cosB;
                    Double v1Y = v1 * cosB;

                    Double v0Z = v0 * cosC;
                    Double v1Z = v1 * cosC;
                    if (v0Z > airplaneCharacteristics.getRateAltitudeChange())
                        v0Z = airplaneCharacteristics.getRateAltitudeChange();
                    if (v1Z > airplaneCharacteristics.getRateAltitudeChange())
                        v1Z = airplaneCharacteristics.getRateAltitudeChange();

                    //acceleration for OX, OY, OZ and path
                    Double a = (v1 - v0) / t;
                    if( a > airplaneCharacteristics.getMaxAcceleration()) {
                        a = airplaneCharacteristics.getMaxAcceleration();
                        t = (v1 - v0) / a;
                    }
                    Double aX = (v1X - v0X) / t;
                    Double aY = (v1Y - v0Y) / t;
                    Double aZ = (v1Z - v0Z) / t;

                    //course
                    Double course = 0.0;
                    if (x0 <= x1) course = acos(cosB);
                    else if (x0 > x1) course = acos(cosB) + PI;

                    Double prevCourse = 0.0;
                    Double dCourse = 0.0;
                    if (!results.isEmpty()) {
                        prevCourse = results.get(results.size() - 1).getCourse();
                        course += prevCourse;
                        dCourse = course - prevCourse;
                    }

                    if (course > 2 * PI) course -= 2 * PI;


                    for(int t1 = 0; t1 < round(t); t1++) {
                        TemporaryPoint passedPoint = new TemporaryPoint();

                        passedPoint.setLatitude(x0 + (v0X * t1 + aX * pow(t1, 2) / 2));

                        if (dCourse >= airplaneCharacteristics.getRateCourseChange()) {
                            passedPoint.setLongitude(y0 + (v0Y * t1 +
                                    aY * cos(airplaneCharacteristics.getRateCourseChange()) * pow(t1, 2) / 2));
                            dCourse -= airplaneCharacteristics.getRateCourseChange();
                        }
                        else {
                            passedPoint.setLongitude(y0 + (v0Y * t1 + aY * cos(dCourse) * pow(t1, 2) / 2));
                        }

                        Double altitude = z0 + (v0Z * t1 + aZ * pow(t1, 2) / 2);
                        if (altitude <
                                minAltitude[(int) round(passedPoint.getLatitude())]
                                        [(int) round(passedPoint.getLongitude())])
                            throw new WaypointException("Point has altitude: " +
                                    altitude + " less than min altitude " +
                                    minAltitude[(int) round(passedPoint.getLatitude())]
                                            [(int) round(passedPoint.getLongitude())]);
                        else passedPoint.setFlightAltitude(altitude);

                        passedPoint.setFlightSpeed(v0 + a*t1);

                        passedPoint.setCourse(course);

                        results.add(passedPoint);
                    }
                }
            }
            else throw new WaypointException("Waypoints has speed more than max airplane speed " + wayPoints
                    .stream()
                    .filter(wayPoint -> wayPoint.getFlightSpeed() > airplaneCharacteristics.getMaxSpeed())
                    .collect(Collectors.toList()));

        return results;
    }
}
