package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
@JsonIgnoreProperties(value = {
		"type"
})
public class
Rectangle extends Shape{

	private double width;
	private double height;
	private double orientation;
	private List<Segment> segmentList = new ArrayList<>();


	public Rectangle() {}
	
	public Rectangle(double width, double height, double orientation) {
		super("rectangle");
		this.width = width;
		this.height = height;
		this.orientation = orientation;
	}

	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public double getOrientation() {
		return orientation;
	}

	@Override
	public boolean equals(Object object){
		if (!(object instanceof Rectangle))
			return false;
		Rectangle rectangle=(Rectangle) object;
		if (this.width==rectangle.getWidth()&&
		this.height==rectangle.height&&
		this.orientation==rectangle.orientation)
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + String.valueOf(width).hashCode();
		result = 31 * result + Integer.parseInt(String.valueOf(height));
		result = 31 * result + String.valueOf(orientation).hashCode();
		return result;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode oarActionJSON = mapper.createObjectNode();
		oarActionJSON.put("type", "rectangle");
		oarActionJSON.put("width", width);
		oarActionJSON.put("height", height);
		oarActionJSON.put("orientation", orientation);


		try {
			return mapper.writeValueAsString(oarActionJSON);
		} catch (JsonProcessingException e) {
			logger.log(Level.INFO, "Exception");
		}
		return "";
	}

	public List<Segment> getSegmentList() {
		return segmentList;
	}
}
