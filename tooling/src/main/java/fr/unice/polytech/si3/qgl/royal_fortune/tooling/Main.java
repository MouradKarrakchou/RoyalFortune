package fr.unice.polytech.si3.qgl.royal_fortune.tooling;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

public class Main {

	public static void main(String[] args) throws Exception {	
		String file = System.getProperty("user.dir")+"/src/ressources/data.json";
		String json = JsonManager.readFileAsString(file);
        	
		System.out.println("-------start-------");
		Ship ship = JsonManager.readJson(json);
		System.out.println(ship.toString());
		System.out.println("--------end--------");
		
		Position pos = new Position(1, 2, 3);
		Ship secondShip = new Ship("ship",100, pos, "ougougoug", null, null, null);
		JsonManager.convertToJson(secondShip);
	}

}
