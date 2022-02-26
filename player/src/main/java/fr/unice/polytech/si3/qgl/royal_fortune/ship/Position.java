package fr.unice.polytech.si3.qgl.royal_fortune.ship;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Position {
	private double x;
	private double y;
	private double orientation;
	final Logger logger = Logger.getLogger(Position.class.getName());
	
	public Position() {}
	public Position(double x, double y, double orientation) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getOrientation() {
		return orientation;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (! (o instanceof Position)) return false;
		Position position = (Position) o;

		if (Double.compare(position.x, x) != 0) return false;
		if (Double.compare(position.y, y) != 0) return false;
		return Double.compare(position.orientation, orientation) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, orientation);
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode oarActionJSON = mapper.createObjectNode();
		oarActionJSON.put("x", x);
		oarActionJSON.put("y", y);
		oarActionJSON.put("orientation", orientation);

		try {
			return mapper.writeValueAsString(oarActionJSON);
		} catch (JsonProcessingException e) {
			logger.log(Level.INFO, "Exception");
		}
		return "";
	}
}
