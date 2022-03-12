package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

public class Segment {
    private Position pointA;
    private Position pointB;

    public Segment(Position pointA, Position pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    /**
     * Compute the intersection between this segment and an other segment
     * @param segment
     * @return the position of the intersection
     */
    private Position computeIntersectionWith(Segment segment){
        return null;
    }

    public Position getPointA() {
        return pointA;
    }

    public Position getPointB() {
        return pointB;
    }
}
