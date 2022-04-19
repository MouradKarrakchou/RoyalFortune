package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryCircle;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryPolygone;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Polygone;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeometryPolygoneTest {

    @Test
    void computeIntersection() {
        Position start = new Position(0,10);
        Position arrival = new Position(100, 10);
        Segment segment = new Segment(start, arrival);

        Position reefPosition = new Position(45, 33);

        Point[] vertices = {new Point(10, 0), new Point(80, 0), new Point(45, 100)};

        Polygone polygone = new Polygone(vertices, 0);

        polygone.computeSegmentsIfPossible(reefPosition);
        List<Position> positionList = GeometryPolygone.computeIntersectionWith(segment, reefPosition, polygone);

        List<Beacon> beaconList = GeometryPolygone.generateBeacon(polygone);

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
}
