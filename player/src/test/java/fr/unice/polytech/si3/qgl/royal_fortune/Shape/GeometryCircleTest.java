package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryCircle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeometryCircleTest {
    @Test
    void rectangleIsInCircle(){
        double radius = 500.;
        double h = 100;
        double w = 100;
        double o = 0;
        Position seaEntitiePos = new Position(0,0,0);

        Rectangle r = new Rectangle(w, h , o);
        Circle c = new Circle(radius);

        assertTrue(GeometryCircle.rectangleIsInCircle(r, seaEntitiePos, radius));
    }

    @Test
    void rectangleIsNotInCircle(){
        double radius = 50.;
        double h = 100;
        double w = 100;
        double o = 0;
        Position circlePos = new Position(1000,0,0);

        Rectangle r = new Rectangle(w, h , o);
        Circle c = new Circle(radius);

        assertFalse(GeometryCircle.rectangleIsInCircle(r, circlePos, radius));
    }

    @Test
    void toStringTest(){
        double radius = 50.;
        Circle c = new Circle(radius);

        assertNotEquals("", c.toString());
    }
}
