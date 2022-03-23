package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.ArrayList;
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
	private List<Segment> segmentList;
	Position aPosition;
	int precision = 50;
	int radiusOfBeacon = 50;

	public Rectangle() {}
	
	public Rectangle(double width, double height, double orientation) {
		super("rectangle");
		this.width = width;
		this.height = height;
		this.orientation = orientation;
	}

	/**
	 * Compute the 4 segments of the rectangle
	 * @return a list that contain the 4 segment of the rectangle [H, D, B, G]
	 */
	public List<Segment> computeSegments() {
		List<Segment> rectangleSegment = new ArrayList<>();
		List<Position> rectangleCorner = computeCorner();
		Position hg = rectangleCorner.get(0);
		Position hd = rectangleCorner.get(1);
		Position bd = rectangleCorner.get(2);
		Position bg = rectangleCorner.get(3);
		rectangleSegment.add(new Segment(hg, hd));
		rectangleSegment.add(new Segment(hd, bd));
		rectangleSegment.add(new Segment(bd, bg));
		rectangleSegment.add(new Segment(bg, hg));
		return rectangleSegment;
	}

	/**
	 * Compute the 4 corners of the rectangle
	 * @return a list that contain the 4 corner of the rectangle [HG, HD, BD, BG]
	 */
	public List<Position> computeCorner() {
		List<Position> listOfPosition=new ArrayList<>();
		listOfPosition.add(Mathematician.changeBase(this.aPosition,-width/2,height/2));
		listOfPosition.add(Mathematician.changeBase(this.aPosition,width/2,height/2));
		listOfPosition.add(Mathematician.changeBase(this.aPosition,width/2,-height/2));
		listOfPosition.add(Mathematician.changeBase(this.aPosition,-width/2,-height/2));
		return listOfPosition;
	}
	@Override
	/**
	 * Generate beacons
	 * @return a list of Beacon
	 */
	public List<Beacon> generateBeacon() {
		List<Beacon> listOfPosition=new ArrayList<>();
		double widthUnit=width/ precision;
		double heightUnit=height/ precision;
		for (int k = -precision /5; k< precision + precision /5; k++){
		listOfPosition.add(new Beacon(Mathematician.changeBase(this.aPosition,-width/2+k*widthUnit,height/2),new Circle(radiusOfBeacon)));
		listOfPosition.add(new Beacon(Mathematician.changeBase(this.aPosition,width/2,height/2-k*heightUnit),new Circle(radiusOfBeacon)));
		listOfPosition.add(new Beacon(Mathematician.changeBase(this.aPosition,width/2-k*widthUnit,-height/2),new Circle(radiusOfBeacon)));
		listOfPosition.add(new Beacon(Mathematician.changeBase(this.aPosition,-width/2,-height/2+k*heightUnit),new Circle(radiusOfBeacon)));}
		return listOfPosition;
	}


	/**
	 * Compute the intersection between the current shape and a segment
	 * @param segment a segment
	 * @return the 2 positions of the intersection
	 */
	public List<Position> computeIntersectionWith(Segment segment){
		List<Position> intersectionsPosition = new ArrayList<>();
		Optional<Position> intersection;
		for(Segment seg : segmentList) {
			intersection = segment.computeIntersectionWith(seg);
			intersection.ifPresent(intersectionsPosition::add);
		}
		return ordered(intersectionsPosition,segment);
	}

	private List<Position> ordered(List<Position> intersectionsPosition, Segment segment) {
		List<Position> intersectionsPositionOrdered= new ArrayList<>();
		intersectionsPositionOrdered.add(segment.getPointA());
		while (!intersectionsPosition.isEmpty()){
			Position pointToCompare=intersectionsPositionOrdered.get(intersectionsPositionOrdered.size()-1);
			Position min=intersectionsPosition.get(0);
			double distMin=Mathematician.distanceFormula(aPosition,pointToCompare);
			for (Position thePosition :intersectionsPosition){
				if (Mathematician.distanceFormula(thePosition,pointToCompare)<distMin){
					distMin=Mathematician.distanceFormula(thePosition,pointToCompare);
					min=thePosition;
				}
			}
			intersectionsPositionOrdered.add(min);
			intersectionsPosition.remove(min);
		}
		intersectionsPositionOrdered.add(segment.getPointB());
		return intersectionsPositionOrdered;
	}

	/**
	 * check if the given Position is in the rectangle
	 * @return true if the point is in the rectangle
	 * @param pointA a point
	 */
	public boolean positionIsInTheRectangle(Position pointA) {
		List<Position> cornersList = computeCorner();
		Position hg = cornersList.get(0);
		Position hd = cornersList.get(1);
		Position bg = cornersList.get(3);

		double x = pointA.getX();
		double y = pointA.getY();

		double hdhgx = hd.getX() - hg.getX();
		double hdhgy = hd.getY() - hg.getY();
		double bghgx = bg.getX() - hg.getX();
		double bghgy = bg.getY() - hg.getY();

		boolean calculusHDHGx = (x - hg.getX()) * hdhgx + (y - hg.getY()) * hdhgy < 0;
		boolean calculusHDHGy = (x - hd.getX()) * hdhgx + (y - hd.getY()) * hdhgy > 0;
		boolean calculusBGHGx = (x - hg.getX()) * bghgx + (y - hg.getY()) * bghgy < 0;
		boolean calculusBGHGy = (x - bg.getX()) * bghgx + (y - bg.getY()) * bghgy > 0;

		return !calculusHDHGx && !calculusHDHGy && !calculusBGHGx && !calculusBGHGy;
	}

	@Override
	/**
	 * Update the x and y position but keep the same orientation. When we set the position the rectangle create his segments
	 * @param position
	 */
	public void setPosition(Position position) {
		this.aPosition = position;
		this.aPosition.setOrientation(orientation);
		this.segmentList = computeSegments();
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
