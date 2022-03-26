package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryRectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometryRectangleTest {
    Rectangle rectangle;

    @BeforeEach
    void init() {
        rectangle=new Rectangle(50, 50, Math.PI/2);
    }

    @Test
    void positionIsInTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, Math.PI/2);

        boolean isInRectangle = GeometryRectangle.positionIsInTheRectangle(new Position(0,0, 0), rectPos, rectangle);
        assertTrue(isInRectangle);
    }

    @Test
    void positionIsOnSideOfTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, Math.PI/2);

        boolean isInRectangle = GeometryRectangle.positionIsInTheRectangle(new Position(1.5,0, 0), rectPos, rectangle);
        assertTrue(isInRectangle);
    }

    @Test
    void positionIsOnCornerOfTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, 0);

        boolean isInRectangle = GeometryRectangle.positionIsInTheRectangle(new Position(1.5,2.5, 0), rectPos, rectangle);
        assertTrue(isInRectangle);
    }

    @Test
    void positionIsNotInTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, 0);

        boolean isInRectangle = GeometryRectangle.positionIsInTheRectangle(new Position(1.6,0, 0), rectPos, rectangle);
        assertFalse(isInRectangle);
    }
    @Test
    void positionIsInTheRectangleTest1() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, Math.PI/2);

        boolean isInRectangle = GeometryRectangle.positionIsInTheRectangle(new Position(1.5,1.5, 0), rectPos, rectangle);
        assertTrue(isInRectangle);
    }
    @Test
    void positionIsInTheRectangleTest2() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, 0);

        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(1.5,2.5, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-1.5,-2.5, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-1.5,0, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0,-2.5, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0,2.5, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(1.5,0, 0), rectPos, rectangle));
    }
    @Test
    void positionIsInTheRectangleTest4() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Stream stream = new Stream(new Position(-1,-1,0), rectangle, 5);


        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0.5,1.5, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-2.5,-3.5, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-2.5,-1, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-1,-3.5, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-1,1.5, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0.5,-1, 0), stream.getPosition(), rectangle));
    }
    @Test
    void positionIsInTheRectangleTest3() {
        Rectangle rectangle = new Rectangle(5, 3, Math.PI/2);
        Position rectPos = new Position(0, 0, Math.PI/2);

        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(1.49999,2.4999, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-1.49999,-2.4999, 0),rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-1.49999,0, 0),rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0,-2.4999, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0,2.4999, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(1.49999,0, 0), rectPos, rectangle));
    }

    @Test
    void computeSegmentTest(){
        List<Segment> res = GeometryRectangle.computeSegments(new Position(0,0,0), rectangle);
        assertTrue(res.size() > 0);
    }

    @Test
    void generateBeaconTest(){
        Stream stream = new Stream(new Position(0,0,Math.PI/2), rectangle, 5);
        List<Beacon> res = GeometryRectangle.generateBeacon(stream.getPosition(), rectangle);
        assertTrue((Math.abs(res.get(0).getPosition().getX()-(-25)))<0.2);
        assertTrue((Math.abs(res.get(0).getPosition().getY()-(-35)))<0.2);
        assertTrue((Math.abs(res.get(0).getPosition().getOrientation()-0))<0.2);

        assertTrue((Math.abs(res.get(1).getPosition().getX()-(-35)))<0.2);
        assertTrue((Math.abs(res.get(1).getPosition().getY()-(25)))<0.2);
        assertTrue((Math.abs(res.get(1).getPosition().getOrientation()-0))<0.2);

        assertTrue((Math.abs(res.get(2).getPosition().getX()-(25)))<0.2);
        assertTrue((Math.abs(res.get(2).getPosition().getY()-(35)))<0.2);
        assertTrue((Math.abs(res.get(2).getPosition().getOrientation()-0))<0.2);

        assertTrue((Math.abs(res.get(3).getPosition().getX()-(35)))<0.2);
        assertTrue((Math.abs(res.get(3).getPosition().getY()-(-25)))<0.2);
        assertTrue((Math.abs(res.get(3).getPosition().getOrientation()-0))<0.2);
    }

    @Test
    void toStringTest(){
        assertNotEquals("", rectangle.toString());
    }

    @Test
    void getWidthHeightTest(){
        double w = 2.;
        double h = 3.;
        Rectangle r = new Rectangle(w, h, 0.);
        assertEquals(2., r .getWidth());
        assertEquals(3., r .getHeight());
    }


    @Test
    void computeIntersectionWithTest(){
        Position rectanglePos = new Position(5, 0, 0);
        Position pointA = new Position(0,0,0);
        Position pointB = new Position(10,0,0);

        Segment s = new Segment(pointA, pointB);
        List<Position> res = GeometryRectangle.computeIntersectionWith(s, rectanglePos,rectangle);
        assertEquals(2, res.size());
    }


}
