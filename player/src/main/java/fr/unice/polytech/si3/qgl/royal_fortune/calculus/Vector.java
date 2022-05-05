package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.awt.*;

public class Vector {
    public double x;
    public double y;

    public Vector(Position tail, Position head) {
        double tailX = tail.getX();
        double tailY = tail.getY();
        double headX = head.getX();
        double headY = head.getY();

        this.x = headX - tailX;
        this.y = headY - tailY;
    }

    public Vector(Point tail, Point head) {
        double tailX = tail.getX();
        double tailY = tail.getY();
        double headX = head.getX();
        double headY = head.getY();

        this.x = headX - tailX;
        this.y = headY - tailY;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the magnitude of the vector.
     * @return the magnitude of the vector.
     */
    public double magnitude(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * Get the unit vector of the vector.
     * @return the unit vector of the vector.
     */
    public Vector unitVector(){
        double magnitude = this.magnitude();
        return new Vector(x/magnitude, y/magnitude);
    }

    /**
     * Get the unit normal vector of the vector.
     * @return the unit normal vector of the vector.
     */
    public Vector unitNormalVector(){
        Vector unitVector = this.unitVector();
        return new Vector(-unitVector.y, unitVector.x);
    }

    public double getOrientationFromSegment(Segment segment) {
        double x = segment.getPointB().getX() - segment.getPointA().getX();
        double y = segment.getPointB().getY() - segment.getPointA().getY();
        double norm = Mathematician.distanceFormula(segment.getPointA(), segment.getPointB());

        return Math.acos( (x*1 + y*0) / (norm*1) );
    }

}
