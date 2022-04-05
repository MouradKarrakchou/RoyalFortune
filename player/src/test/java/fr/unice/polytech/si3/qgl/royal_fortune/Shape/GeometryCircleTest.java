package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryCircle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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

        assertTrue(GeometryCircle.rectangleIsInCircle(r, seaEntitiePos, radius));
    }

    @Test
    void rectangleIsInCircleLimitTest(){
        double radius = Math.sqrt(Math.pow(50, 2) + Math.pow(50, 2));
        double h = 100;
        double w = 100;
        double o = 0;
        Position seaEntitiePos = new Position(0,0,0);

        Rectangle r = new Rectangle(w, h , o);

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

        List<Beacon> beaconList = GeometryCircle.generateBeacon(shipPosition, checkpointPosition, reefPosition, reefShape);

        assertEquals(2, beaconList.size());

        assertEquals(100, beaconList.get(0).getPosition().getX());
        assertEquals(125, beaconList.get(0).getPosition().getY());

        assertEquals(100, beaconList.get(1).getPosition().getX());
        assertEquals(-145, beaconList.get(1).getPosition().getY());
    }*/

    @Test
    void computeNormalVectorShipCheckpointTest() {
        Position shipPosition = new Position(0, 0, 0);
        Position checkpointPosition = new Position(1000, 0, 0);

        double[] vectors = GeometryCircle.computeNormalVectorShipCheckpoint(shipPosition, checkpointPosition);
        assertEquals(-0.0, vectors[0]);
        assertEquals(1, vectors[1]);


        shipPosition = new Position(23.6, -11.4, 0);
        checkpointPosition = new Position(-4.4, 3.2, 0);

        vectors = GeometryCircle.computeNormalVectorShipCheckpoint(shipPosition, checkpointPosition);
        assertEquals(-0.4623495415374716, vectors[0]);
        assertEquals(-0.8866977508937811, vectors[1]);
    }
/*
    @Test
    void createUpAndDownBeaconUsingCircleReef() {
        Position reefPosition = new Position(17.7, -23.6, 0);
        double normalVectorX = 44.5;
        double normalVectorY = 32.9;
        double circleRadius = 50;

        Beacon beacon = GeometryCircle.createUpBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, new ArrayList<>());
        Position beaconExpectedPosition = new Position(5580.2, 4088.9);
        assertEquals(beaconExpectedPosition.getX(), beacon.getPosition().getX());
        assertEquals(beaconExpectedPosition.getY(), beacon.getPosition().getY());

        beacon = GeometryCircle.createDownBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, new ArrayList<>());
        beaconExpectedPosition = new Position(-5544.8, -4136.1);
        assertEquals(beaconExpectedPosition.getX(), beacon.getPosition().getX());
        assertEquals(beaconExpectedPosition.getY(), beacon.getPosition().getY());
    }*/

}
