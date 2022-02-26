package fr.unice.polytech.si3.qgl.royal_fortune.ship;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Deck {
	private int width;
	private int length;
	final Logger LOGGER = Logger.getLogger(JsonManager.class.getName());
	
	public Deck() {}

	public Deck(int width, int length) {
		this.width = width;
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode oarActionJSON = mapper.createObjectNode();
		oarActionJSON.put("width", width);
		oarActionJSON.put("length", length);
		try {
			return mapper.writeValueAsString(oarActionJSON);
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.INFO, "Exception");
		}
		return "";
	}
}
