package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
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
