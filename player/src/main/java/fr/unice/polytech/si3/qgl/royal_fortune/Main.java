package fr.unice.polytech.si3.qgl.royal_fortune;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

public class Main {

	public static void main(String[] args) throws Exception {	
		String file = System.getProperty("user.dir")+"/src/ressources/file.json";
		String json = JsonManager.readFileAsString(file);
        	
		System.out.println("-------start-------");
		Ship ship = JsonManager.readJson(json);
		System.out.println(ship.toString());
		System.out.println("--------end--------");
	}

}
