package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CaptainTest {
    private Ship basicShip;
    private Captain captain;
    private List<Checkpoint> checkpoints;
    private List<Sailor> sailors;
    private List<Entities> entities;
    Goal goal;

    @BeforeEach
    void init() {
        sailors = new ArrayList<>();
        entities = new ArrayList<>();
        entities.add(new Sail(0, 0, false));
        checkpoints=new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(5000,5000,0),new Circle("",100)));
        goal=new Goal("",(ArrayList<Checkpoint>) checkpoints);
        basicShip = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));
        captain=new Captain(basicShip,sailors,goal,new FictitiousCheckpoint(checkpoints), new Wind(0,0));
    }

    @Test
    void getSailDecisionNoWindTest() {
        assertEquals(Optional.empty(), captain.getSailDecision());
    }

    @Test
    void getSailDecisionWindRightDirectionTest() {
        captain.setWind(new Wind(0, 50));
        assertEquals(Optional.of(true), captain.getSailDecision());
    }

    @Test
    void getSailDecisionWindWrongDirectionTest() {
        captain.setWind(new Wind(Math.PI, 50));
        entities.clear();
        entities.add(new Sail(0, 0, true));
        assertEquals(Optional.of(false), captain.getSailDecision());
    }

    @Test
    void getSailDecisionUpperLimitTest() {
        basicShip.setPosition(new Position(0, 0, Math.PI/2));
        captain.setWind(new Wind(0, 50));
        entities.clear();
        entities.add(new Sail(0, 0, true));
        assertEquals(Optional.of(false), captain.getSailDecision());
    }

    @Test
    void getSailDecisionLowerLimitTest() {
        basicShip.setPosition(new Position(0, 0, -Math.PI/2));
        captain.setWind(new Wind(0, 50));
        entities.clear();
        entities.add(new Sail(0, 0, false));
        assertEquals(Optional.empty(), captain.getSailDecision());
    }

    @Test
    void oarWeightTest() {
        sailors.clear();
        entities.clear();
        assertEquals(0, captain.oarWeight(0));
    }

    @Test
    void oarWeight2Test() {
        sailors.clear();
        entities.clear();
        entities.add(new Oar(0, 0));
        assertEquals(0, captain.oarWeight(Math.PI));
    }

    @Test
    void oarWeight3Test() {
        sailors.clear();
        entities.clear();
        sailors.add(new Sailor(1, 0, 0, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        entities.add(new Oar(0, 0));
        assertEquals(1, captain.oarWeight(Math.PI));
    }

    @Test
    void oarWeight4Test() {
        sailors.clear();
        entities.clear();
        sailors.add(new Sailor(1, 0, 0, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        entities.add(new Oar(0, 0));
        assertEquals(2, captain.oarWeight(2*Math.PI));
    }

    @Test
    void oarWeight5Test() {
        sailors.clear();
        entities.clear();
        sailors.add(new Sailor(1, 0, 0, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(1, 0, 0, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(1, 0, 0, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        entities.add(new Oar(0, 0));
        entities.add(new Oar(0, 1));
        entities.add(new Oar(1  , 0));
        assertEquals(4, captain.oarWeight(5));
    }

    @Test
    void getWindTest() {
        Wind wind = new Wind(0, 0);
        assertEquals(wind.getOrientation(), captain.getWind().getOrientation());
        assertEquals(wind.getStrength(), captain.getWind().getStrength());

    }

    @Test
    void getRudderDecisionTest() {
        boolean angleIsNotZero = captain.getRudderDecision(Math.pow(10, -3), 0);
        assertFalse(angleIsNotZero);
    }

    @Test
    void getRudderDecision2Test() {
        boolean angleIsNotZero = captain.getRudderDecision(Math.pow(10, -3), -Math.pow(10, -3));
        assertTrue(angleIsNotZero);
    }

    @Test
    void computeAngleToTurnRudderTest() {
        assertEquals(-Math.PI / 4, captain.computeAngleToTurnRudder(0, Math.PI / 4));
        assertEquals(Math.PI / 4, captain.computeAngleToTurnRudder(Math.PI / 4, 0));
        assertEquals(Math.PI / 4, captain.computeAngleToTurnRudder(0, -Math.PI / 4));
        assertEquals(Math.PI / 4, captain.computeAngleToTurnRudder(Math.PI, Math.PI / 2));
        assertEquals(-Math.PI / 4, captain.computeAngleToTurnRudder(-Math.PI, -Math.PI / 2));
        assertEquals(Math.PI / 5 - Math.PI / 6, captain.computeAngleToTurnRudder(Math.PI / 5, Math.PI / 6));
        assertEquals(-Math.PI / 5 + Math.PI / 6, captain.computeAngleToTurnRudder(-Math.PI / 5, -Math.PI / 6));
        assertEquals(0, captain.computeAngleToTurnRudder(0, 0));
        assertEquals(Math.pow(10, -4) - Math.pow(10, -1), captain.computeAngleToTurnRudder(Math.pow(10, -4), Math.pow(10, -1)));
        assertEquals(Math.PI / 4, captain.computeAngleToTurnRudder(1, 1));
        assertEquals(Math.PI / 4, captain.computeAngleToTurnRudder(Math.pow(10, -3), 0));
    }

}