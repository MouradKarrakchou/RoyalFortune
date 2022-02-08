package fr.unice.polytech.si3.qgl.royal_fortune.ship.shape;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Shape {
	private String type;
	private double radius;


	public Shape() {}
	public Shape(String type) {
		this.type = type;
	}
	public Shape(String type,double radius) {
		this.radius=radius;
		this.type = type;
	}


	public String getType() {
		return type;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "Shape [type=" + type + ", radius="+ radius+"]";
	}
	
	
}
