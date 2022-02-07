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
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

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

	public static String getNode(String json, String searchNode){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JsonNode actualObj = new JsonNode() {

			@Override
			public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer)
					throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public JsonParser traverse(ObjectCodec codec) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public JsonParser traverse() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public NumberType numberType() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public JsonToken asToken() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public JsonNode path(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public JsonNode path(String fieldName) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public JsonNodeType getNodeType() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public JsonNode get(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public JsonNode findValue(String fieldName) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public JsonNode findPath(String fieldName) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public JsonNode findParent(String fieldName) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean equals(Object o) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T extends JsonNode> T deepCopy() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String asText() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected JsonNode _at(JsonPointer ptr) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		try {
			actualObj = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonNode shipJson = actualObj.get(searchNode);
		return shipJson.toString();
	}
}
