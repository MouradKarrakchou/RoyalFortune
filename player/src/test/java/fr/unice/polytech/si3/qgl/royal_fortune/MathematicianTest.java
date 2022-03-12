package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathematicianTest {
    @BeforeEach
    void setUp() {

    }

    @Test
    void changeBaseTest() {
        Position positionInSeaBase = Mathematician.changeBase(new Position(0,0, Math.PI/4), 2, 1);
        double scale = Math.pow(10, 4);
        double x = Math.round(positionInSeaBase.getX()*scale)/scale;
        double y = Math.round(positionInSeaBase.getY()*scale)/scale;
        double ori = Math.round(positionInSeaBase.getOrientation()*scale)/scale;

        Position positionAfterBase = new Position(0.7071, 2.1213, 0);

        assertEquals(positionAfterBase.getX(), x);
        assertEquals(positionAfterBase.getY(), y);
        assertEquals(positionAfterBase.getOrientation(), ori);
    }
}
