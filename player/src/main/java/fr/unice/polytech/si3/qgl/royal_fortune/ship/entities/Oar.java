package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
@JsonIgnoreProperties(value = {
		"type",
		"left"
	})

public class Oar extends Entities{
	public Oar() {}

	public Oar(int x, int y) {
		super("oar", x, y);
	}

	public boolean isLeft() {
		return y == 0;
	}
}
