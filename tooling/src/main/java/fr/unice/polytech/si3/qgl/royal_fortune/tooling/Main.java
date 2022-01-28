package fr.unice.polytech.si3.qgl.royal_fortune.tooling;
import java.util.ArrayList;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

public class Main {

	public static void main(String[] args) throws Exception {	
		String file = System.getProperty("user.dir")+"/src/ressources/file.json";
		String json = JsonManager.readFileAsString(file);
		String shipJson = JsonManager.getNode(json, "ship");

		System.out.println("-------start-------");
		Ship ship = JsonManager.readJson(shipJson);
		System.out.println(ship.toString());
		System.out.println("--------end--------");
	}

}
