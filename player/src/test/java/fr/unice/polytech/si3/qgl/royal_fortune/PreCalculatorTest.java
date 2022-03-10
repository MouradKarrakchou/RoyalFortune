package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.PreCalculator;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.SeaMap;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Shape;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreCalculatorTest {
/*
    @Test
    void needSailorToOarToCheckpointTest() {
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

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        List<Checkpoint> checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(100, 0, 0), new Circle("Circle", 10)));

        SeaMap seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition());

        PreCalculator preCalculator = new PreCalculator(ship, sailors, seaMap);
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

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        List<Checkpoint> checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(100, 0, 0), new Circle("Circle", 100)));

        SeaMap seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition());

        PreCalculator preCalculator = new PreCalculator(ship, sailors, seaMap);
        assertFalse(preCalculator.needSailorToOarToCheckpoint(ship.getNbrOar()));
    }*/
}
