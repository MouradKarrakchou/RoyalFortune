package fr.unice.polytech.si3.qgl.royal_fortune;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.NextRoundDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.exception.EmptyDaoException;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;

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
			LOGGER.info("Empty Dao");
		}
		assert initGameDAO != null;
		ship = initGameDAO.getShip();
		sailors = initGameDAO.getSailors();
		goal = initGameDAO.getGoal();
		captain = new Captain(ship, sailors, goal, new FictitiousCheckpoint(goal.getCheckPoints()), initGameDAO.getWind());
	}

	public String nextRound(String round){
		System.out.println(round);
		NextRoundDAO nextRoundDAO = null;
		try {
			nextRoundDAO = createNextRoundDAO(round);
		} catch (EmptyDaoException e) {
			LOGGER.info("Empty Dao");
		}
		LOGGER.info("Next round input: " + round);
		updateWithNextRound(nextRoundDAO);
		String actions  =captain.roundDecisions();
		System.out.println("Actions = "+actions);
		return actions;
	}
	public void updateWithNextRound(NextRoundDAO nextRoundDAO){
		Ship newShip = nextRoundDAO.getShip();
		//System.out.println("New ship = "+newShip);
		ship.updatePos(newShip.getPosition());
		ship.setEntities(newShip.getEntities());
		captain.updateSeaEntities(nextRoundDAO.getVisibleEntities());
		captain.setWind(nextRoundDAO.getWind());
		captain.getPreCalculator().setWind(captain.getWind());
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

	public Captain getCaptain() {
		return captain;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	@Override
	public ArrayList<String> getLogs() {
		return new ArrayList<>();
	}


}
