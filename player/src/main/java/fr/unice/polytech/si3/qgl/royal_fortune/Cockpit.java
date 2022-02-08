package fr.unice.polytech.si3.qgl.royal_fortune;

import java.util.ArrayList;
import java.util.List;

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
	private Goal goal;
	private Captain captain;

	public void initGame(String game) {
		System.out.println("Init game input: " + game);

		// Initialize the ship
		String shipJson = JsonManager.getNode(game, "ship");
		ship = JsonManager.readShipJson(shipJson);
		
		String sailorsJson = JsonManager.getNode(game, "sailors");
		sailors = JsonManager.readSailorsJson(sailorsJson);

		String checkpointsJson = JsonManager.getNode(game,"goal");
		goal = JsonManager.readGoalJson(checkpointsJson);

		captain = new Captain(ship, sailors, goal);
	}

	public String nextRound(String round) {
		String shipJson = JsonManager.getNode(round, "ship");
		Ship newShip = JsonManager.readShipJson(shipJson);
		ship.setPosition(newShip.getPosition());
		ship.setEntities(newShip.getEntities());
		System.out.println("Next round input: " + round);
		return captain.roundDecisions();
	}

	public Ship getShip() {
		return ship;
	}

	public ArrayList<Sailor> getSailors() {
		return sailors;
	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}
