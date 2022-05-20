package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Crew;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.*;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrewTest {
    private List<Sailor> sailors;
    private List<Entities> entities;
    private Crew crew;
    Goal goal;
    Associations associations;

    @BeforeEach
    void init(){
        sailors = new ArrayList<>();
        entities = new ArrayList<>();
        List<Checkpoint> checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(5000,5000,0),new Circle(100)));
        goal=new Goal("",(ArrayList<Checkpoint>) checkpoints);
        Ship basicShip = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle(3, 4, 0));
        Captain captain = new Captain(basicShip, sailors, goal, new FictitiousCheckpoint(checkpoints), new Wind(0, 0));
        crew= captain.getCrew();
        associations = captain.getAssociations();
    }

    @Test
    void sailorToTurnWithRudderTest(){
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        entities.add(new Rudder(0, 0));
        String action = crew.sailorsTurnWithRudder(20).toString();
        assertEquals("[]", action);

        associations.addAssociation(sailors.get(0), entities.get(0));
        action = crew.sailorsTurnWithRudder(20).toString();
        assertEquals("[{\"sailorId\":0,\"type\":\"TURN\",\"rotation\":20.0}]", action);
    }

    @Test
    void sailorToTurnWithRudderTest2(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        entities.add(new Oar(1, 0));
        entities.add(new Rudder(0, 0));

        assertEquals(new ArrayList<>(),crew.sailorsTurnWithRudder(20));
    }

    @Test
    void sailorsUseSailTest(){
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        entities.add(new Sail(0, 0, false));
        String action = crew.sailorsUseSail(true).toString();
        assertEquals("[]", action);

        associations.addAssociation(sailors.get(0), entities.get(0));
        action = crew.sailorsUseSail(true).toString();
        assertEquals("[{\"sailorId\":0,\"type\":\"LIFT_SAIL\"}]", action);

        action = crew.sailorsUseSail(false).toString();
        assertEquals("[{\"sailorId\":0,\"type\":\"LOWER_SAIL\"}]", action);
    }
    @Test
    void sailorsMoveTest(){
        Associations associations=mock(Associations.class);
        Sailor sailor=new Sailor(0,0,0,"");
        when(associations.isFree(sailor)).thenReturn(false);
        when(associations.getAssociatedEntity(sailor)).thenReturn(new Oar(2,2));
        sailors.add(sailor);
        crew=new Crew(sailors,associations);
        assertEquals("[{\"sailorId\":0,\"type\":\"MOVING\",\"xdistance\":2,\"ydistance\":2}]", crew.sailorsMove().toString());
        assertEquals("[{\"sailorId\":0,\"type\":\"OAR\"}]", crew.sailorsOar().toString());

        when(associations.isFree(sailor)).thenReturn(true);
        assertEquals("[]", crew.sailorsMove().toString());
        assertEquals("[]", crew.sailorsOar().toString());
        when(associations.isFree(sailor)).thenReturn(false);
        when(associations.getAssociatedEntity(sailor)).thenReturn(new Rudder(2,2));
        assertEquals("[]", crew.sailorsMove().toString());
        assertEquals("[]", crew.sailorsOar().toString());

    }

    @Test
    void useWatchTest() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        entities.add(new Watch(0, 0));
        String action = crew.useWatch().toString();
        assertEquals("[]", action);

        associations.addAssociation(sailors.get(0), entities.get(0));
        action = crew.useWatch().toString();
        assertEquals("[{\"sailorId\":0,\"type\":\"USE_WATCH\"}]", action);
    }

    @Test
    void makeBoatMoveTest() {
        sailors.clear();
        sailors.add(new Sailor(0, 0, 0, "Sailor0"));
        entities.clear();
        entities.add(new Oar(1, 1));
        associations.addAssociation(sailors.get(0), entities.get(0));
        assertEquals("[{\"sailorId\":0,\"type\":\"MOVING\",\"xdistance\":1,\"ydistance\":1}, {\"sailorId\":0,\"type\":\"OAR\"}]", crew.makeBoatMove().toString());
    }

    @Test
    void sailorsActions() {
        sailors.clear();
        sailors.add(new Sailor(0, 0, 0, "Sailor0"));
        entities.clear();

        entities.add(new Oar(1, 1));
        associations.addAssociation(sailors.get(0), entities.get(0));
        assertEquals("[]", crew.sailorsOar().toString());
        associations.dissociateAll();
        entities.clear();

        entities.add(new Rudder(1, 1));
        associations.addAssociation(sailors.get(0), entities.get(0));
        assertEquals("[]", crew.sailorsTurnWithRudder(2).toString());
        associations.dissociateAll();
        entities.clear();

        entities.add(new Sail(1, 1, true));
        associations.addAssociation(sailors.get(0), entities.get(0));
        assertEquals("[]", crew.sailorsUseSail(true).toString());

        assertEquals("[]", crew.useWatch().toString());
        associations.dissociateAll();
        entities.clear();
        entities.add(new Watch(1, 1));
        associations.addAssociation(sailors.get(0), entities.get(0));
        assertEquals("[]", crew.useWatch().toString());
    }
}
