package fr.unice.polytech.si3.qgl.royal_fortune.ship;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Position {
	private double x;
	private double y;
	private double orientation;
	
	public Position() {}
	public Position(double x, double y, double orientation) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getOrientation() {
		return orientation;
	}
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + ", orientation=" + orientation + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (! (o instanceof Position)) return false;
		Position position = (Position) o;

		if (Double.compare(position.x, x) != 0) return false;
		if (Double.compare(position.y, y) != 0) return false;
		return Double.compare(position.orientation, orientation) == 0;
	}
}