package fr.unice.polytech.si3.qgl.royal_fortune.tooling;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;

import java.util.logging.Logger;

public class Application {
	
	public static void main(String [] args) {
		final Logger LOGGER = Logger.getLogger(Application.class.getName());

		Cockpit cockpit = new Cockpit();
		LOGGER.info("An instance of my team player: " + cockpit);
		LOGGER.info("When called, it returns some JSON: " + cockpit.nextRound(""));
	}
}
