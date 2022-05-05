package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryPolygone;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Polygone;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static fr.unice.polytech.si3.qgl.royal_fortune.Cockpit.SECURITY_UPSCALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeometryPolygoneTest {
    private final double ACCURACY = 0.001;

    @Test
    void computeIntersection() {
        Position start = new Position(-200,10);
        Position arrival = new Position(200, 10);
        Segment segment = new Segment(start, arrival);

        Position reefPosition = new Position(45, 33);

        Point[] vertices = {new Point(-35, -33), new Point(35, -33), new Point(0, 66)};


        Polygone polygone = new Polygone(vertices, Math.PI/2);
        polygone.updatePolygone(reefPosition);
        polygone.computeSegmentsIfPossible(reefPosition);
        List<Position> positionList = GeometryPolygone.computeIntersectionWith(segment, reefPosition, polygone);

        List<Beacon> beaconList = GeometryPolygone.generateBeacon(reefPosition, polygone);

        assertTrue(true);
    }

    @Test
    void baseChangeTest() {

        Position reefPosition = new Position(45, 33);

        Point[] vertices = {new Point(-35, -33), new Point(35, -33), new Point(0, 66)};

        Polygone polygone = new Polygone(vertices, 0);

        polygone.updatePolygone(reefPosition);

        assertTrue(true);
    }

    @Test
    void beaconPositionTest(){
        Point[] points = {
                new Point(10, 5),
                new Point(10, 10),
                new Point(15, 5),
                new Point(15, 10)
        };
        Polygone p = new Polygone(points, 0);

        List<Beacon> beacons = GeometryPolygone.generateBeacon(new Position(12.5, 7.5), p);

        assertEquals(4, beacons.size());
        double coef = Math.sqrt((SECURITY_UPSCALE * SECURITY_UPSCALE)/2.0);

        assertTrue(Math.abs(10 - coef - beacons.get(0).getPosition().getX()) < ACCURACY);
        assertTrue(Math.abs(5 - coef - beacons.get(0).getPosition().getY()) < ACCURACY);

        assertTrue(Math.abs(10 - coef - beacons.get(1).getPosition().getX()) < ACCURACY);
        assertTrue(Math.abs(10 + coef - beacons.get(1).getPosition().getY()) < ACCURACY);

        assertTrue(Math.abs(15 + coef - beacons.get(2).getPosition().getX()) < ACCURACY);
        assertTrue(Math.abs(5 - coef - beacons.get(2).getPosition().getY()) < ACCURACY);

        assertTrue(Math.abs(15 + coef - beacons.get(3).getPosition().getX()) < ACCURACY);
        assertTrue(Math.abs(10 + coef - beacons.get(3).getPosition().getY()) < ACCURACY);
    }
}
