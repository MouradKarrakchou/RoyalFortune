package fr.unice.polytech.si3.qgl.royal_fortune;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.NextRoundDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Cockpit implements ICockpit {
	private Ship ship;
	private List<Sailor> sailors;
	private Goal goal;
	private Captain captain;
	private static final Logger LOGGER = Logger.getLogger(Cockpit.class.getName());

	public void initGame(String game) {
		String out = "Init game input: " + game;
		LOGGER.info(out);

		//initialization InitGameDAO
		InitGameDAO initGameDAO = JsonManager.readInitGameDAOJson(game);
		ship = initGameDAO.getShip();
		sailors = initGameDAO.getSailors();
		goal = initGameDAO.getGoal();
		captain = new Captain(ship, sailors, goal);
	}

	public String nextRound(String round) {
		NextRoundDAO nextRoundDAO = JsonManager.readNextRoundDAOJson(round);
		Ship newShip = nextRoundDAO.getShip();
		ship.setPosition(newShip.getPosition());
		ship.setEntities(newShip.getEntities());
		String out = "Next round input: " + round;
		LOGGER.info(out);

		return captain.roundDecisions();
	}

	public Ship getShip() {
		return ship;
	}

	public List<Sailor> getSailors() {
		return sailors;
	}

	public Goal getGoal() {
		return goal;
	}

	@Override
	public ArrayList<String> getLogs() {
		return new ArrayList<>();
	}
}
