package fr.unice.polytech.si3.qgl.royal_fortune.DAO;

import java.util.ArrayList;

import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

public class InitGameDAO {
	Goal goal;
	Ship ship;
	ArrayList<Sailor> sailors;
	int shipCount;
	
	public InitGameDAO() {}
	public InitGameDAO(Goal goal, Ship ship, ArrayList<Sailor> sailors, int shipCount) {
		super();
		this.goal = goal;
		this.ship = ship;
		this.sailors = sailors;
		this.shipCount = shipCount;
	}
	
	public Goal getGoal() {
		return goal;
	}
	public Ship getShip() {
		return ship;
	}
	public ArrayList<Sailor> getSailors() {
		return sailors;
	}
	public int getShipCount() {
		return shipCount;
	}
	
}
