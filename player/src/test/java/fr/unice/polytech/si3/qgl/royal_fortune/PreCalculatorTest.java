package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.*;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.PreCalculator;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PreCalculatorTest {
    Ship ship;
    List<Sailor> sailors;
    Wind wind;
    List<Entities> entities = new ArrayList<>();
    PreCalculator preCalculator;
    SeaMap seaMap;
    List<Checkpoint> checkpoints;
    Sail sail;
    @BeforeEach
    void init(){
        ship=new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle(3, 4, 0));
        sail=new Sail(4, 2, false);
        entities.add(sail);
        wind=new Wind(0,0);
        sailors=new ArrayList<>();
        checkpoints=new ArrayList<>();
        new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);
    }

    @Test
    void needSailorToOarToCheckpointTest() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));
        sailors.add(new Sailor(2, 1, 0, "sailor4"));
        sailors.add(new Sailor(3, 1, 1, "sailor5"));

        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));

        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));

        entities.add(new Sail(4, 2, false));

        checkpoints.add(new Checkpoint(new Position(1000, 0, 0), new Circle(50)));

        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);
        preCalculator = new PreCalculator(ship, sailors, seaMap, new Wind(0, 0));

        assertTrue(preCalculator.needSailorToOarToCheckpoint(ship.getNbrOar()));
    }

    @Test
    void doNotNeedSailorToOarToCheckpointTest() {
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        List<Entities> entities = new ArrayList<>();
        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        entities.add(new Sail(4, 2, false));

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle(3, 4, 0));

        List<Checkpoint> checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(100, 0, 0), new Circle(100)));

        SeaMap seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);

        PreCalculator preCalculator = new PreCalculator(ship, sailors, seaMap, new Wind(0, 0));
        assertFalse(preCalculator.needSailorToOarToCheckpoint(ship.getNbrOar()));
    }

    @Test
    void numberOfSailorToOarToCheckPointTest() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(1000, 0, 0), new Circle(100)));
        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);

        assertEquals(4,preCalculator.numberOfSailorToOarToCheckPoint());
    }
    @Test
    void numberOfSailorToOarToCheckPointTest2() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));

        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(1000, 0, 0), new Circle(100)));
        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);

        assertEquals(2,preCalculator.numberOfSailorToOarToCheckPoint());
    }

    @Test
    void numberOfSailorToOarToCheckPointTest3() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 0, "sailor2"));

        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(50, 0, 0), new Circle(100)));
        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);

        assertEquals(2,preCalculator.numberOfSailorToOarToCheckPoint());
    }
    @Test
    void numberOfSailorToOarToCheckPointTest4() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 0, "sailor2"));

        entities.add(new Oar(0, 0));

        entities.add(new Oar(0, 3));


        checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(264, 0, 0), new Circle(100)));
        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);

        assertEquals(2,preCalculator.numberOfSailorToOarToCheckPoint());
    }
    @Test
    void numberOfSailorToOarToCheckPointTest5() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 0, "sailor2"));
        sailors.add(new Sailor(5, 1, 0, "sailor2"));
        sailors.add(new Sailor(6, 1, 0, "sailor2"));

        entities.add(new Oar(0, 0));

        entities.add(new Oar(0, 3));


        checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(429, 0, 0), new Circle(100)));
        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);

        assertEquals(4,preCalculator.numberOfSailorToOarToCheckPoint());
    }
    @Test
    void numberOfSailorToOarToCheckPointTest6() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 0, "sailor2"));
        sailors.add(new Sailor(5, 1, 0, "sailor2"));
        sailors.add(new Sailor(6, 1, 0, "sailor2"));

        entities.add(new Oar(0, 0));

        entities.add(new Oar(0, 3));


        checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(431, 0, 0), new Circle(100)));
        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);

        assertEquals(6,preCalculator.numberOfSailorToOarToCheckPoint());
    }
    @Test
    void numberOfSailorToOarToCheckPointTest7() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 0, "sailor2"));

        entities.add(new Oar(0, 0));

        entities.add(new Oar(0, 3));


        checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(264, 0, 0), new Circle(100)));
        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);

        assertEquals(2,preCalculator.numberOfSailorToOarToCheckPoint());
    }
    @Test
    void numberOfSailorToOarToCheckPointTest8() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 0, "sailor2"));

        entities.add(new Oar(0, 0));

        entities.add(new Oar(0, 3));


        checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(364, 0, 0), new Circle(100)));
        sail.setOpenned(true);
        wind=new Wind(0,100);
        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),wind, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);

        assertEquals(2,preCalculator.numberOfSailorToOarToCheckPoint());
    }
    @Test
    void numberOfSailorToOarToCheckPointTest9() {
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 0, "sailor2"));
        sailors.add(new Sailor(4, 1, 0, "sailor2"));
        sailors.add(new Sailor(5, 1, 0, "sailor2"));

        entities.add(new Oar(0, 0));

        entities.add(new Oar(0, 3));


        checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(366, 0, 0), new Circle(100)));
        sail.setOpenned(true);
        wind=new Wind(0,100);
        seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),wind, null);
        preCalculator=new PreCalculator(ship,sailors,seaMap,wind);

        assertEquals(4,preCalculator.numberOfSailorToOarToCheckPoint());
    }
}
