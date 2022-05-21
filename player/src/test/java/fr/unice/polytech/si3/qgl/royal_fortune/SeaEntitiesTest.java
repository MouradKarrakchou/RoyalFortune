package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeaEntitiesTest {
    SeaEntities seaEntities;

    @BeforeEach
    void init() {
        seaEntities = new SeaEntities(new Position(2, 3, Math.PI), new Shape("Shape"));
    }

    @Test
    void getPositionTest() {
        Position position = new Position(2, 3, Math.PI);
        Position seaEntityPosition = seaEntities.getPosition();
        assertEquals(position.getX(), seaEntityPosition.getX());
        assertEquals(position.getY(), seaEntityPosition.getY());
        assertEquals(position.getOrientation(), seaEntityPosition.getOrientation());
    }
    @Test
    void equalsTest(){
        SeaEntities stream1= new SeaEntities(new Position(200,0),new Rectangle(100,100,0));
        SeaEntities stream2= new SeaEntities(new Position(200,0),new Rectangle(100,100,0));
        assertEquals(true,stream1.equals(stream2));
    }
    @Test
    void getShapeTest(){
        SeaEntities stream1= new Stream(new Position(200,0),new Rectangle(100,100,0),50);
        assertEquals(100,((Rectangle)stream1.getShapeForTool()).getWidth());
        assertEquals(110,((Rectangle)stream1.getShape()).getWidth());
        assertEquals(110,((Rectangle)stream1.getShape()).getHeight());
    }

    @Test
    void isStreamTest() {
        Stream stream = new Stream(new Position(0, 0, 0), new Shape("Shape"), 10);
        assertTrue(stream.isStream());
        assertFalse(stream.isReef());
    }

    @Test
    void isReefTest() {
        Reef reef = new Reef(new Position(0, 0, 0), new Shape("Shape"));
        assertTrue(reef.isReef());
        assertFalse(reef.isStream());
    }
}
