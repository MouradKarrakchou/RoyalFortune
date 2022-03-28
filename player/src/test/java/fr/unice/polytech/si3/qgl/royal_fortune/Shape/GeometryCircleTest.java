package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryCircle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometryCircleTest {
    GeometryCircle geometryCircle;

    @BeforeEach
    void init() {
        geometryCircle = new GeometryCircle();
    }

    @Test
    void rectangleIsInCircle(){
        double radius = 500.;
        double h = 100;
        double w = 100;
        double o = 0;
        Position seaEntitiePos = new Position(0,0,0);

        Rectangle r = new Rectangle(w, h , o);
        Circle c = new Circle(radius);

        assertTrue(GeometryCircle.rectangleIsInCircle(r, seaEntitiePos, radius));
    }

    @Test
    void rectangleIsNotInCircle(){
        double radius = 50.;
        double h = 100;
        double w = 100;
        double o = 0;
        Position circlePos = new Position(1000,0,0);

        Rectangle r = new Rectangle(w, h , o);
        Circle c = new Circle(radius);

        assertFalse(GeometryCircle.rectangleIsInCircle(r, circlePos, radius));
    }

    @Test
    void toStringTest(){
        double radius = 50.;
        Circle c = new Circle(radius);

        assertNotEquals("", c.toString());
    }
/*
    @Test
    void generateBeaconTest() {
        Position shipPosition = new Position(0, 0);
        Position checkpointPosition = new Position(200, 0);
        Position reefPosition = new Position(100, -10);
        Circle reefShape = new Circle(30);

        List<Beacon> beaconList = geometryCircle.generateBeacon(shipPosition, checkpointPosition, reefPosition, reefShape);

        assertEquals(6, beaconList.size());

        assertEquals(100, beaconList.get(0).getPosition().getX());
        assertEquals(90, beaconList.get(0).getPosition().getY());

        assertEquals(100, beaconList.get(1).getPosition().getX());
        assertEquals(-110, beaconList.get(1).getPosition().getY());

        assertEquals(100, beaconList.get(2).getPosition().getX());
        assertEquals(140, beaconList.get(2).getPosition().getY());

        assertEquals(100, beaconList.get(3).getPosition().getX());
        assertEquals(-160, beaconList.get(3).getPosition().getY());
    }*/
}
