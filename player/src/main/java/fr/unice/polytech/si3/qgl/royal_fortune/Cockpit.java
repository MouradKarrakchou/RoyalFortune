package fr.unice.polytech.si3.qgl.royal_fortune;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.NextRoundDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.exception.EmptyDaoException;
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

	public Cockpit(){}
	public Cockpit(Ship ship, List<Sailor> sailors, Goal goal, Captain captain) {
		this.ship = ship;
		this.sailors = sailors;
		this.goal = goal;
		this.captain = captain;
	}

	public void initGame(String game)  {
		String out = "Init game input: " + game;
		LOGGER.info(out);
		InitGameDAO initGameDAO = null;
		try {
			initGameDAO = createInitGameDAO(game);
		} catch (EmptyDaoException e) {
			e.printStackTrace();
		}
		ship = initGameDAO.getShip();
		sailors = initGameDAO.getSailors();
		goal = initGameDAO.getGoal();
		captain = new Captain(ship, sailors, goal, new FictitiousCheckpoint(goal.getCheckPoints()));
	}

	public String nextRound(String round){
		NextRoundDAO nextRoundDAO = null;
		try {
			nextRoundDAO = createNextRoundDAO(round);
		} catch (EmptyDaoException e) {
			e.printStackTrace();
		}

		Ship newShip = nextRoundDAO.getShip();
		ship.updatePos(newShip.getPosition());
		ship.setEntities(newShip.getEntities());
		String out = "Next round input: " + round;
		LOGGER.info(out);

		return captain.roundDecisions();
	}

	public InitGameDAO createInitGameDAO(String json)throws EmptyDaoException{
		InitGameDAO initGameDAO = JsonManager.readInitGameDAOJson(json);
		if(initGameDAO == null) {
			throw new EmptyDaoException("InitGameDAO is null check the InitGame JSON");
		}
		return initGameDAO;
	}

	public NextRoundDAO createNextRoundDAO(String json)throws EmptyDaoException{
		NextRoundDAO nextRoundDAO = JsonManager.readNextRoundDAOJson(json);
		if(nextRoundDAO == null) {
			throw new EmptyDaoException("NextRoundDAO is null check the NextRound JSON");
		}
		return nextRoundDAO;
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
