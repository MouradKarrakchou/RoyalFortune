package fr.unice.polytech.si3.qgl.royal_fortune.dao;

import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.OtherShip;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

public class NextRoundDAO {
	private static final Logger LOGGER = Logger.getLogger(NextRoundDAO.class.getName());
	Ship ship;
	List<SeaEntities> visibleEntities;
	Wind wind;

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

	public void removeShipFromSeaEntities(){
		visibleEntities.removeIf(OtherShip.class::isInstance);
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			LOGGER.info("Json Exception");
		}
		return "";
	}
}
