package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
