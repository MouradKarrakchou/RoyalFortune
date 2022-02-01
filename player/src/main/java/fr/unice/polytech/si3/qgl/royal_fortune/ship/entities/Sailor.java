package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Sailor extends Entities{
	Integer id;
	private String name;
	
	public Sailor() {}
	public Sailor(String type, int x, int y,int id,String name) {
		super(type, x, y);
		this.id=id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Integer getId() {
		return (id);
	}
}
