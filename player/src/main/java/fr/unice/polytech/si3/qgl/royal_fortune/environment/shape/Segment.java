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
        a=(pointA.getY()-pointB.getY())/(pointA.getX()-pointB.getX());
        b=pointA.getY()-a*pointA.getX();
        length= Mathematician.distanceFormula(pointA,pointB);
    }

    /**
     * Compute the intersection between this segment and another segment
     * @param segment a segment
     * @return the position of the intersection
     */
    public Optional<Position> computeIntersectionWith(Segment segment){
        if (a==segment.getA()){
            boolean pointAIsInTheSegment=pointInSegment(pointA);
            boolean pointBIsInTheSegment=pointInSegment(pointB);
            if (b==segment.getB()){
                if (pointAIsInTheSegment)
                    return Optional.of(pointA);
                else if (pointBIsInTheSegment)
                    return Optional.of(pointB);
            }}
        else if (Math.abs(a)==Float.POSITIVE_INFINITY&& Math.abs(segment.getA())==Float.POSITIVE_INFINITY){
            if (b==segment.getB()) return Optional.of(pointA);
        }
        else if (Math.abs(a)==Float.POSITIVE_INFINITY){
            double intersectionY=segment.getA()*pointA.getX()+segment.getB();
            boolean rightY=segment.pointInSegment(new Position(pointA.getX(),intersectionY));
            boolean rightX=pointA.getX()<=Math.max(segment.getPointA().getX(),segment.getPointB().getX())
                    &&pointA.getX()>=Math.min(segment.getPointA().getX(),segment.getPointB().getX());
            if (rightX&& rightY) return (Optional.of(new Position(pointA.getX(),intersectionY)));
        }
        else if (Math.abs(segment.getA())==Float.POSITIVE_INFINITY){
            return(segment.computeIntersectionWith(this));
        }
        else
        {
        double x=(segment.getB()-b)/(a-segment.getA());
        double y=a*x+b;
        Position newPoint=new Position(x,y);
        if (segment.pointInSegment(newPoint)&&this.pointInSegment(newPoint)){
            return Optional.of(new Position(x,y));
        }}
        return Optional.empty();
    }

    public boolean pointInSegment(Position point){
        if (a==Float.POSITIVE_INFINITY)
            return (point.getX()==pointA.getX());
        return(a*point.getX()-b==point.getY());
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
