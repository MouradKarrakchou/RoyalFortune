package fr.unice.polytech.si3.qgl.royal_fortune.tooling;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;

import java.util.logging.Logger;

public class Application {
	
	public static void main(String [] args) {
		final Logger logger = Logger.getLogger(Application.class.getName());

		Cockpit cockpit = new Cockpit();
		String out = "An instance of my team player: " + cockpit;
		logger.info(out);
		out = "When called, it returns some JSON: " + cockpit.nextRound("");
		logger.info(out);
	}
}
