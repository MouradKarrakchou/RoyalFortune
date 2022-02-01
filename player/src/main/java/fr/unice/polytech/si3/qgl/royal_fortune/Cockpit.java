package fr.unice.polytech.si3.qgl.royal_fortune;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Cockpit implements ICockpit {
	private Ship ship;
	private ArrayList<Sailor> sailors;

	public void initGame(String game) {
		System.out.println("Init game input: " + game);
		
		String shipJson = JsonManager.getNode(game, "ship");
		ship = (Ship) JsonManager.readJson(shipJson, "ship");
		
		String sailorsJson = JsonManager.getNode(game, "sailors");
		sailors = (ArrayList<Sailor>) JsonManager.readJson(sailorsJson, "sailors");
		
		System.out.println(ship);
	}

	public String nextRound(String round) {
		System.out.println("Next round input: " + round);
		return JsonManager.writeJsonAction(idOfSailors());

		/*
		 * return " [{" + "    \"sailorId\": 0," + "    \"type\": \"OAR\"" + "  }," +
		 * "  {" + "    \"sailorId\": 1," + "    \"type\": \"OAR\"" + "  }]";
		 */
	}

	public List<Integer> idOfSailors() {
		return sailors.stream().map(entities -> ((Sailor) entities).getId()).collect(Collectors.toList());
	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}
