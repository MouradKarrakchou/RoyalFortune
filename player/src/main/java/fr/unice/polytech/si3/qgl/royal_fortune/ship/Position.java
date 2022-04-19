package fr.unice.polytech.si3.qgl.royal_fortune.ship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.IPositionable;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Position implements IPositionable {
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

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
		this.orientation = 0.0;
	}

	public Position(Point p) {
		this.x = p.getX();
		this.y = p.getY();
		this.orientation = 0.0;
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

    public void update(Position position) {
		this.x=position.getX();
		this.y=position.getY();
		this.orientation=position.getOrientation();
    }
	@Override
	public boolean equals(Object object){
		if (object == null) return false;
		if (this.getClass() != object.getClass()) return false;

		Position position = (Position) object;
		return ((Math.abs(position.getX()-x)<0.001)&&(Math.abs(position.getY()-y)<0.001));
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + String.valueOf(y).hashCode();
		result = 31 * result + Integer.parseInt(String.valueOf(y));
		result = 31 * result + String.valueOf(orientation).hashCode();
		return result;
	}

	@JsonIgnore
	@Override
	public Position getPosition(){
		return this;
	}
}
