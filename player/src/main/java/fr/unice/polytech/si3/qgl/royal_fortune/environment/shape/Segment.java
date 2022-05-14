package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.awt.*;
import java.util.Optional;

public class Segment {
    private Position pointA;
    private Position pointB;
    private double length;

    //Equation of the line
    private double a;
    private double b;

    public Segment(Position pointA, Position pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        a = computeA(pointA, pointB);
        if (Math.abs(a)!=Float.POSITIVE_INFINITY)
            b = computeB(pointA);
        length= Mathematician.distanceFormula(pointA,pointB);
    }
    public Segment(Point pointA, Point pointB) {
        this.pointA = new Position(pointA.getX(),pointA.getY());
        this.pointB = new Position(pointB.getX(),pointB.getY());
        a = computeA(this.pointA, this.pointB);
        if (Math.abs(a)!=Float.POSITIVE_INFINITY)
            b = computeB(this.pointA);
        length= Mathematician.distanceFormula(this.pointA,this.pointB);
    }

    /**
     * Compute the intersection between this segment and another segment
     * @param segment a segment
     * @return the position of the intersection
     */
    public Optional<Position> computeIntersectionWith(Segment segment){
        if (Math.abs(a)==Float.POSITIVE_INFINITY&& Math.abs(segment.getA())==Float.POSITIVE_INFINITY){
            if (pointA.getX()==segment.getPointA().getX()) return Optional.of(pointA);
        }
        else if (a==segment.getA()){
            boolean pointAIsInTheSegment=pointInSegment(pointA);
            boolean pointBIsInTheSegment=pointInSegment(pointB);
            if (b==segment.getB()){
                if (pointAIsInTheSegment)
                    return Optional.of(pointA);
                else if (pointBIsInTheSegment)
                    return Optional.of(pointB);
            }}
        else if (Math.abs(a)==Float.POSITIVE_INFINITY){
            double intersectionY=segment.getA()*pointA.getX()+segment.getB();
            boolean rightY=this.pointInSegment(new Position(pointA.getX(),intersectionY));
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
        if (Mathematician.distanceFormula(point,pointA)>length||Mathematician.distanceFormula(point,pointB)>length)
            return false;
        if (Math.abs(a)==Float.POSITIVE_INFINITY)
            return (point.getX()==pointA.getX());
        return(Math.abs(a*point.getX()+b-point.getY())<0.001);
    }

    /**
     * Calculate the angle between the segment and the orientation of a rectangle
     * @param orientationOfStream orientation of the stream
     * @return the angle between the segment and the orientation of a rectangle
     */
    public double angleIntersectionBetweenSegmentAndRectangle(double orientationOfStream) {
        double distanceSegmentExtremityX = pointB.getX() - pointA.getX();
        double distanceSegmentExtremityY = pointB.getY() - pointA.getY();
        double distanceSegmentExtremity = Mathematician.distanceFormula(pointA, pointB);

        double rectangleOrientation = orientationOfStream;
        double num = distanceSegmentExtremityX * Math.cos(rectangleOrientation) + distanceSegmentExtremityY * Math.sin(rectangleOrientation);

        return Math.acos(num / distanceSegmentExtremity);
    }

    public Position getPointA() {
        return pointA;
    }

    public Position getPointB() {
        return pointB;
    }

    public double computeA(Position pointA, Position pointB){ return (pointA.getY()-pointB.getY())/(pointA.getX()-pointB.getX());}
    public double computeB(Position pointA){ return (pointA.getY()-a*pointA.getX());}


    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getLength() {
        return length;
    }

    public void setPointA(Position pointA) {
        this.pointA = pointA;
    }

    public void setPointB(Position pointB) {
        this.pointB = pointB;
    }

    @Override
    public String toString() {
        return "PointA: {"+pointA.getX()+";"+pointA.getY()+"}   PointB: {"+pointB.getX()+";"+pointB.getY()+"}";
    }
}
