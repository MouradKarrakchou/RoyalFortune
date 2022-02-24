package fr.unice.polytech.si3.qgl.royal_fortune.ship.shape;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
@JsonTypeInfo(use = Id.NAME, property = "type", include = As.EXTERNAL_PROPERTY)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = Circle.class, name = "circle"),
        @JsonSubTypes.Type(value = Rectangle.class, name = "rectangle")
})
public class Shape {
	private String type;

	public Shape() {}
	public Shape(String type) {
		this.type = type;
	}


	public String getType() {
		return type;
	}
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode oarActionJSON = mapper.createObjectNode();
		oarActionJSON.put("life", type);

		try {
			return mapper.writeValueAsString(oarActionJSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
}
