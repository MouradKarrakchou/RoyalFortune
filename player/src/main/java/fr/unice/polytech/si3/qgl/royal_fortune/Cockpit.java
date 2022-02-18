package fr.unice.polytech.si3.qgl.royal_fortune;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.DAO.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.DAO.NextRoundDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
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

		//initialization InitGameDAO
		InitGameDAO initGameDAO = JsonManager.readInitGameDAOJson(game);
		ship = initGameDAO.getShip();
		sailors = initGameDAO.getSailors();
		goal = initGameDAO.getGoal();
		captain = new Captain(ship, sailors);
	}

	public String nextRound(String round) {
		NextRoundDAO nextRoundDAO = JsonManager.readNextRoundDAOJson(round);
		Ship newShip = nextRoundDAO.getShip();
		ship.setPosition(newShip.getPosition());
		ship.setEntities(newShip.getEntities());
		System.out.println("Next round input: " + round);

		DirectionsManager directionsManager = new DirectionsManager(ship, goal);
		return captain.roundDecisions(directionsManager);
	}

	public Ship getShip() {
		return ship;
	}

	public ArrayList<Sailor> getSailors() {
		return sailors;
	}

	public Goal getGoal() {
		return goal;
	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}
