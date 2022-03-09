package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.SailorMovementStrategy;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SailorMovementStrategyTest {

    @Test
    void getSailorsNearToOarLeftTest(){
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 2, 3, "sailor0")); // 5 range from Oar1 (close enough)
        sailors.add(new Sailor(1, 4, 1, "sailor1")); // 5 range from Oar1 (close enough)
        List<Sailor> correctSailors = new ArrayList<>(sailors);

        Sailor wrongSailor = new Sailor(2, 3, 3, "sailor2"); // 6 range from Oar1 (too far)
        sailors.add(wrongSailor);

        List<Entities> entities = new ArrayList<>();
        entities.add(new Oar(0, 0)); // Is a left oar.
        entities.add(new Oar(5, 5)); // Is a right oar.

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
    }

    @Test
    void getSailorsNearToOarRightTest(){
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(1, 3, 3, "sailor1")); // 5 range from Oar2 (close enough)
        sailors.add(new Sailor(2, 10, 4, "sailor2")); // 5 range from Oar2 (close enough)
        List<Sailor> correctSailors = new ArrayList<>(sailors);

        Sailor wrongSailor = new Sailor(2, 2, 3, "sailor2"); // 6 range from Oar2 (too far)
        sailors.add(wrongSailor);

        List<Entities> entities = new ArrayList<>();
        entities.add(new Oar(0, 0)); // Is a left oar.
        entities.add(new Oar(7, 2)); // Is a right oar.

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
    }
}
