package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Oar extends Entities{
	private boolean left;
	private Sailor sailor;

	public Oar() {}

	public Oar(String type, int x, int y) {
		super(type, x, y);
	}

	public boolean isLeft() {
		return y == 0;
	}

	public Sailor getSailor() {
		return sailor;
	}

	public void setSailor(Sailor sailor) {
		this.sailor = sailor;
	}
}
