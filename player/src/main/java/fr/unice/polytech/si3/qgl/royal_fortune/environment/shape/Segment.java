package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

public class Segment {
    private Position pointA;
    private Position pointB;
    //equation of the line
    private double a;
    private double b;

    public Segment(Position pointA, Position pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        a=(pointA.getY()-pointB.getY())/(pointA.getX()-pointB.getX());
    }

    /**
     * Compute the intersection between this segment and an other segment
     * @param segment
     * @return the position of the intersection
     */
    public Position computeIntersectionWith(Segment segment){
        double x=b+segment.getB();
        return null;
    }

    public Position getPointA() {
        return pointA;
    }

    public Position getPointB() {
        return pointB;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }
}
