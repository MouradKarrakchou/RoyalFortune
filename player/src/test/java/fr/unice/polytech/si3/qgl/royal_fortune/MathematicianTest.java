package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MathematicianTest {
    @BeforeEach
    void setUp() {

    }

    @Test
    void changeBaseTest() {
        Position ship = new Position(10, 20, 0);
        Position objectInShip = new Position(0, 0, 0);
        Mathematician.changeBase(objectInShip, 2, 3);

        assertEquals(ship.getX(), )
    }
}
