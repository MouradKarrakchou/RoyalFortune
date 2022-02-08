package fr.unice.polytech.si3.qgl.royal_fortune.ship.shape;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Circle extends Shape{

    private double radius;

    public Circle() {}
    public Circle(String type, double diameter) {
        super(type);
        this.radius = diameter;
    }

    public double getRadius() { return radius; }

}
