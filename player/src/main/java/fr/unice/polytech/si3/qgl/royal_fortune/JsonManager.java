package fr.unice.polytech.si3.qgl.royal_fortune;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import fr.unice.polytech.si3.qgl.royal_fortune.json_management.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

public class JsonManager {

	public static Object readJson(String json, String type){
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

		try {
			if(type.equals("ship"))
				return mapper.readValue(json, Ship.class);
			if(type.equals("sailors")) {
				JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Sailor.class);
				return mapper.readValue(json, javaType);
			}

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String writeJsonAction(List<Integer> id) {
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Action> listOfActions = new ArrayList<>();
		for (Integer integer : id) {
			listOfActions.add (new Action(integer,"OAR"));
		}
		String postJson = "";
		try {
			postJson = mapper.writeValueAsString(listOfActions);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(postJson);
		return postJson;
	}

	public static String readFileAsString(String file) throws Exception {
		return new String(Files.readAllBytes(Paths.get(file)));
	}

	public static void convertToJson(Ship ship) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		// Convert object to JSON string
		String postJson = mapper.writeValueAsString(ship);
		System.out.println(postJson);

		// Save JSON string to file
		FileOutputStream fileOutputStream = new FileOutputStream(
				System.getProperty("user.dir") + "/tooling/src/ressources/data.json");
		mapper.writeValue(fileOutputStream, ship);
		fileOutputStream.close();
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
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonNode shipJson = actualObj.get(searchNode);
		return shipJson.toString();
	}
}
