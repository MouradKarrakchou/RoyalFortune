package fr.unice.polytech.si3.qgl.royal_fortune;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Cockpit implements ICockpit {
	private Ship ship;
	private ArrayList<Sailor> sailors;
	private Captain captain;

	public void initGame(String game) {
		System.out.println("Init game input: " + game);

		String shipJson = JsonManager.getNode(game, "ship");
		ship = (Ship) JsonManager.readJson(shipJson, "ship");
		
		String sailorsJson = JsonManager.getNode(game, "sailors");
		sailors = (ArrayList<Sailor>) JsonManager.readJson(sailorsJson, "sailors");

		captain = new Captain(ship, sailors);
	}

	public String nextRound(String round) {
		System.out.println("Next round input: " + round);

	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}
