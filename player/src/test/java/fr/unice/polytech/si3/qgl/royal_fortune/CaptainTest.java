package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Checkpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CaptainTest {
    private Ship basicShip;
    private Captain captain;
    private Checkpoint checkpoint;
    private List<Sailor> sailors;
    private List<Entities> entities;
    private DirectionsManager directionsManager;

    @BeforeEach
    void init(){
        sailors = new ArrayList<>();
        entities = new ArrayList<>();
        entities.add(new Sail());

        basicShip = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));
        captain = new Captain(basicShip, null, null, null, new Wind(Math.PI,10),null);

    }

    @Test
    void getSailDecisionNoWindTest(){
        Captain captain = new Captain(basicShip, null, null, null, new Wind(0,0),null);
        Optional<Boolean> res = captain.getSailDecision();
        assertEquals(Optional.empty(), res);
    }
    @Test
    void getSailDecisionWindForUsSailCloseTest(){
        Captain captain = new Captain(basicShip, null, null, null, new Wind(0,10),null);
        Optional<Boolean> res = captain.getSailDecision();
        assertTrue(res.get());
    }
    @Test
    void getSailDecisionWindForUsSailOpenTest(){
        basicShip.getSail().setOpenned(false);
        Captain captain = new Captain(basicShip, null, null, null, new Wind(Math.PI,10),null);
        Optional<Boolean> res = captain.getSailDecision();
        assertEquals(Optional.empty(), res);
    }


    @Test
    void getSailDecisionWindNotForUsSailCloseTest(){
        Captain captain = new Captain(basicShip, null, null, null, new Wind(Math.PI,10),null);
        Optional<Boolean> res = captain.getSailDecision();
        assertEquals(Optional.empty(), res);
    }

    @Test
    void getSailDecisionWindNotForUsSailOpenTest(){
        basicShip.getSail().setOpenned(true);
        Captain captain = new Captain(basicShip, null, null, null, new Wind(Math.PI,10),null);
        Optional<Boolean> res = captain.getSailDecision();
        assertFalse(res.get());
    }

    @Test
    void getRudderDecisionNoNeedRudderTest(){
        double angleMove = 0.0;
        double angleSailorShouldMake = 0.0;

        boolean angleIsZero = captain.getRudderDecision(angleMove, angleSailorShouldMake);
        assertFalse(angleIsZero);

        angleMove = Math.PI/3;
        angleSailorShouldMake = Math.PI/3;

        angleIsZero = captain.getRudderDecision(angleMove, angleSailorShouldMake);
        assertFalse(angleIsZero);
    }

    @Test
    void getRudderDecisionNeedRudderTest(){
        double angleMove = Math.PI/3;
        double angleSailorShouldMake = 0.0;
        boolean angleIsZero = captain.getRudderDecision(angleMove, angleSailorShouldMake);
        assertTrue(angleIsZero);

        angleMove = Math.PI;
        angleSailorShouldMake = Math.PI/4;

        angleIsZero = captain.getRudderDecision(angleMove, angleSailorShouldMake);
        assertTrue(angleIsZero);
    }

    @Test
    void computeAngleToTurnRudderTest(){
        double angleMove = Math.PI/5;
        double angleMadeBySailors = 0.0;
        double angleToTurn = captain.computeAngleToTurnRudder(angleMove, angleMadeBySailors);
        assertEquals(Math.PI/5, angleToTurn);
    }

    @Test
    void computeAngleToTurnRudderMaxTest(){
        double angleMove = Math.PI;
        double angleMadeBySailors = 0.0;
        double angleToTurn = captain.computeAngleToTurnRudder(angleMove, angleMadeBySailors);
        assertEquals(Math.PI/4, angleToTurn);
    }



