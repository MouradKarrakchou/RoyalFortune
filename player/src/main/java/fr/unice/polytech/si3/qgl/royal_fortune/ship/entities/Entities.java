package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Entities {
	
	private String type;
	private int x;
	protected int y;
	
	public Entities() {}
	public Entities(String type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public String getType() {
		return type;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
	public String toString() {
		return "Entities [type=" + type + ", x=" + x + ", y=" + y + "]";
	}
	
	
	
}
