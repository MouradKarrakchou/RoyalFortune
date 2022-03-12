package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.util.List;
import java.util.logging.Level;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
@JsonIgnoreProperties(value = {
		"type"
})
public class Rectangle<list> extends Shape{

	private double width;
	private double height;
	private double orientation;
	private List<Segment> segmentlist;
	Position position;
	
	public Rectangle() {}
	
	public Rectangle(String type, double width, double height, double orientation) {
		super(type);
		this.width = width;
		this.height = height;
		this.orientation = orientation;
	}

	/**
	 * Compute the 4 segments of the rectangle
	 * @return a list that contain the 4 segment of the rectangle [H, D, B, G]
	 */
	private List<Segment> computeSegments() {return null;}

	/**
	 * Compute the 4 corners of the rectangle
	 * @return a list that contain the 4 corner of the rectangle [HG, HD, BG, BD]
	 */
	private List<Position> computeCorner() {return null;}

	/**
	 * Compute the intersection between the current shape and a segment
	 * @param segment
	 * @return the 2 positions of the intersection
	 */
	private Position[] computeIntersectionWith(Segment segment){return null;}

	/**
	 * When we set the position the rectangle create his segments
	 * @param position
	 */
	public void setPosition(Position position) {
		this.position = position;
		this.segmentlist = computeSegments();
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
}
