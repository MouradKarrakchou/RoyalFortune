package fr.unice.polytech.si3.qgl.royal_fortune.Shape;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {
    Rectangle rectangle;

    @BeforeEach
    void init() {
        rectangle=new Rectangle(50, 50, Math.PI/2);
    }
    @Test
    void rectangleInitialisionTest(){
        rectangle.setPosition(new Position(50,50,Math.PI/2));
    }

    @Test
    void positionIsInTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        rectangle.setPosition(new Position(0, 0, 0));

        boolean isInRectangle = rectangle.positionIsInTheRectangle(new Position(0,0, 0));
        assertTrue(isInRectangle);
    }

    @Test
    void positionIsOnSideOfTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        rectangle.setPosition(new Position(0, 0, 0));

        boolean isInRectangle = rectangle.positionIsInTheRectangle(new Position(1.5,0, 0));
        assertTrue(isInRectangle);
    }

    @Test
    void positionIsOnCornerOfTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        rectangle.setPosition(new Position(0, 0, 0));

        boolean isInRectangle = rectangle.positionIsInTheRectangle(new Position(1.5,2.5, 0));
        assertTrue(isInRectangle);
    }

    @Test
    void positionIsNotInTheRectangleTest() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        rectangle.setPosition(new Position(0, 0, 0));

        boolean isInRectangle = rectangle.positionIsInTheRectangle(new Position(1.6,0, 0));
        assertFalse(isInRectangle);
    }
    @Test
    void positionIsInTheRectangleTest1() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        rectangle.setPosition(new Position(0, 0, 0));

        boolean isInRectangle = rectangle.positionIsInTheRectangle(new Position(1.5,1.5, 0));
        assertTrue(isInRectangle);
    }
    @Test
    void positionIsInTheRectangleTest2() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        rectangle.setPosition(new Position(0, 0, 0));

        assertTrue(rectangle.positionIsInTheRectangle(new Position(1.5,2.5, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(-1.5,-2.5, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(-1.5,0, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(0,-2.5, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(0,2.5, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(1.5,0, 0)));
    }
    @Test
    void positionIsInTheRectangleTest4() {
        Rectangle rectangle = new Rectangle(3, 5, 0);
        rectangle.setPosition(new Position(-1, -1, 0));

        assertTrue(rectangle.positionIsInTheRectangle(new Position(0.5,1.5, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(-2.5,-3.5, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(-2.5,-1, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(-1,-3.5, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(-1,1.5, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(0.5,-1, 0)));
    }
    @Test
    void positionIsInTheRectangleTest3() {
        Rectangle rectangle = new Rectangle(5, 3, Math.PI/2);
        rectangle.setPosition(new Position(0, 0, Math.PI/2));

        assertTrue(rectangle.positionIsInTheRectangle(new Position(1.49999,2.4999, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(-1.49999,-2.4999, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(-1.49999,0, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(0,-2.4999, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(0,2.4999, 0)));
        assertTrue(rectangle.positionIsInTheRectangle(new Position(1.49999,0, 0)));
    }

    @Test
    void computeSegmentTest(){
        rectangle.setPosition(new Position(0,0,0));
        List<Segment> res = rectangle.computeSegments();
        assertTrue(res.size() > 0);
    }

    @Test
    void generateBeaconTest(){
        rectangle.setPosition(new Position(0,0,0));
        List<Beacon> res = rectangle.generateBeacon();
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
}
