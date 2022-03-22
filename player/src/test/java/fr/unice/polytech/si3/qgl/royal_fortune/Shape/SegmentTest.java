package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SegmentTest {
    private Segment s;
    private Position p1;
    private Position p2;

    @BeforeEach
    void init(){
        p1 = new Position(0,0,0);
        p2 = new Position(50,0,0);
        s = new Segment(p1, p2);
    }

    @Test
    void getPointATest(){
        assertEquals(p1, s.getPointA());
    }

    @Test
    void getPointBTest(){
        assertEquals(p2, s.getPointB());
    }

    @Test
    void getLenghtTest(){
        assertEquals(50., s.getLength());
    }

    @Test
    void getATest(){
        assertEquals(-0., s.getA());
    }

    @Test
    void getBTest(){
        assertEquals(-0., s.getB());
    }

    @Test
    void computeATest(){
        assertEquals(-0., s.computeA(p1, p2));
    }
    @Test
    void computeBTest(){
        assertEquals(-0., s.computeB(p1, p2));
    }

    @Test
    void computeIntersectionWith_IntersectionTest(){
        Segment other = new Segment(new Position(25,-25,-Math.PI/2),new Position(25,25,-Math.PI/2));
        Optional<Position> res = s.computeIntersectionWith(other);
        assertTrue(res.isPresent());
    }

    @Test
    void angleIntersectionBetweenSegmentAndRectangleTest(){
        Rectangle r = new Rectangle(10.,10.,0.);
        r.setPosition(new Position(20,0,0));
        double res = s.angleIntersectionBetweenSegmentAndRectangle(r);
        assertEquals(0., res);

        r = new Rectangle(10.,10.,Math.PI/2);
        r.setPosition(new Position(20,0,0));
        res = s.angleIntersectionBetweenSegmentAndRectangle(r);
        assertEquals(Math.PI/2, res);
    }

}
