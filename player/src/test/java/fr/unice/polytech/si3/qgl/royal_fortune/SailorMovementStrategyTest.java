package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.SailorMovementStrategy;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SailorMovementStrategyTest {

    @Test
    void getSailorsNearToOarLeftTest(){
        List<Sailor> sailors = new ArrayList<>();
        // 5 range from Oar1 (close enough)
        Sailor goodSailor00 = new Sailor(0, 2, 3, "sailor0");
        sailors.add(goodSailor00);

        // 5 range from Oar1 (close enough)
        Sailor goodSailor01 = new Sailor(1, 4, 1, "sailor1");
        sailors.add(goodSailor01);

        List<Sailor> correctSailors = new ArrayList<>(sailors);

        Sailor wrongSailor = new Sailor(2, 3, 3, "sailor2"); // 6 range from Oar1 (too far)
        sailors.add(wrongSailor);

        List<Entities> entities = new ArrayList<>();
        Oar leftOar = new Oar(0, 0);
        entities.add(leftOar);

        // The rudder should not be assigned
        Rudder rudder = new Rudder(0, 0);
        entities.add(rudder);

        // The sail should not be assigned
        Sail sail = new Sail(0, 0, false);
        entities.add(sail);

        Oar rightOar = new Oar(5, 5);
        entities.add(rightOar);

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(10, 10),
                entities,
                new Rectangle("rectangle", 10, 10, 0));

        Associations associations = new Associations();

        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations, null);

        assertTrue(sailorMovementStrategy.getSailorNearToOar(DirectionsManager.LEFT).containsAll(correctSailors));
        assertFalse(sailorMovementStrategy.getSailorNearToOar(DirectionsManager.LEFT).contains(wrongSailor));

        associations.addAssociation(goodSailor00, leftOar);
        assertTrue(sailorMovementStrategy.getSailorNearToOar(DirectionsManager.LEFT).isEmpty());

        entities.add(new Oar(0, 0));
        assertTrue(sailorMovementStrategy.getSailorNearToOar(DirectionsManager.LEFT).contains(goodSailor01));
    }

    @Test
    void getSailorsNearToOarRightTest(){
        List<Sailor> sailors = new ArrayList<>();

        // 5 range from Oar1 (close enough)
        Sailor goodSailor00 = new Sailor(0, 3, 3, "sailor0");
        sailors.add(goodSailor00);

        // 5 range from Oar1 (close enough)
        Sailor goodSailor01 = new Sailor(1, 10, 4, "sailor1");
        sailors.add(goodSailor01);

        List<Sailor> correctSailors = new ArrayList<>(sailors);

        Sailor wrongSailor = new Sailor(2, 2, 3, "sailor2"); // 6 range from Oar2 (too far)
        sailors.add(wrongSailor);

        List<Entities> entities = new ArrayList<>();
        Oar leftOar = new Oar(0, 0);
        entities.add(leftOar);

        Oar rightOar = new Oar(7, 2);
        entities.add(rightOar);

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(10, 10),
                entities,
                new Rectangle("rectangle", 10, 10, 0));

        Associations associations = new Associations();

        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations, null);

        assertTrue(sailorMovementStrategy.getSailorNearToOar(DirectionsManager.RIGHT).containsAll(correctSailors));
        assertFalse(sailorMovementStrategy.getSailorNearToOar(DirectionsManager.RIGHT).contains(wrongSailor));

        associations.addAssociation(goodSailor00, rightOar);
        assertTrue(sailorMovementStrategy.getSailorNearToOar(DirectionsManager.RIGHT).isEmpty());

        entities.add(new Oar(7, 2));
        assertTrue(sailorMovementStrategy.getSailorNearToOar(DirectionsManager.RIGHT).contains(goodSailor01));
    }

    @Test
    void associateTheOnlyOnePossibleTest(){
        List<Sailor> sailors = new ArrayList<>();
        // 5 range from Oar1 (close enough)
        Sailor leftSailor00 = new Sailor(0, 2, 3, "sailor0");
        sailors.add(leftSailor00);

        // 5 range from Oar1 (close enough)
        Sailor leftSailor01 = new Sailor(1, 4, 1, "sailor1");
        sailors.add(leftSailor01);

        Sailor rightSailor = new Sailor(2, 8, 2, "sailor2");
        sailors.add(rightSailor);

        List<Entities> entities = new ArrayList<>();
        Oar leftOar = new Oar(0, 0);
        entities.add(leftOar);

        Oar rightOar = new Oar(10, 3);
        entities.add(rightOar);

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(10, 10),
                entities,
                new Rectangle("rectangle", 10, 10, 0));

        Associations associations = new Associations();

        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations, null);

        sailorMovementStrategy.associateTheOnlyOnePossible(leftOar);
        assertNull(associations.getAssociatedEntity(leftSailor00));
        assertNull(associations.getAssociatedEntity(leftSailor01));
        assertNull(associations.getAssociatedEntity(rightSailor));

        sailorMovementStrategy.associateTheOnlyOnePossible(rightOar);
        assertEquals(rightOar, associations.getAssociatedEntity(rightSailor));
        assertEquals(rightSailor, associations.getAssociatedSailor(rightOar));
    }
}
