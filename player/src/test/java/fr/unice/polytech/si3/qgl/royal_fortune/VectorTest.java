package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Vector;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {
    @Test
    void instantiateVectorTest(){
        Position aPos = new Position(5, -3);
        Position bPos = new Position(-4, 2);
        Vector vectorPos = new Vector(aPos, bPos);
        assertEquals(-9, vectorPos.x);
        assertEquals(5, vectorPos.y);

        Point aPoint = new Point(5, -3);
        Point bPoint = new Point(-4, 2);
        Vector vectorPoint = new Vector(aPoint, bPoint);
        assertEquals(-9, vectorPoint.x);
        assertEquals(5, vectorPoint.y);
    }

    @Test
    void instantiateNegativeVectorTest(){
        Position aPos = new Position(-120, -4);
        Position bPos = new Position(-5, -37);
        Vector vectorPos = new Vector(aPos, bPos);
        assertEquals(115, vectorPos.x);
        assertEquals(-33, vectorPos.y);

        Point aPoint = new Point(-120, -4);
        Point bPoint = new Point(-5, -37);
        Vector vectorPoint = new Vector(aPoint, bPoint);
        assertEquals(115, vectorPoint.x);
        assertEquals(-33, vectorPoint.y);
    }

    @Test
    void calculateMagnitudeTest(){
        Vector vector = new Vector(5, -2);
        assertEquals(Math.sqrt(29), vector.magnitude());

        Vector vector1 = new Vector(-618, 1531);
        assertEquals(Math.sqrt(2725885), vector1.magnitude());

        Vector vector2 = new Vector(-5415, -366);
        assertEquals(Math.sqrt(29456181), vector2.magnitude());
    }

    @Test
    void calculateBasicUnitVectorTest(){
        Vector vector = new Vector(0, 100);
        assertEquals(0, vector.unitVector().x);
        assertEquals(1, vector.unitVector().y);

        Vector vector1 = new Vector(5, 0);
        assertEquals(1, vector1.unitVector().x);
        assertEquals(0, vector1.unitVector().y);
    }

    @Test
    void calculateUnitVectorTest(){
        Vector vector = new Vector(5, 5);
        assertEquals(5/ Math.sqrt(50), vector.unitVector().x);
        assertEquals(5/ Math.sqrt(50), vector.unitVector().y);

        Vector vector1 = new Vector(-27, 12);
        assertEquals(-27/ Math.sqrt(873), vector1.unitVector().x);
        assertEquals(12/ Math.sqrt(873), vector1.unitVector().y);
    }

    @Test
    void calculateNormalUnitVectorTest(){
        Vector vector = new Vector(0, 100);
        assertEquals(-1, vector.unitNormalVector().x);
        assertEquals(0, vector.unitNormalVector().y);
    }
}
