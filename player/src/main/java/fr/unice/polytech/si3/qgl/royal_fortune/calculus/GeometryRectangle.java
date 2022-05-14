package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GeometryRectangle {

    private GeometryRectangle() {}

    /**
     * Compute the 4 segments of the rectangle
     * @return a list that contain the 4 segment of the rectangle [H, D, B, G]
     */
    public static List<Segment> computeSegments(Position seaEntitiesPos, Rectangle rectangle) {
        List<Segment> rectangleSegment = new ArrayList<>();
        List<Position> rectangleCorner = computeCorner(seaEntitiesPos, rectangle);
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
    public static List<Position> computeCorner(Position aPosition, Rectangle rectangle) {
        List<Position> listOfPosition=new ArrayList<>();
        double width = rectangle.getWidth();
        double height = rectangle.getHeight();
        listOfPosition.add(Mathematician.changeBase(aPosition,-height/2,width/2));
        listOfPosition.add(Mathematician.changeBase(aPosition,height/2,width/2));
        listOfPosition.add(Mathematician.changeBase(aPosition,height/2,-width/2));
        listOfPosition.add(Mathematician.changeBase(aPosition,-height/2,-width/2));
        return listOfPosition;
    }
    
    /**
     * Compute the intersection between the current shape and a segment
     * @param segment a segment
     * @return the 2 positions of the intersection
     */
    public static List<Position> computeIntersectionWith(Segment segment,Position seaEntitiesPos, Rectangle rectangle){
        List<Segment> segmentList = rectangle.getSegmentList();
        List<Position> intersectionsPosition = new ArrayList<>();
        Optional<Position> intersection;
        for(Segment seg : segmentList) {
            intersection = segment.computeIntersectionWith(seg);
            intersection.ifPresent(intersectionsPosition::add);
        }
        return distinct(ordered(intersectionsPosition,segment, seaEntitiesPos));
    }

    private static List<Position> distinct(List<Position> ordered) {
        List<Position> positionList=new ArrayList<>();
        List<Position> positionList2=new ArrayList<>();

        for (int k=0;k<ordered.size();k++){
            if (!positionList.contains(ordered.get(ordered.size()-k-1)))
                positionList.add(ordered.get(ordered.size()-k-1));
        }
        for (int k=0;k<positionList.size();k++){
            positionList2.add(positionList.get(positionList.size()-k-1));
        }
        return positionList2;
    }

    private static List<Position> ordered(List<Position> intersectionsPosition, Segment segment, Position aPosition) {
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
    public static boolean positionIsInTheRectangle(Position pointA, Position seaEntitiesPos, Rectangle rectangle) {
        List<Position> cornersList = computeCorner(seaEntitiesPos, rectangle);
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

    public static List<Beacon> generateBeacon(Position aPosition, Rectangle rectangle, boolean isAReef) {
        double safetyLength = 150;
        rectangle=new Rectangle(rectangle.getWidth()+safetyLength,rectangle.getHeight()+safetyLength,rectangle.getOrientation());
        if (isAReef)
            return generateBeaconForReef(aPosition,rectangle);
        else
            return generateBeaconforStream(aPosition,rectangle);
    }


    private static List<Beacon> generateBeaconForReef(Position aPosition, Rectangle rectangle) {
        List<Beacon> listOfPosition=new ArrayList<>();

        double width = rectangle.getWidth();
        double height = rectangle.getHeight();

        listOfPosition.add(new Beacon(Mathematician.changeBase(aPosition,-height / 2,-width / 2)));
        listOfPosition.add(new Beacon(Mathematician.changeBase(aPosition,height / 2,width / 2)));
        listOfPosition.add(new Beacon(Mathematician.changeBase(aPosition,-height / 2,width / 2)));
        listOfPosition.add(new Beacon(Mathematician.changeBase(aPosition,height / 2,-width / 2)));

        return listOfPosition;
    }

    public static List<Beacon> generateBeaconforStream(Position aPosition, Rectangle rectangle) {
        double width = rectangle.getWidth();
        double height = rectangle.getHeight();
        List<Beacon> listOfPosition=new ArrayList<>();
        int precision=4;
        double widthUnit=width/ precision;
        double heightUnit=height/ precision;
        for (int k = 0 ; k< precision; k++){
            listOfPosition.add(new Beacon(Mathematician.changeBase(aPosition,-width/2,-height/2+k*heightUnit)));
            listOfPosition.add(new Beacon(Mathematician.changeBase(aPosition,width/2,height/2-k*heightUnit)));
            listOfPosition.add(new Beacon(Mathematician.changeBase(aPosition,-width/2+k*widthUnit,height/2)));
            listOfPosition.add(new Beacon(Mathematician.changeBase(aPosition,width/2-k*widthUnit,-height/2)));}
        return listOfPosition;
    }


}
