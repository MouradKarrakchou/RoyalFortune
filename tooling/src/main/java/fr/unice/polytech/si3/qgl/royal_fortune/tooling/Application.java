package fr.unice.polytech.si3.qgl.royal_fortune.tooling;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;

import java.util.logging.Logger;

public class Application {

	public static void main(String[] args)
	{
		fr.unice.polytech.si3.qgl.soyouz.tooling.Application.runSimulator(fr.unice.polytech.si3.qgl.royal_fortune.Cockpit.class); // <-----------
	}
}
