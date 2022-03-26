package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {

    private Shape s;

    @BeforeEach
    void init(){
        s = new Shape("test");
    }

    @Test
    void toStringTest(){
        assertNotEquals("", s.toString());
    }

    @Test
    void getTypeTest(){
        assertEquals("test", s.getType());
    }

    @Test
    void isRectangleTest(){
        s = new Rectangle(5.,2.,0.);
        assertTrue(s.isRectangle().isPresent());
    }

    @Test
    void isNotRectangleTest(){
        s = new Circle(5.);
        assertFalse(s.isRectangle().isPresent());
    }

    @Test
    void isCircleTest(){
        s = new Circle(5.);
        assertTrue(s.isCircle().isPresent());
    }

    @Test
    void isNotCircleTest(){
        s = new Rectangle(5.,2.,0.);
        assertFalse(s.isCircle().isPresent());
    }


    @Test
    void computeSegmentsIfPossibleTest(){
        Rectangle rectangle = new Rectangle(5,5,0);
        rectangle.computeSegmentsIfPossible(new Position(0, 0, 0));
        assertEquals(4, rectangle.getSegmentList().size());
    }
}