/*

    @Test
    void numberOfSailorToTurnTest(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        // Left oars
        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        // Right oars
        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        Captain captain = new Captain(basicShip, sailors, null, null, new Wind(0,0));
        assertEquals(6, basicShip.getNbrOar());
        assertEquals(3, captain.oarWeight(Math.PI));
    }

    @Test
    void numberOfSailorToTurnTestAngleLimiting(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        // Left oars
        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        // Right oars
        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        Captain captain = new Captain(basicShip, sailors, null, null, new Wind(0,0));
        assertEquals(6, basicShip.getNbrOar());
        assertEquals(1, captain.oarWeight(Math.PI / 6));
    }

    @Test
    void numberOfSailorToTurnTestSailorLimiting(){
        // Initialize 2 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));

        // Left oars
        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        // Right oars
        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        Captain captain = new Captain(basicShip, sailors, null, null, new Wind(0,0));
        assertEquals(6, basicShip.getNbrOar());
        assertEquals(2, captain.oarWeight(Math.PI));
    }

    @Test
    void numberOfSailorToTurnTestRightOarLimiting(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        // Left oars
        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        // Right oars
        entities.add(new Oar(0, 3));

        Captain captain = new Captain(basicShip, sailors, null, null, new Wind(0,0));
        assertEquals(4, basicShip.getNbrOar());
        assertEquals(1, captain.oarWeight(Math.PI));
    }

    @Test
    void numberOfSailorToTurnTestLeftOarLimiting(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        // Left oars
        entities.add(new Oar(0, 0));

        // Right oars
        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        Captain captain = new Captain(basicShip, sailors, null, null, new Wind(0,0));
        assertEquals(4, basicShip.getNbrOar());
        assertEquals(1, captain.oarWeight(- Math.PI));
    }


    @Test
    //Moving straight
    void roundDecisionsTest() {
        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 1.5),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        entities.add(new Oar(0, 0));
        entities.add(new Oar(0, 1));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(1, 1));

        checkpoint = new Checkpoint(new Position(0,500,0), new Circle("Circle", 50));
        ArrayList<Checkpoint> checkpointArrayList = new ArrayList<>();
        checkpointArrayList.add(checkpoint);
        Goal goal = new Goal("REGATTA", checkpointArrayList);

        captain = new Captain(ship, sailors, goal, new FictitiousCheckpoint(checkpointArrayList), new Wind(0,0));
        captain.roundDecisions();

        assertEquals(4, captain.getRoundActions().size());
    }

    @Test
        //Turning
    void roundDecisions2Test() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        entities.add(new Oar(0, 0));
        entities.add(new Oar(0, 1));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(1, 1));

        checkpoint = new Checkpoint(new Position(0,500,0), new Circle("Circle", 50));
        ArrayList<Checkpoint> checkpointArrayList = new ArrayList<>();
        checkpointArrayList.add(checkpoint);
        Goal goal = new Goal("REGATTA", checkpointArrayList);

        captain = new Captain(basicShip, sailors, goal, new FictitiousCheckpoint(checkpointArrayList), new Wind(0,0));
        captain.roundDecisions();

        assertEquals(4, captain.getRoundActions().size());
    }

    @Test
    void roundDecisionOutTest() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        entities.add(new Oar(0, 0));
        entities.add(new Oar(0, 1));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(1, 1));

        checkpoint = new Checkpoint(new Position(0,500,0), new Circle("Circle", 50));
        ArrayList<Checkpoint> checkpointArrayList = new ArrayList<>();
        checkpointArrayList.add(checkpoint);
        Goal goal = new Goal("REGATTA", checkpointArrayList);

        captain = new Captain(basicShip, sailors, goal, new FictitiousCheckpoint(checkpointArrayList), new Wind(0,0));

        assertEquals("[{\"sailorId\":0,\"type\":\"MOVING\",\"xdistance\":0,\"ydistance\":1},{\"sailorId\":1,\"type\":\"MOVING\",\"xdistance\":1,\"ydistance\":0},{\"sailorId\":0,\"type\":\"OAR\"},{\"sailorId\":1,\"type\":\"OAR\"}]", captain.roundDecisions());
    }

    @Test
    void createAction() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        entities.add(new Oar(0, 0));
        entities.add(new Oar(0, 1));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(1, 1));

        checkpoint = new Checkpoint(new Position(0,500,0), new Circle("Circle", 50));
        ArrayList<Checkpoint> checkpointArrayList = new ArrayList<>();
        checkpointArrayList.add(checkpoint);
        Goal goal = new Goal("REGATTA", checkpointArrayList);

        captain = new Captain(basicShip, sailors, goal, new FictitiousCheckpoint(checkpointArrayList), new Wind(0,0));
        captain.roundDecisions();

        assertEquals("{\"sailorId\":0,\"type\":\"MOVING\",\"xdistance\":0,\"ydistance\":1},{\"sailorId\":1,\"type\":\"MOVING\",\"xdistance\":1,\"ydistance\":0},{\"sailorId\":0,\"type\":\"OAR\"},{\"sailorId\":1,\"type\":\"OAR\"}", captain.createAction());
    }

    /*@Test
    void askSailorToMoveToRudderTest(){
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));
        sailors.add(new Sailor(4, 1, 2, "sailor4"));
        Sailor nearestSailor = new Sailor(5, 3, 3, "sailor5");
        sailors.add(nearestSailor);

        entities.add(new Oar(0, 0));
        entities.add(new Oar(0, 1));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(1, 1));

        captain = new Captain(basicShip, sailors, null, null, new Wind(0,0));
        assertEquals(4, basicShip.getEntities().size());
        captain.getCrew().sailorMoveToRudder();
        assertEquals(0, captain.getRoundActions().size());

        entities.add(new Rudder(3, 4));
        assertEquals(5, basicShip.getEntities().size());
        List<Action> actions=captain.getCrew().sailorMoveToRudder();
        assertEquals(1, actions.size());

        assertEquals(new MovingAction(nearestSailor.getId(), 0, 1).toString(),actions.get(0).toString());
    }

    @Test
    void disassociateTest() {
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sogeking"));
        sailors.get(0).setTargetEntity(new Oar(0, 0));
        Captain captain = new Captain(new Ship(null, 1, null, null, null, null, null), sailors, null, null, new Wind(0,0));

        assertTrue(sailors.get(0).getTargetEntity() != null);
        captain.disassociate();
        assertTrue(sailors.get(0).getTargetEntity() == null);
    }
*/


}