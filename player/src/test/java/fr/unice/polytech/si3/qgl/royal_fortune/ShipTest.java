package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ShipTest {
    private Ship ship;
    private ArrayList<Entities> entities;

    @BeforeEach
    public void init(){
        entities = new ArrayList<>();
    }

    @Test
    public void getOarListTest(){
        entities.add(new Oar("oar", 0, 0));
        entities.add(new Oar("oar", 1, 0));
        entities.add(new Oar("oar", 2, 0));

        entities.add(new Oar("oar", 0, 3));
        entities.add(new Oar("oar", 1, 3));

        ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        assertEquals(5, entities.size());
        assertEquals(3, ship.getOarList("left").size());
        assertEquals(2, ship.getOarList("right").size());
    }
}
