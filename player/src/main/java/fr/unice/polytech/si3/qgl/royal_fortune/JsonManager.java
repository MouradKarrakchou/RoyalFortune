package fr.unice.polytech.si3.qgl.royal_fortune;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

public class JsonManager {

	public static Ship readJson(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Ship readValue = mapper.readValue(json, Ship.class);
		return readValue;
	}

	public static void writeJson(String json) {

	}

	public static String readFileAsString(String file) throws Exception {
		return new String(Files.readAllBytes(Paths.get(file)));
	}

	public static void read2(String path) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Ship ship = mapper.readValue(Paths.get(path).toFile(), Ship.class);
		System.out.println(ship);
	}
	
	public static void convertToJson(Ship ship) throws IOException {
		 ObjectMapper mapper = new ObjectMapper();
	     mapper.enable(SerializationFeature.INDENT_OUTPUT);
		// Convert object to JSON string
        String postJson = mapper.writeValueAsString(ship);
        System.out.println(postJson);

        // Save JSON string to file
        FileOutputStream fileOutputStream = new FileOutputStream("post.json");
        mapper.writeValue(fileOutputStream, ship);
        fileOutputStream.close();
	}
}
