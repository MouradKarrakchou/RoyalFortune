package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	Position position;
	private String type;
	final Logger logger = Logger.getLogger(Shape.class.getName());

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
			logger.log(Level.INFO, "Exception");
		}
		return "";
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Generate some beacons all around the shape
	 * @return the list of beacon of the shape
	 */
	public List<Beacon> generateBeacon(){return null;}

	public Optional<Circle> isCircle(){
		if(this instanceof Circle){
			Circle current = (Circle) this;
			return Optional.of(current);
		}
		return Optional.empty();
	}
	public Optional<Rectangle> isRectangle(){
		if(this instanceof Rectangle){
			Rectangle current = (Rectangle) this;
			return Optional.of(current);
		}
		return Optional.empty();
	}

}
