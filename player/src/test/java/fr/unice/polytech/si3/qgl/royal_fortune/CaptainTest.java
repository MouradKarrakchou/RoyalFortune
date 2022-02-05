package fr.unice.polytech.si3.qgl.royal_fortune;


import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Shape;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CaptainTest {
    private Ship ship;
    private ArrayList<Sailor> sailors;

    @Test
    public void associateSailorToOarTest(){
        ArrayList<Entities> entities = new ArrayList<>();
        entities.add(new Oar("oar", 0, 0));
        entities.add(new Oar("oar", 1, 0));
        entities.add(new Oar("oar", 2, 0));

        entities.add(new Oar("oar", 0, 3));
        entities.add(new Oar("oar", 1, 3));
        entities.add(new Oar("oar", 2, 3));

        ship = new  Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4);

    }

}
