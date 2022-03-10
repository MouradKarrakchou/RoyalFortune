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

    @Test
    void associateNearestSailorTest(){
        List<Sailor> sailors = new ArrayList<>();
        // 4 range from Oar1 (close enough)
        Sailor leftSailor00 = new Sailor(0, 2, 2, "sailor0");
        sailors.add(leftSailor00);

        // 5 range from Oar1 (close enough)
        Sailor leftSailor01 = new Sailor(1, 4, 1, "sailor1");
        sailors.add(leftSailor01);

        List<Entities> entities = new ArrayList<>();
        Oar leftOar = new Oar(0, 0);
        entities.add(leftOar);

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

        assertTrue(sailorMovementStrategy.associateNearestSailor(leftOar));
        assertEquals(leftSailor00, associations.getAssociatedSailor(leftOar));
        assertEquals(leftOar, associations.getAssociatedEntity(leftSailor00));

        leftSailor01.setX(1);
        leftSailor01.setY(1);
        assertFalse(sailorMovementStrategy.associateNearestSailor(leftOar));
        assertEquals(leftSailor00, associations.getAssociatedSailor(leftOar));
        assertEquals(leftOar, associations.getAssociatedEntity(leftSailor00));

        associations.dissociateAll();
        assertTrue(sailorMovementStrategy.associateNearestSailor(leftOar));
        assertEquals(leftSailor01, associations.getAssociatedSailor(leftOar));
        assertEquals(leftOar, associations.getAssociatedEntity(leftSailor01));
    }

    @Test
    void associateNearestSailorTooFarTest() {
        List<Sailor> sailors = new ArrayList<>();
        // 4 range from Oar1 (close enough)
        Sailor leftSailor00 = new Sailor(0, 3, 3, "sailor0");
        sailors.add(leftSailor00);

        // 5 range from Oar1 (close enough)
        Sailor leftSailor01 = new Sailor(1, 5, 1, "sailor1");
        sailors.add(leftSailor01);

        List<Entities> entities = new ArrayList<>();
        Oar leftOar = new Oar(0, 0);
        entities.add(leftOar);

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

        assertFalse(sailorMovementStrategy.associateNearestSailor(leftOar));
        assertNull(associations.getAssociatedSailor(leftOar));
    }

    @Test
    void onlyOnePossibleAssociation01Test(){
        List<Sailor> sailors = new ArrayList<>();

        // Sailor is close enough to all entities.
        Sailor sailor = new Sailor(0, 3, 3, "sailor0");
        sailors.add(sailor);

        List<Entities> entities = new ArrayList<>();
        Oar leftOar = new Oar(1, 0);
        entities.add(leftOar);

        Oar rightOar = new Oar(0, 3);
        entities.add(rightOar);

        Rudder rudder = new Rudder(2, 2);
        entities.add(rudder);

        Sail sail = new Sail(1, 1, false);
        entities.add(sail);

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(5, 5),
                entities,
                new Rectangle("rectangle", 10, 10, 0));

        Associations associations = new Associations();
        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations, null);

        sailorMovementStrategy.onlyOnePossibleAssociation(DirectionsManager.LEFT, true, true);
        assertEquals(rudder, associations.getAssociatedEntity(sailor));

        associations.dissociateAll();
        sailorMovementStrategy.onlyOnePossibleAssociation(DirectionsManager.LEFT, false, true);
        assertEquals(sail, associations.getAssociatedEntity(sailor));

        associations.dissociateAll();
        sailorMovementStrategy.onlyOnePossibleAssociation(DirectionsManager.LEFT, false, false);
        assertEquals(leftOar, associations.getAssociatedEntity(sailor));
    }

    @Test
    void onlyOnePossibleAssociation02Test(){
        List<Sailor> sailors = new ArrayList<>();

        // Sailor00 is close enough to all entities.
        Sailor sailor00 = new Sailor(0, 3, 3, "sailor0");
        sailors.add(sailor00);

        // Sailor01 is close enough to rudder entities.
        Sailor sailor01 = new Sailor(1, 4, 5, "sailor1");
        sailors.add(sailor01);

        List<Entities> entities = new ArrayList<>();
        Oar leftOar = new Oar(1, 0);
        entities.add(leftOar);

        Oar rightOar = new Oar(0, 3);
        entities.add(rightOar);

        Rudder rudder = new Rudder(2, 2);
        entities.add(rudder);

        Sail sail = new Sail(1, 1, false);
        entities.add(sail);

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(5, 5),
                entities,
                new Rectangle("rectangle", 10, 10, 0));

        Associations associations = new Associations();
        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations, null);

        sailorMovementStrategy.onlyOnePossibleAssociation(DirectionsManager.LEFT, true, true);
        assertNull(associations.getAssociatedSailor(rudder));
        assertNull(associations.getAssociatedEntity(sailor01));
        assertEquals(sail, associations.getAssociatedEntity(sailor00));

        sailorMovementStrategy.onlyOnePossibleAssociation(DirectionsManager.LEFT, true, true);
        assertEquals(sail, associations.getAssociatedEntity(sailor00));
        assertEquals(rudder, associations.getAssociatedEntity(sailor01));

        associations.dissociateAll();
        sailorMovementStrategy.onlyOnePossibleAssociation(DirectionsManager.RIGHT, false, true);
        assertEquals(sail, associations.getAssociatedEntity(sailor00));
    }

    @Test
    void associateNearestSailorToOarTest(){
        List<Sailor> sailors = new ArrayList<>();

        // Sailor00 is near to leftOar00 and rightOar00
        Sailor sailor00 = new Sailor(0, 0, 2, "sailor0");
        sailors.add(sailor00);

        // Sailor01 is near to leftOar00 and rightOar00
        Sailor sailor01 = new Sailor(1, 1, 2, "sailor1");
        sailors.add(sailor01);

        List<Entities> entities = new ArrayList<>();
        Oar leftOar00 = new Oar(0, 0);
        entities.add(leftOar00);

        Oar leftOar01 = new Oar(1, 0);
        entities.add(leftOar01);

        Oar rightOar00 = new Oar(0, 3);
        entities.add(rightOar00);

        Oar rightOar01 = new Oar(1, 3);
        entities.add(rightOar01);

        Rudder rudder = new Rudder(3, 2);
        entities.add(rudder);

        Sail sail = new Sail(1, 2, false);
        entities.add(sail);

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(5, 5),
                entities,
                new Rectangle("rectangle", 10, 10, 0));

        Associations associations = new Associations();
        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations, null);

        assertEquals(2, sailorMovementStrategy.associateNearestSailorToOar(DirectionsManager.LEFT, 2));
        assertEquals(leftOar00, associations.getAssociatedEntity(sailor00));
        assertEquals(leftOar01, associations.getAssociatedEntity(sailor01));
        assertNull(associations.getAssociatedSailor(rightOar00));
        assertNull(associations.getAssociatedSailor(rightOar01));

        associations.dissociateAll();
        assertEquals(2, sailorMovementStrategy.associateNearestSailorToOar(DirectionsManager.RIGHT, 2));
        assertEquals(rightOar00, associations.getAssociatedEntity(sailor00));
        assertEquals(rightOar01, associations.getAssociatedEntity(sailor01));
        assertNull(associations.getAssociatedSailor(leftOar00));
        assertNull(associations.getAssociatedSailor(leftOar01));

        associations.dissociateAll();
        assertEquals(1, sailorMovementStrategy.associateNearestSailorToOar(DirectionsManager.RIGHT, 1));
        assertEquals(rightOar00, associations.getAssociatedEntity(sailor00));
        assertEquals(1, sailorMovementStrategy.associateNearestSailorToOar(DirectionsManager.LEFT, 1));
        assertEquals(leftOar01, associations.getAssociatedEntity(sailor01));
    }

}
