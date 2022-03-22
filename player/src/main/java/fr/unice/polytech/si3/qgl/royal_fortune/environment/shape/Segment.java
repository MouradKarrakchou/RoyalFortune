package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.util.Optional;

public class Segment {
    private Position pointA;
    private Position pointB;
    private double length;

    //equation of the line
    private double a;
    private double b;

    public Segment(Position pointA, Position pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        a = computeA(pointA, pointB);
        b = computeB(pointA, pointB);
        length= Mathematician.distanceFormula(pointA,pointB);
    }

    /**
     * Compute the intersection between this segment and another segment
     * @param segment a segment
     * @return the position of the intersection
     */
    public Optional<Position> computeIntersectionWith(Segment segment){
        double x=(b-segment.getB())/(a-segment.getA());
        double y=a*x+b;
        if ((Math.sqrt(Math.pow(pointB.getY()-y,2)+Math.pow(pointB.getX()-x,2)))<=length){
            return Optional.of(new Position(x,y));
        }
        else
            return Optional.empty();
    }

    /**
     * Calculate the angle between the segment and the orientation of a rectangle
     * @param rectangle a rectangle
     * @return the angle between the segment and the orientation of a rectangle
     */
    public double angleIntersectionBetweenSegmentAndRectangle(Rectangle rectangle) {
        double distanceSegmentExtremityX = pointB.getX() - pointA.getX();
        double distanceSegmentExtremityY = pointB.getY() - pointA.getY();
        double distanceSegmentExtremity = Mathematician.distanceFormula(pointA, pointB);

        double rectangleOrientation = rectangle.getOrientation();
        double num = distanceSegmentExtremityX * Math.cos(rectangleOrientation) + distanceSegmentExtremityY * Math.sin(rectangleOrientation);

        double intersectionAngle = Math.acos(num / distanceSegmentExtremity);

        return intersectionAngle;
    }

    public Position getPointA() {
        return pointA;
    }

    public Position getPointB() {
        return pointB;
    }

    public double computeA(Position pointA, Position pointB){ return (pointA.getY()-pointB.getY())/(pointA.getX()-pointB.getX());}
    public double computeB(Position pointA, Position pointB){ return (pointA.getY()-pointB.getY())/(pointA.getX()-pointB.getX());}


    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getLength() {
        return length;
    }
}
