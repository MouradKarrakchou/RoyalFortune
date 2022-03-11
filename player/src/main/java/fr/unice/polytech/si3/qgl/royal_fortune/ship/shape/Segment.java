package fr.unice.polytech.si3.qgl.royal_fortune.ship.shape;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

public class Segment {
    private Position pointA;
    private Position pointB;

    public Segment(Position pointA, Position pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public Position getPointA() {
        return pointA;
    }

    public Position getPointB() {
        return pointB;
    }
}
