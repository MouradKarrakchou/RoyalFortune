package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Sailor extends Entities{

	private String name;
	
	public Sailor() {}
	public Sailor(String type, int x, int y, String name) {
		super(type, x, y);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
