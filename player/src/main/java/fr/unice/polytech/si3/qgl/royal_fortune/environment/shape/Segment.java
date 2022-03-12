package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.util.Optional;

public class Segment {
    private Position pointA;
    private Position pointB;
    private double taille;
    //equation of the line
    private double a;
    private double b;

    public Segment(Position pointA, Position pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        a=(pointA.getY()-pointB.getY())/(pointA.getX()-pointB.getX());
        b=pointA.getY()-a*pointA.getX();
        taille=0;
    }

    /**
     * Compute the intersection between this segment and an other segment
     * @param segment
     * @return the position of the intersection
     */
    public Optional<Position> computeIntersectionWith(Segment segment){
        double x=(b-segment.getB())/(a-segment.getA());
        double y=a*x+b;
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
