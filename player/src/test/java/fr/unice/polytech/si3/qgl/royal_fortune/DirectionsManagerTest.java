package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;

import java.text.DecimalFormat;
import java.util.ArrayList;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Checkpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DirectionsManagerTest {
    private DirectionsManager dirMan;
    private ArrayList<Entities> entities;
    private final DecimalFormat df = new DecimalFormat("#.##");

    @BeforeEach
    public void init(){
        entities = new ArrayList<>();
        dirMan = new DirectionsManager(null, null);
    }

    @Test
    //Positive angle
    void angleCalculatorTest() {
        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle(3, 4, 0));

        Checkpoint checkpoint = new Checkpoint(new Position(0, 50, 0), new Circle(50));

        ArrayList<Checkpoint> checkpointArrayList = new ArrayList<>();
        checkpointArrayList.add(checkpoint);

        dirMan = new DirectionsManager(ship, new FictitiousCheckpoint(checkpointArrayList));

        double angle = dirMan.angleCalculator()[0];
        assertEquals(Math.PI/2, angle);
    }

    @Test
    void isInConeTrueTest() {
        dirMan = new DirectionsManager(null, null);
        assertTrue(dirMan.isInCone());
    }

    @Test
    void isInConeFalseTest(){
        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle(3, 4, 0));

        Checkpoint checkpoint = new Checkpoint(new Position(0, 50, 0), new Circle(50));

        ArrayList<Checkpoint> checkpointArrayList = new ArrayList<>();
        checkpointArrayList.add(checkpoint);

        dirMan = new DirectionsManager(ship, new FictitiousCheckpoint(checkpointArrayList));

        dirMan.update();
        assertFalse(dirMan.isInCone());
    }

    @Test
    void isConeTooSmallTrueTest() {
        //6 entities
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

       Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 1.5),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle(3, 4, 0));

        dirMan = new DirectionsManager(ship, null);
        assertTrue(dirMan.isConeTooSmall());
    }

    @Test
    void isConeTooSmallFalseTest() {
        Ship mockShip = mock(Ship.class);
        when(mockShip.getNbrOar()).thenReturn(Integer.valueOf((int) -Math.PI));
        dirMan = new DirectionsManager(mockShip, null);
        assertFalse(dirMan.isConeTooSmall());
    }

    @Test
    void computeDistanceBetweenTwoPositionSamePositionTest(){
        Position p1 = new Position(0,0,0);
        Position p2 = new Position(0,0,0);
        double computeDistance = dirMan.computeDistanceBetweenTwoPosition(p1,p2);
        assertEquals(0.0,computeDistance);
    }


    @Test
    void computeDistanceBetweenTwoPosition_P1x_SUP_P2_AND_P1y_SUP_P2y_Test(){
        Position p1 = new Position(100,100,0);
        Position p2 = new Position(0,0,0);
        double computeDistance = dirMan.computeDistanceBetweenTwoPosition(p1,p2);
        assertTrue(Math.abs(141.42-computeDistance)<0.01);
    }

    @Test
    void computeDistanceBetweenTwoPosition_P1x_INF_P2_AND_P1y_SUP_P2y_Test(){
        Position p1 = new Position(-100,100,0);
        Position p2 = new Position(0,0,0);
        double computeDistance = dirMan.computeDistanceBetweenTwoPosition(p1,p2);
        assertTrue(Math.abs(141.42-computeDistance)<0.01);
    }

    @Test
    void computeDistanceBetweenTwoPosition_P1x_INF_P2_AND_P1y_INF_P2y_Test(){
        Position p1 = new Position(-100,-100,0);
        Position p2 = new Position(0,0,0);
        double computeDistance = dirMan.computeDistanceBetweenTwoPosition(p1,p2);
        assertTrue(Math.abs(141.42-computeDistance)<0.01);
    }

    @Test
    void computeDistanceBetweenTwoPosition_P1x_SUP_P2_AND_P1y_INF_P2y_Test(){
        Position p1 = new Position(-100,100,0);
        Position p2 = new Position(0,0,0);
        double computeDistance = dirMan.computeDistanceBetweenTwoPosition(p1,p2);
        assertTrue(Math.abs(141.42-computeDistance)<0.01);

    }

    @Test
    void computeNumerateurTest(){
        Position p1 = new Position(-100,100,0);
        Position p2 = new Position(0,0,0);
        double computeNumerateur = dirMan.computeNumerateur(p1,p2);
        assertTrue(Math.abs(-100-computeNumerateur)<0.01);
    }

    @Test
    void computeNumerateurSamePositionTest(){
        Position p1 = new Position(0,0,0);
        Position p2 = new Position(0,0,0);
        double computeNumerateur = dirMan.computeNumerateur(p1,p2);
        assertEquals(0.0, computeNumerateur);
    }

    @Test
    void computeAngleConeTest(){
        Position p1 = new Position(-0,5,-Math.PI/3);
        Position p2 = new Position(0,0,0);
        Checkpoint checkpoint = new Checkpoint(p1, new Circle(p1,500));
        double computeAngleCone = dirMan.computeAngleCone(checkpoint,p2);
        assertTrue(Math.abs(1.56-computeAngleCone)<0.01);
    }

    @Test
    void computeAngleConeSamePosTest(){
        boolean catchException = false;
        Position p1 = new Position(-0,0,-Math.PI/3);
        Position p2 = new Position(0,0,0);
        Checkpoint checkpoint = new Checkpoint(p1, new Circle(p1,500));
        try{
            double computeAngleCone = dirMan.computeAngleCone(checkpoint,p2);
        }
        catch (ArithmeticException e){
            catchException = true;
        }
        assertTrue(catchException);    }

    @Test
    void computeAngleMoveTest(){
        Position p1 = new Position(0,5,-Math.PI/3);
        Position p2 = new Position(0,0,0);
        double computeAngleMove = dirMan.computeAngleMove(p1,p2);
        assertTrue(Math.abs(1.57-computeAngleMove)<0.01);

        p1 = new Position(74,5,-Math.PI/3);
        p2 = new Position(435,-74,-2);
        computeAngleMove = dirMan.computeAngleMove(p1,p2);
        assertTrue(Math.abs(1.36-computeAngleMove)<0.01);
    }


    @Test
    void computeAngleMoveSamePositionTest(){
        boolean catchException = false;
        Position p1 = new Position(0,0,0);
        Position p2 = new Position(0,0,0);
        try{
            double computeAngleMove = dirMan.computeAngleMove(p1,p2);
        }
        catch (ArithmeticException e){
            catchException = true;
        }
        assertTrue(catchException);
    }

    @Test
    void adjustAccuracySupPi(){
        double angleSuppPi = Math.PI+15;
        double fixAngle = dirMan.adjustAccuracy(angleSuppPi);
        assertTrue(fixAngle < Math.PI && fixAngle > -Math.PI);

        double angleLessPi = Math.PI-15;
        fixAngle = dirMan.adjustAccuracy(angleSuppPi);
        assertTrue(fixAngle < Math.PI && fixAngle > -Math.PI);

        double angleInPi = Math.PI-15;
        fixAngle = dirMan.adjustAccuracy(angleSuppPi);
        assertTrue(fixAngle < Math.PI && fixAngle > -Math.PI);

        double anglePi = Math.PI;
        fixAngle = dirMan.adjustAccuracy(angleSuppPi);
        assertTrue(fixAngle < Math.PI && fixAngle > -Math.PI);

        double angleMinusPi = Math.PI;
        fixAngle = dirMan.adjustAccuracy(angleSuppPi);
        assertTrue(fixAngle < Math.PI && fixAngle > -Math.PI);
    }

    @Test
    void distToCheckPointTest(){
        Position checkpointPosition = new Position(-100,100,0);
        Position shipPosition = new Position(0,0,0);
        double angle = Math.PI;
        double computeDistance = dirMan.distToCheckPoint(angle,checkpointPosition,shipPosition);
        assertTrue(Math.abs(140.72-computeDistance)<0.01);

        checkpointPosition = new Position(-100,10,9);
        shipPosition = new Position(-999,85,-74);
        angle = Math.PI;
        computeDistance = dirMan.distToCheckPoint(angle,checkpointPosition,shipPosition);
        assertTrue(Math.abs(902.21-computeDistance)<0.01);
    }

    @Test
    void distToCheckPointSamePositionTest(){
        Position checkpointPosition = new Position(0,0,0);
        Position shipPosition = new Position(0,0,0);
        double angle = Math.PI;
        double computeDistance = dirMan.distToCheckPoint(angle,checkpointPosition,shipPosition);
        assertTrue(Math.abs(computeDistance-1.) < 0.2);

    }

    @Test
    void checkSignTest(){
        Position checkpointPosition = new Position(-100,100,193);
        Position shipPosition = new Position(0,0,0);
        double angle = 17*Math.PI;
        double computeDistance = dirMan.checkSign(angle,checkpointPosition,shipPosition);
        assertTrue(Math.abs(-53.41-computeDistance)<0.01);

        checkpointPosition = new Position(-100,10,0.18*Math.PI);
        shipPosition = new Position(-999,85,-74);
        angle = -Math.PI;
        computeDistance = dirMan.checkSign(angle,checkpointPosition,shipPosition);
        assertTrue(Math.abs(3.14-computeDistance)<0.01);
    }

    @Test
    void checkSignSamePosTest(){
        Position checkpointPosition = new Position(0,0,0);
        Position shipPosition = new Position(0,0,0);
        double angle = Math.PI;
        double computeDistance = dirMan.checkSign(angle,checkpointPosition,shipPosition);
        assertTrue(Math.abs(computeDistance-3.14) < 0.2);
    }


}