package fr.unice.polytech.si3.qgl.royal_fortune.tooling;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

public class JsonManager {

	public static Ship readJson(String json) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(json, Ship.class);
	}

	public static void writeJson(String json) {}

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
        FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir")+"/tooling/src/ressources/data.json");
        mapper.writeValue(fileOutputStream, ship);
        fileOutputStream.close();
	}
}
