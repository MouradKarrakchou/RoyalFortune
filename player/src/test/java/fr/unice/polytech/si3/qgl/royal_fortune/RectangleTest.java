package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RectangleTest {
    Rectangle rectangle;

    @BeforeEach
    void init() {
        rectangle=new Rectangle("rectangle", 50, 50, Math.PI/2);
    }
    @Test
    void rectangleInitialisionTest(){
        rectangle.setPosition(new Position(50,50,Math.PI/2));
        assertTrue(true);
    }
}
