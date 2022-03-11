package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.target.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Checkpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FictitiousCheckpointTest {
    FictitiousCheckpoint emptyFictitiousCheckpoints;

    @BeforeEach
    void init(){
        emptyFictitiousCheckpoints = new FictitiousCheckpoint(null);
    }

    @Test
    void createFictitiousCheckpointTestVertically(){
        // CurrentCheckpoint is located in (0,0) and has a radius of 100
        Position currentCheckpointPosition = new Position(0, 0, 0);
        Circle currentCheckpointShape= new Circle("Circle", 100);
        Checkpoint currentCheckpoint = new Checkpoint(currentCheckpointPosition, currentCheckpointShape);

        // NextCheckpoint is located in (0,1000) and has a radius of 100
        Position nextCheckpointPosition = new Position(0, 1000, 0);
        Circle nextCheckpointShape = new Circle("Circle", 100);
        Checkpoint nextCheckpoint = new Checkpoint(nextCheckpointPosition, nextCheckpointShape);

        Checkpoint fictitiousCheckpoint = emptyFictitiousCheckpoints.createFictitiousCheckpoint(currentCheckpoint, nextCheckpoint);
        assertEquals(0, fictitiousCheckpoint.getPosition().getX());
        assertEquals(50, fictitiousCheckpoint.getPosition().getY());
        assertEquals(50, ((Circle) fictitiousCheckpoint.getShape()).getRadius());
    }

    @Test
    void createFictitiousCheckpointTestHorizontally(){
        // CurrentCheckpoint is located in (0,0) and has a radius of 100
        Position currentCheckpointPosition = new Position(0, 0, 0);
        Circle currentCheckpointShape= new Circle("Circle", 100);
        Checkpoint currentCheckpoint = new Checkpoint(currentCheckpointPosition, currentCheckpointShape);

        // NextCheckpoint is located in (1000,0) and has a radius of 100
        Position nextCheckpointPosition = new Position(1000, 0, 0);
        Circle nextCheckpointShape = new Circle("Circle", 100);
        Checkpoint nextCheckpoint = new Checkpoint(nextCheckpointPosition, nextCheckpointShape);

        Checkpoint fictitiousCheckpoint = emptyFictitiousCheckpoints.createFictitiousCheckpoint(currentCheckpoint, nextCheckpoint);
        assertEquals(50, fictitiousCheckpoint.getPosition().getX());
        assertEquals(0, fictitiousCheckpoint.getPosition().getY());
        assertEquals(50, ((Circle) fictitiousCheckpoint.getShape()).getRadius());
    }

    @Test
    void createFictitiousCheckpointTestDiagonally(){
        // CurrentCheckpoint is located in (0,0) and has a radius of 100
        Position currentCheckpointPosition = new Position(0, 0, 0);
        Circle currentCheckpointShape= new Circle("Circle", 100);
        Checkpoint currentCheckpoint = new Checkpoint(currentCheckpointPosition, currentCheckpointShape);

        // NextCheckpoint is located in (1000,1000) and has a radius of 100
        Position nextCheckpointPosition = new Position(1000, 1000, 0);
        Circle nextCheckpointShape = new Circle("Circle", 100);
        Checkpoint nextCheckpoint = new Checkpoint(nextCheckpointPosition, nextCheckpointShape);

        Checkpoint fictitiousCheckpoint = emptyFictitiousCheckpoints.createFictitiousCheckpoint(currentCheckpoint, nextCheckpoint);
        assertEquals(35, Math.round(fictitiousCheckpoint.getPosition().getX()));
        assertEquals(35, Math.round(fictitiousCheckpoint.getPosition().getY()));
        assertEquals(50, ((Circle) fictitiousCheckpoint.getShape()).getRadius());
    }

    @Test
    void createFictitiousCheckpointTest(){
        // CurrentCheckpoint is located in (0,0) and has a radius of 100
        Position currentCheckpointPosition = new Position(0, 0, 0);
        Circle currentCheckpointShape= new Circle("Circle", 100);
        Checkpoint currentCheckpoint = new Checkpoint(currentCheckpointPosition, currentCheckpointShape);

        // NextCheckpoint is located in (120,-90) and has a radius of 20
        Position nextCheckpointPosition = new Position(120, -90, 0);
        Circle nextCheckpointShape = new Circle("Circle", 20);
        Checkpoint nextCheckpoint = new Checkpoint(nextCheckpointPosition, nextCheckpointShape);

        Checkpoint fictitiousCheckpoint = emptyFictitiousCheckpoints.createFictitiousCheckpoint(currentCheckpoint, nextCheckpoint);
        assertEquals(40, fictitiousCheckpoint.getPosition().getX());
        assertEquals(-30, fictitiousCheckpoint.getPosition().getY());
        assertEquals(50, ((Circle) fictitiousCheckpoint.getShape()).getRadius());
    }

    @Test
    void createFictitiousCheckpointsTest(){
        List<Checkpoint> originalCheckpoints = new ArrayList<>();

        // CurrentCheckpoint is located in (0,0) and has a radius of 100
        Position currentCheckpointPosition = new Position(0, 0, 0);
        Circle currentCheckpointShape= new Circle("Circle", 100);
        Checkpoint currentCheckpoint = new Checkpoint(currentCheckpointPosition, currentCheckpointShape);
        originalCheckpoints.add(currentCheckpoint);

        // NextCheckpoint is located in (120,-90) and has a radius of 20
        Position secondCheckpointPosition = new Position(120, -90, 0);
        Circle secondCheckpointShape = new Circle("Circle", 20);
        Checkpoint secondCheckpoint = new Checkpoint(secondCheckpointPosition, secondCheckpointShape);
        originalCheckpoints.add(secondCheckpoint);

        // NextCheckpoint is located in (200,-90) and has a radius of 30
        Position thirdCheckpointPosition = new Position(200, -90, 0);
        Circle thirdCheckpointShape = new Circle("Circle", 30);
        Checkpoint thirdCheckpoint = new Checkpoint(thirdCheckpointPosition, thirdCheckpointShape);
        originalCheckpoints.add(thirdCheckpoint);

        FictitiousCheckpoint fictitiousCheckpoints = new FictitiousCheckpoint(originalCheckpoints);

        // First Checkpoint
        assertEquals(40, fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getX());
        assertEquals(-30, fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getY());
        assertEquals(50, ((Circle) fictitiousCheckpoints.getCurrentCheckPoint().getShape()).getRadius());

        // Second Checkpoint
        fictitiousCheckpoints.nextCheckPoint();
        assertEquals(130, fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getX());
        assertEquals(-90, fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getY());
        assertEquals(10, ((Circle) fictitiousCheckpoints.getCurrentCheckPoint().getShape()).getRadius());

        // Third Checkpoint (unchanged)
        fictitiousCheckpoints.nextCheckPoint();
        assertEquals(200, fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getX());
        assertEquals(-90, fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getY());
        assertEquals(30, ((Circle) fictitiousCheckpoints.getCurrentCheckPoint().getShape()).getRadius());

        fictitiousCheckpoints.nextCheckPoint();
        assertNull(fictitiousCheckpoints.getCurrentCheckPoint());
    }

}
