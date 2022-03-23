package fr.unice.polytech.si3.qgl.royal_fortune.json_management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.NextRoundDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class JsonManager {
	static final Logger LOGGER = Logger.getLogger(JsonManager.class.getName());
	static String exception = "Exception";

	/**
	 *Create a InitGameDAO with a the InitGame JSON
	 * @param game a String formated as JSON 
	 * @return a InitGameDAO that describe the game
	 */
	public static InitGameDAO readInitGameDAOJson(String game) {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		try {
			return mapper.readValue(game, InitGameDAO.class);
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.INFO, exception);
		}
		return null;
	}

	/**
	 *Create a NextRoundDAO with a the NextRound JSON
	 * @param round a String formated as JSON 
	 * @return a NextRoundDAO that describe the next round
	 */
	public static NextRoundDAO readNextRoundDAOJson(String round) {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		try {
			return mapper.readValue(round, NextRoundDAO.class);
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.INFO, exception);
		}
		return null;
	}
	
	/**
	 * For a given Json, will find the part concerning Ship initialization and will instantiate one.
	 * @param json The given json
	 * @return the associated ship.
	 */
	public static Ship readShipJson(String json){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		try {
			return mapper.readValue(json, Ship.class);
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.INFO, exception);
		}
		return null;
	}


	/**
	 * For a given Json, will find the part concerning Sailors initialization and will instantiate the list.
	 * @param json The given json
	 * @return the associated Sailors.
	 */
	public static List<Sailor> readSailorsJson(String json){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Sailor.class);
		try {
			return mapper.readValue(json, javaType);
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.INFO, exception);
		}
		return Collections.emptyList();
	}
	
	public static Shape readShapeJson(String json, String type){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		try {
			return switch (type) {
				case "circle" -> mapper.readValue(json, Circle.class);
				case "rectangle" -> mapper.readValue(json, Rectangle.class);
				default -> null;
			};
				
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.INFO, exception);
		}
		return null;
	}

	 /**
	 * For a given Json, will find the part concerning Ship initialization and will instantiate one.
	 * @param json The given json
	 * @return the associated ship.
	 */
	public static Goal readGoalJson(String json){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		try {
			return mapper.readValue(json, Goal.class);
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.INFO, exception);
		}
		return null;
	}
	public static List<Entities> readEntitiesJson(String json) {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Entities.class);
		try {
			return mapper.readValue(json, javaType);
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.INFO, exception);
		}
		return Collections.emptyList();
	}
	public static List<Action> readActionJson(String json) {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Action.class);
		try {
			return mapper.readValue(json, javaType);
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.INFO, exception);
		}
		return(null);
	}

	public static String getNode(String json, String searchNode){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		JsonNode actualObj;
		try {
			actualObj = mapper.readTree(json);
			JsonNode shipJson = actualObj.get(searchNode);
			return shipJson.toString();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.INFO, exception);
		}
		return null;
	}

}