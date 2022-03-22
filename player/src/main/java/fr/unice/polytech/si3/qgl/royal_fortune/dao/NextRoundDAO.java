package fr.unice.polytech.si3.qgl.royal_fortune.dao;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

public class NextRoundDAO {
	Ship ship;
	List<SeaEntities> visibleEntities;
	Wind wind;

//        return "{\"ship\":"+ ship.toString()+",\n \"wind\":"+wind.toString()+",\n "+JsonManagerTool.convertListToJson(visiblesEntities)+"}";
	public NextRoundDAO() {}
	public NextRoundDAO(Ship ship, List<SeaEntities> visibleEntities, Wind wind) {
		super();
		this.ship = ship;
		this.visibleEntities = visibleEntities;
		this.wind = wind;
	}
	
	public Ship getShip() {
		return ship;
	}

	public Wind getWind() { return wind; }

	public List<SeaEntities> getVisibleEntities() {
		return visibleEntities;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
