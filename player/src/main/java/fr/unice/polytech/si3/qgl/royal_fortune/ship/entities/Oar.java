package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
@JsonIgnoreProperties(value = {
	    "sailor",
		"type",
		"left"
	})

public class Oar extends Entities{
	private Sailor sailor;

	public Oar() {}

	public Oar(int x, int y) {
		super("oar", x, y);
	}

	public boolean isLeft() {
		return y != 0;
	}

	public Sailor getSailor() {
		return sailor;
	}

	public void setSailor(Sailor sailor) {
		this.sailor = sailor;
	}
}
