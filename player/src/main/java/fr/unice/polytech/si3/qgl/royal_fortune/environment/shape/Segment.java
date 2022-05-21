package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Vector;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.awt.*;
import java.util.Objects;
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
    public Optional<Position> computeIntersectionWith(Segment segment){
        if (a==segment.getA()||(Math.abs(a)==Float.POSITIVE_INFINITY&& Math.abs(segment.getA())==Float.POSITIVE_INFINITY)){
            boolean pointAIsInTheSegment=pointInSegment(segment.pointA)||segment.pointInSegment(pointA);
            boolean pointBIsInTheSegment=pointInSegment(segment.pointB)||segment.pointInSegment(pointB);
            if (b==segment.getB()){
                if (pointAIsInTheSegment)
                    return Optional.of(segment.pointA);
                else if (pointBIsInTheSegment)
                    return Optional.of(segment.pointB);
            }}

        Vector I=new Vector(segment);
        Vector J=new Vector(this);
        Position A=segment.getPointA();
        Position C=this.pointA;
        if(I.x*J.y-I.y*J.x == 0)
            return Optional.empty();
        double m = -(-I.x*A.getY()+I.x*C.getY()+I.y*A.getX()-I.y*C.getX())/(I.x*J.y-I.y*J.x);
        double k = -(A.getX()*J.y-C.getX()*J.y-J.x*A.getY()+J.x*C.getY())/(I.x*J.y-I.y*J.x);
        if (m>=1||m<=0||k>=1||k<=0)
            return Optional.empty();
        return (Optional.of(new Position(A.getX()+k*I.x,A.getY()+k*I.y )));
    }

    public boolean pointInSegment(Position point){
        if (Mathematician.distanceFormula(point,pointA)>length||Mathematician.distanceFormula(point,pointB)>length)
            return false;
        if (Math.abs(a)==Float.POSITIVE_INFINITY&&Mathematician.distanceFormula(point,pointA)<=length)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Double.compare(segment.length, length) == 0 && Double.compare(segment.a, a) == 0 && Double.compare(segment.b, b) == 0 && Objects.equals(pointA, segment.pointA) && Objects.equals(pointB, segment.pointB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointA, pointB);
    }
}
