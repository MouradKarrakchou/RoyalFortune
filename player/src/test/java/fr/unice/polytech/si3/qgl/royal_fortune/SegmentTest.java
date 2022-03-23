package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SegmentTest {
    @BeforeEach
    void setUp() {

    }

    @Test
    void angleIntersectionBetweenSegmentAndRectangleTest() {
        Segment segment = new Segment(new Position(0, 0, 0), new Position(10, 0, 0));
        Rectangle rectangle = new Rectangle(3, 5, Math.PI/4);
        double angle = segment.angleIntersectionBetweenSegmentAndRectangle(rectangle);
        assertEquals(Math.PI/4, angle);

        rectangle = new Rectangle(3, 5, 0);
        angle = segment.angleIntersectionBetweenSegmentAndRectangle(rectangle);
        assertEquals(0, angle);

        segment = new Segment(new Position(0, 0, 0), new Position(0.5, Math.sqrt(3)/2, 0));
        rectangle = new Rectangle(3, 5, 0);
        angle = segment.angleIntersectionBetweenSegmentAndRectangle(rectangle);
        assertEquals(Math.PI/3, angle);

        rectangle = new Rectangle(3, 5, Math.PI);
        angle = segment.angleIntersectionBetweenSegmentAndRectangle(rectangle);
        assertEquals(2*Math.PI/3, angle);
    }
    @Test
    void computeIntersectionWithTest(){
        Segment segment1=new Segment(new Position(0,10),new Position(0,100));
        Segment segment2=new Segment(new Position(0,10),new Position(0,100));
        assertTrue(segment1.computeIntersectionWith(segment2).isPresent());
    }
    @Test
    void computeIntersectionWithTest2(){
        Segment segment1=new Segment(new Position(0,10),new Position(0,100));
        Segment segment2=new Segment(new Position(0,50),new Position(0,400));
        assertTrue(segment1.computeIntersectionWith(segment2).isPresent());
    }
    @Test
    void computeIntersectionWithTest3(){
        Segment segment1=new Segment(new Position(10,0),new Position(20,0));
        Segment segment2=new Segment(new Position(5,0),new Position(11,0));
        assertTrue(segment1.computeIntersectionWith(segment2).isPresent());
    }
    @Test
    void computeIntersectionWithTest4(){
        Segment segment1=new Segment(new Position(10,0),new Position(10.5,0));
        Segment segment2=new Segment(new Position(5,0),new Position(11,0));
        assertTrue(segment1.computeIntersectionWith(segment2).isPresent());
    }
    @Test
    void computeIntersectionWithTest5(){
        Segment segment1=new Segment(new Position(10,0),new Position(10.5,0));
        Segment segment2=new Segment(new Position(10.5,0),new Position(11,0));
        assertTrue(segment1.computeIntersectionWith(segment2).isPresent());
    }
    @Test
    void computeIntersectionWithTest6(){
        Segment segment1=new Segment(new Position(0,1),new Position(1,10));
        Segment segment2=new Segment(new Position(0,5),new Position(10,5));
        assertTrue(segment1.computeIntersectionWith(segment2).isPresent());
    }
    @Test
    void computeIntersectionWithTest7(){
        Segment segment1=new Segment(new Position(0,4),new Position(1,10));
        Segment segment2=new Segment(new Position(0,5),new Position(10,5));
        assertTrue(segment1.computeIntersectionWith(segment2).isPresent());
    }




}
