package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.SailorMovementStrategy;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.SailorPlacement;
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

        assertFalse(sailorMovementStrategy.associateStarvingEntity(leftOar));
        assertNull(associations.getAssociatedEntity(leftSailor00));
        assertNull(associations.getAssociatedEntity(leftSailor01));
        assertNull(associations.getAssociatedEntity(rightSailor));

        assertTrue(sailorMovementStrategy.associateStarvingEntity(rightOar));
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
    void associateStarvingEntitiesTest1(){
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

        SailorPlacement requestedPlacement = new SailorPlacement(DirectionsManager.LEFT, true, true);
        sailorMovementStrategy.associateStarvingEntities(requestedPlacement);
        assertEquals(rudder, associations.getAssociatedEntity(sailor));

        associations.dissociateAll();
        requestedPlacement = new SailorPlacement(DirectionsManager.LEFT, false, true);
        sailorMovementStrategy.associateStarvingEntities(requestedPlacement);
        assertEquals(sail, associations.getAssociatedEntity(sailor));

        associations.dissociateAll();
        requestedPlacement = new SailorPlacement(DirectionsManager.LEFT, false, false);
        sailorMovementStrategy.associateStarvingEntities(requestedPlacement);
        assertEquals(leftOar, associations.getAssociatedEntity(sailor));
    }

    @Test
    void associateStarvingEntitiesTest2(){
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

        SailorPlacement requestedPlacement = new SailorPlacement(DirectionsManager.LEFT, true, true);
        sailorMovementStrategy.associateStarvingEntities(requestedPlacement);
        assertNull(associations.getAssociatedSailor(rudder));
        assertNull(associations.getAssociatedEntity(sailor01));
        assertEquals(sail, associations.getAssociatedEntity(sailor00));

        requestedPlacement = new SailorPlacement(DirectionsManager.LEFT, true, true);
        sailorMovementStrategy.associateStarvingEntities(requestedPlacement);
        assertEquals(sail, associations.getAssociatedEntity(sailor00));
        assertEquals(rudder, associations.getAssociatedEntity(sailor01));

        associations.dissociateAll();
        requestedPlacement = new SailorPlacement(DirectionsManager.RIGHT, false, true);
        sailorMovementStrategy.associateStarvingEntities(requestedPlacement);
        assertEquals(sail, associations.getAssociatedEntity(sailor00));
    }

    @Test
    void continueAssociatingStarvingEntitiesTest(){
        List<Sailor> sailors = new ArrayList<>();

        // Sailor00 is close enough to all entities.
        Sailor sailor00 = new Sailor(0, 3, 3, "sailor0");
        sailors.add(sailor00);

        // Sailor01 is close enough to rudder entity.
        Sailor sailor01 = new Sailor(1, 4, 5, "sailor1");
        sailors.add(sailor01);

        List<Entities> entities = new ArrayList<>();
        Oar leftOar = new Oar(1, 0);
        entities.add(leftOar);

        Oar rightOar = new Oar(0, 3);
        entities.add(rightOar);

        Rudder rudder = new Rudder(2, 2);
        entities.add(rudder);

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

        SailorPlacement requestedPlacement = new SailorPlacement(0, true, false);
        sailorMovementStrategy.continueAssociatingStarvingEntities(requestedPlacement);
        assertNull(associations.getAssociatedSailor(rudder));
        assertNull(associations.getAssociatedSailor(rightOar));
        assertNull(associations.getAssociatedSailor(leftOar));

        requestedPlacement = new SailorPlacement(DirectionsManager.RIGHT, true, false);
        sailorMovementStrategy.continueAssociatingStarvingEntities(requestedPlacement);
        assertEquals(sailor00, associations.getAssociatedSailor(rightOar));
        assertEquals(sailor01, associations.getAssociatedSailor(rudder));
        assertNull(associations.getAssociatedSailor(leftOar));
    }

    @Test
    void associateNearestSailorToOarsTest(){
        List<Sailor> sailors = new ArrayList<>();

        // Sailor00
        Sailor sailor00 = new Sailor(0, 3, 3, "sailor0");
        sailors.add(sailor00);

        // Sailor01
        Sailor sailor01 = new Sailor(1, 4, 3, "sailor1");
        sailors.add(sailor01);

        // Sailor02
        Sailor sailor02 = new Sailor(1, 5, 3, "sailor1");
        sailors.add(sailor02);

        List<Entities> entities = new ArrayList<>();
        // LeftOar00
        Oar leftOar00 = new Oar(1, 0);
        entities.add(leftOar00);

        // LeftOar01
        Oar leftOar01 = new Oar(2, 0);
        entities.add(leftOar01);

        // LeftOar02
        Oar leftOar02 = new Oar(3, 0);
        entities.add(leftOar02);

        // RightOar00
        Oar rightOar00 = new Oar(0, 5);
        entities.add(rightOar00);

        // RightOar01
        Oar rightOar01 = new Oar(1, 5);
        entities.add(rightOar01);

        // RightOar02
        Oar rightOar02 = new Oar(2, 5);
        entities.add(rightOar02);

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

        SailorPlacement requestedPlacement = new SailorPlacement(DirectionsManager.LEFT * 2, false, false);
        sailorMovementStrategy.associateNearestSailorToOars(requestedPlacement);
        assertEquals(0, requestedPlacement.getOarWeight());
        assertNull(associations.getAssociatedEntity(sailor02));
        assertEquals(leftOar00, associations.getAssociatedEntity(sailor00));
        assertEquals(leftOar01, associations.getAssociatedEntity(sailor01));

        associations.dissociateAll();

        requestedPlacement = new SailorPlacement(DirectionsManager.RIGHT * 4, false, false);
        sailorMovementStrategy.associateNearestSailorToOars(requestedPlacement);
        assertEquals(1, requestedPlacement.getOarWeight());
        assertEquals(rightOar00, associations.getAssociatedEntity(sailor00));
        assertEquals(rightOar01, associations.getAssociatedEntity(sailor01));
        assertEquals(rightOar02, associations.getAssociatedEntity(sailor02));
    }
}
