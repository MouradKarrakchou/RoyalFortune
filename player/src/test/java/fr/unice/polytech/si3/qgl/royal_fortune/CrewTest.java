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
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrewTest {
    private Ship basicShip;
    private Captain captain;
    private List<Checkpoint> checkpoints;
    private List<Sailor> sailors;
    private List<Entities> entities;
    private Crew crew;
    Goal goal;
    Associations associations;

    @BeforeEach
    void init(){
        sailors = new ArrayList<>();
        entities = new ArrayList<>();
        checkpoints=new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(5000,5000,0),new Circle(100)));
        goal=new Goal("",(ArrayList<Checkpoint>) checkpoints);
        basicShip = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle(3, 4, 0));
        captain=new Captain(basicShip,sailors,goal,new FictitiousCheckpoint(checkpoints), new Wind(0,0));
        crew=captain.getCrew();
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
}
