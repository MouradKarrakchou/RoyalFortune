package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Crew;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
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
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrewTest {
    private Ship basicShip;
    private Captain captain;
    private List<Checkpoint> checkpoints;
    private List<Sailor> sailors;
    private List<Entities> entities;
    private Crew crew;
    Goal goal;
    Associations associations = new Associations();

    @BeforeEach
    void init(){
        sailors = new ArrayList<>();
        entities = new ArrayList<>();
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
        captain=new Captain(basicShip,sailors,goal,new FictitiousCheckpoint(checkpoints), new Wind(0,0), null);
        crew=captain.getCrew();
    }

    @Test
    void sailorToTurnWithRudderTest(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        entities.add(new Rudder(0, 0));
        assertEquals("[]",crew.sailorsTurnWithRudder(20).toString());

        crew.getAssociations().addAssociation(sailors.get(0), entities.get(0));

        assertEquals("[{\"sailorId\":0,\"type\":\"TURN\",\"rotation\":20.0}]",crew.sailorsTurnWithRudder(20).toString());
    }
    @Test
    void sailorToTurnWithRudderTest2(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        entities.add(new Oar(1, 0));
        entities.add(new Rudder(0, 0));

        assertEquals(new ArrayList<>(),crew.sailorsTurnWithRudder(20));}
}
