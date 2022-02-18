package fr.unice.polytech.si3.qgl.royal_fortune.json_management;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Rectangle;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Shape;

public class JsonManager {

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
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * For a given Json, will find the part concerning Sailors initialization and will instantiate the list.
	 * @param json The given json
	 * @return the associated Sailors.
	 */
	public static ArrayList<Sailor> readSailorsJson(String json){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Sailor.class);
		try {
			return mapper.readValue(json, javaType);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Shape readShapeJson(String json, String type){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		try {
			switch(type) {
			case "circle": return mapper.readValue(json, Circle.class);
			case "rectangle": return mapper.readValue(json, Rectangle.class);
			default: return null;
			}
				
		} catch (JsonProcessingException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<Entities> readEntitiesJson(String json) {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Entities.class);
		try {
			return mapper.readValue(json, javaType);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<Action> readActionJson(String json) {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Action.class);
		try {
			return mapper.readValue(json, javaType);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return(null);
	}

	public static String getNode(String json, String searchNode){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree(json);
			JsonNode shipJson = actualObj.get(searchNode);
			return shipJson.toString();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
