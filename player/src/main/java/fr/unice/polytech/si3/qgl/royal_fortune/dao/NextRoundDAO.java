package fr.unice.polytech.si3.qgl.royal_fortune.dao;

import java.util.List;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

public class NextRoundDAO {
	Ship ship;
	List<Entities> visibleEntities;
	
	public NextRoundDAO() {}
	public NextRoundDAO(Ship ship, List<Entities> visibleEntities) {
		super();
		this.ship = ship;
		this.visibleEntities = visibleEntities;
	}
	
	public Ship getShip() {
		return ship;
	}

	public List<Entities> getVisibleEntities() {
		return visibleEntities;
	}
	
}
