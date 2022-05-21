package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Polygone;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public static List<Position> computeIntersectionWith(Segment segment, Position seaEntitiesPos, List<Segment> segmentList){
        return GeometryRectangle.computeIntersectionWith(segment,seaEntitiesPos,segmentList);
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
                    currentPoint.getX() + centerPointUnitVector.x * 160,
                    currentPoint.getY() + centerPointUnitVector.y * 160)
            ));
        }

        return generatedBeacons;
    }
}
