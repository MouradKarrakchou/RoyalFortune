package fr.unice.polytech.si3.qgl.royal_fortune.ship;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Deck {
	private int width;
	private int length;
	
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
			e.printStackTrace();
		}
		return "";
	}
}
