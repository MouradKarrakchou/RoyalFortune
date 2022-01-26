package fr.unice.polytech.si3.qgl.royal_fortune.ship.shape;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Rectangle extends Shape{

	private double width;
	private double height;
	private double orientation;
	
	public Rectangle(String type, double width, double height, double orientation) {
		super(type);
		this.width = width;
		this.height = height;
		this.orientation = orientation;
	}
	
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public double getOrientation() {
		return orientation;
	}

}
