package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Sailor{
	private int id;
	private int x;
	private int y;
	private String name;
	private Entities targetEntity;
	
	public Sailor() {}

	public Sailor(int id, int x, int y, String name) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Integer getId() {
		return (id);
	}

	public void moveToTarget(){

	}
}
