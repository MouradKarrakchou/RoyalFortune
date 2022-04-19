package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Polygone;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class GeometryPolygoneTest {

    @Test
    void computeIntersection() {
        Position start = new Position(0,10);
        Position arrival = new Position(100, 10);

        Point[] vertices = {new Point(10, 0), new Point(80, 0), new Point(45, 100)};

        Polygone polygone = new Polygone(vertices, 0);
    }
}
