package fr.unice.polytech.si3.qgl.royal_fortune.dao;

import java.util.List;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

public class NextRoundDAO {
	Ship ship;
	List<Entities> visibleEntities;
	Wind wind;
	List<SeaEntities> seaEntities;



	public NextRoundDAO() {}
	public NextRoundDAO(Ship ship, List<Entities> visibleEntities, Wind wind,List<SeaEntities> seaEntities) {
		super();
		this.ship = ship;
		this.visibleEntities = visibleEntities;
		this.wind = wind;
		this.seaEntities=seaEntities;
	}
	
	public Ship getShip() {
		return ship;
	}

	public Wind getWind() { return wind; }

	public List<SeaEntities> getSeaEntities() {
		return seaEntities;
	}

	public List<Entities> getVisibleEntities() {
		return visibleEntities;
	}
	
}
