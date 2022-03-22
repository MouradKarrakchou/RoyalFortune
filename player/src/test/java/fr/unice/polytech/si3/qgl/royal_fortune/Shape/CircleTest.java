package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CircleTest {
    @Test
    void rectangleIsInCircle(){
        double radius = 500.;
        double h = 100;
        double w = 100;
        double o = 0;
        Position circlePos = new Position(0,0,0);
        Position rectPos = new Position(0,0,0);

        Rectangle r = new Rectangle(w, h , o);
        Circle c = new Circle(circlePos, radius);

        r.setPosition(rectPos);
        assertTrue(c.rectangleIsInCircle(r));
    }

    @Test
    void rectangleIsNotInCircle(){
        double radius = 50.;
        double h = 100;
        double w = 100;
        double o = 0;
        Position circlePos = new Position(1000,0,0);
        Position rectPos = new Position(0,0,0);

        Rectangle r = new Rectangle(w, h , o);
        Circle c = new Circle(circlePos, radius);

        r.setPosition(rectPos);
        assertFalse(c.rectangleIsInCircle(r));
    }

    @Test
    void toStringTest(){
        double radius = 50.;
        Circle c = new Circle(radius);

        assertTrue(c.toString() != "");
    }
}
