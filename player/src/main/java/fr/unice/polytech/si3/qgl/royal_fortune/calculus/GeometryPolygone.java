package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Polygone;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fr.unice.polytech.si3.qgl.royal_fortune.Cockpit.SECURITY_UPSCALE;

public class GeometryPolygone {

    private GeometryPolygone() {}

    /**
     * Compute all the segments of the polygon
     * @return a list that contain all the segments of the polygon
     */
    public static List<Segment> computeSegments(Point[] vertices) {
        List<Segment> polygonSegment = new ArrayList<>();

        int cornersNumber = vertices.length;
        for(int i = 0; i < cornersNumber - 1; i++) {
            Point startSegment = vertices[i];
            Point arrivalSegment = vertices[i+1];

            polygonSegment.add(new Segment(startSegment, arrivalSegment));
        }

        int lastElementIndex = cornersNumber - 1;
        polygonSegment.add(new Segment(vertices[lastElementIndex], vertices[0]));

        return polygonSegment;
    }


    /**
     * Compute the intersection between the current shape and a segment
     * @param segment a segment
     * @return the 2 positions of the intersection
     */
    public static List<Position> computeIntersectionWith(Segment segment, Position seaEntitiesPos, Polygone polygon){
        List<Segment> segmentList = polygon.getSegmentList();
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
     * Generate a Beacon in each corner
     * @param position
     * @param polygone
     * @return
     */
    public static List<Beacon> generateBeacon(Position position, Polygone polygone) {
        Point[] vertices = polygone.getVertices();
        List<Beacon> generatedBeacons = new ArrayList<>();

        for (Point currentPoint : vertices) {
            Vector tmp = new Vector(position, new Position(currentPoint.x, currentPoint.y));
            Vector centerPointUnitVector = tmp.unitVector();

            generatedBeacons.add(new Beacon(new Position(
                    currentPoint.getX() + centerPointUnitVector.x * SECURITY_UPSCALE,
                    currentPoint.getY() + centerPointUnitVector.y * SECURITY_UPSCALE)
            ));
        }

        return generatedBeacons;
    }
}
