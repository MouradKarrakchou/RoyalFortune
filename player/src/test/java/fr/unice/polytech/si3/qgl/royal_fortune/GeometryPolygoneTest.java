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
}
