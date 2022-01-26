package fr.unice.polytech.si3.qgl.royal_fortune;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

public class Main {

	public static void main(String[] args) throws Exception {
		JsonManager jsonMan = new JsonManager();
		
		String file = "src/ressources/file.json";
        String json = jsonMan.readFileAsString(file);
        	
		System.out.println("-------start-------");
		Ship ship = new Ship("ship", 150, null, "HelloWolrd", null, null, null);
		jsonMan.convertToJson(ship);
		//jsonMan.read2(file);
		
		Ship ship2 = jsonMan.readJson(json);
		System.out.println(ship2.toString());
		
		System.out.println("--------end--------");
	}

}
