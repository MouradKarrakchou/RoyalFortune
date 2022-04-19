package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryCircle;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryRectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
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

        boolean isInRectangle = GeometryRectangle.positionIsInTheRectangle(new Position(0,1.5, 0), rectPos, rectangle);
        assertTrue(isInRectangle);
    }

    @Test
    void positionIsOnCornerOfTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, 0);

        boolean isInRectangle = GeometryRectangle.positionIsInTheRectangle(new Position(2.5,1.5, 0), rectPos, rectangle);
        assertTrue(isInRectangle);
    }

    @Test
    void positionIsNotInTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, 0);

        boolean isInRectangle = GeometryRectangle.positionIsInTheRectangle(new Position(0,1.6, 0), rectPos, rectangle);
        assertFalse(isInRectangle);
    }
    @Test
    void positionIsInTheRectangleTest1() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, Math.PI/2);

        boolean isInRectangle = GeometryRectangle.positionIsInTheRectangle(new Position(1.4,0, 0), rectPos, rectangle);
        assertTrue(isInRectangle);
    }
    @Test
    void positionIsInTheRectangleTest2() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Position rectPos = new Position(0, 0, 0);

        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(2.5,1.5, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-2.5,-1.5, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0,-1.5, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-2.5,0, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(2.5,0, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0,1.5, 0), rectPos, rectangle));
    }
    @Test
    void positionIsInTheRectangleTest4() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        Stream stream = new Stream(new Position(-1,-1,0), rectangle, 5);

        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(1.5,0.5, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-3.5,-2.5, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-1,-2.5, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-3.5,-1.5, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(1.5,-1, 0), stream.getPosition(), rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-1,0.5, 0), stream.getPosition(), rectangle));
    }
    @Test
    void positionIsInTheRectangleTest3() {
        Rectangle rectangle = new Rectangle(5, 3, Math.PI/2);
        Position rectPos = new Position(0, 0, Math.PI/2);

        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(2.4999,1.49999, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-2.4999,-1.49999, 0),rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0,-1.49999, 0),rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(-2.4999,0, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(2.4999,0, 0), rectPos, rectangle));
        assertTrue(GeometryRectangle.positionIsInTheRectangle(new Position(0,1.49999, 0), rectPos, rectangle));
    }

    @Test
    void computeSegmentTest(){
        List<Segment> res = GeometryRectangle.computeSegments(new Position(0,0,0), rectangle);
        assertTrue(res.size() > 0);
    }

    @Test
    void generateBeaconStreamTest(){
        Rectangle rectangle = new Rectangle(70, 50, 0);
        Stream stream = new Stream(new Position(0,0,0), rectangle, 5);
        List<Beacon> res = GeometryRectangle.generateBeacon(stream.getPosition(), rectangle,false);

        assertTrue((Math.abs(res.get(0).getPosition().getX() + 25))<0.2);
        assertTrue((Math.abs(res.get(0).getPosition().getY() + 35))<0.2);
        assertTrue((Math.abs(res.get(0).getPosition().getOrientation()-0))<0.2);

        assertTrue((Math.abs(res.get(1).getPosition().getX() - 25)) <0.2);
        assertTrue((Math.abs(res.get(1).getPosition().getY() - 35))<0.2);
        assertTrue((Math.abs(res.get(1).getPosition().getOrientation()-0))<0.2);

        assertTrue((Math.abs(res.get(2).getPosition().getX() + 25)) < 0.2);
        assertTrue((Math.abs(res.get(2).getPosition().getY() - 35)) < 0.2);
        assertTrue((Math.abs(res.get(2).getPosition().getOrientation()-0))<0.2);

        assertTrue((Math.abs(res.get(3).getPosition().getX() - 25)) < 0.2);
        assertTrue((Math.abs(res.get(3).getPosition().getY() + 35)) < 0.2);
        assertTrue((Math.abs(res.get(3).getPosition().getOrientation()-0))<0.2);
    }

    @Test
    void generateBeaconReefTest(){
        Rectangle rectangle = new Rectangle(70, 50, 0);
        Reef reef = new Reef(new Position(0,0,0), rectangle);
        List<Beacon> res = GeometryRectangle.generateBeacon(reef.getPosition(), rectangle,true);

        System.out.println(Math.abs(res.get(0).getPosition().getX()));
        System.out.println(Math.abs(res.get(0).getPosition().getY()));
        assertTrue((Math.abs(res.get(0).getPosition().getX() + 100))<0.2);
        assertTrue((Math.abs(res.get(0).getPosition().getY() + 110))<0.2);
        assertTrue((Math.abs(res.get(0).getPosition().getOrientation()-0))<0.2);

        assertTrue((Math.abs(res.get(1).getPosition().getX() - 100)) <0.2);
        assertTrue((Math.abs(res.get(1).getPosition().getY() - 110))<0.2);
        assertTrue((Math.abs(res.get(1).getPosition().getOrientation()-0))<0.2);

        assertTrue((Math.abs(res.get(2).getPosition().getX() + 100)) < 0.2);
        assertTrue((Math.abs(res.get(2).getPosition().getY() - 110)) < 0.2);
        assertTrue((Math.abs(res.get(2).getPosition().getOrientation()-0))<0.2);

        assertTrue((Math.abs(res.get(3).getPosition().getX() - 100)) < 0.2);
        assertTrue((Math.abs(res.get(3).getPosition().getY() + 110)) < 0.2);
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

    @Test
    void positionIsInTheCircle() {
        Position pointA = new Position(4.3, 5.6);
        Position position = new Position(7.84, 1.23);
        Circle shape = new Circle(2.47);

        assertFalse(GeometryCircle.positionIsInTheCircle(pointA, position, shape));

        shape = new Circle(10.47);
        assertTrue(GeometryCircle.positionIsInTheCircle(pointA, position, shape));
    }

}
