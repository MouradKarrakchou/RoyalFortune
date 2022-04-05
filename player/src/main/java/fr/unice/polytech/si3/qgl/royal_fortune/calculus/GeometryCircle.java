package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.ArrayList;
import java.util.List;

public class GeometryCircle {
    public GeometryCircle() {
        //No elements needed
    }

    /**
     * check if the rectangle is in the circle
     * @return true if the rectangle is the circle
     */
    public static boolean rectangleIsInCircle(Rectangle rectangle, Position currentPos, double radius){
        List<Position> positionList= GeometryRectangle.computeCorner(currentPos, rectangle);
        for(Position position:positionList){
            if(Mathematician.distanceFormula(currentPos,position)<=radius)
                return true;
        }
        return false;
    }

    /**
     * Generates two beacons on the circle, one at the opposite of the other:
     * the line between the two beacons is perpendicular to the line of the trajectory from the ship to the checkpoint
     * @param shipPosition ship position
     * @param checkpointPosition checkpoint position
     * @param reefPosition reef position
     * @param reefShape reef position
     * @return the list containing the two beacons
     */
        public static List<Beacon> generateBeacon(Position shipPosition, Position checkpointPosition, Position reefPosition, Circle reefShape){
        List<Beacon> beaconList = new ArrayList<>();
        double[] vectorShipCheckpoint = computeNormalVectorShipCheckpoint(shipPosition, checkpointPosition);
        double normalVectorX = vectorShipCheckpoint[0];
        double normalVectorY = vectorShipCheckpoint[1];

        Beacon upBeacon = createUpBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, reefShape.getRadius());
        Beacon downBeacon = createDownBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, reefShape.getRadius());

        placeBeaconsAroundReef(upBeacon.getPosition(), downBeacon.getPosition(), normalVectorX, normalVectorY, beaconList);
        return beaconList;
    }

    /**
     *
     * @param shipPosition
     * @param checkpointPosition
     * @return a tab of double of size 2, at index 0 their is normalVectorX et at index 1 normalVectorY
     */
    public static double[] computeNormalVectorShipCheckpoint(Position shipPosition, Position checkpointPosition){
        double vectorShipCheckpointX = checkpointPosition.getX() - shipPosition.getX();
        double vectorShipCheckpointY = checkpointPosition.getY() - shipPosition.getY();
        double normShipCheckpoint = Math.sqrt(vectorShipCheckpointX * vectorShipCheckpointX + vectorShipCheckpointY * vectorShipCheckpointY);

        double normalVectorX = -vectorShipCheckpointY / normShipCheckpoint;
        double normalVectorY = vectorShipCheckpointX / normShipCheckpoint;
        return new double[]{normalVectorX, normalVectorY};
    }

    /**
     * Create a beacon at the top of the reef with a circle shape
     * @param reefPosition
     * @param normalVectorX
     * @param normalVectorY
     * @param circleRadius
     * @return a beacon place at the top of the circle reef
     */
    public static Beacon createUpBeaconUsingCircleReef(Position reefPosition, double normalVectorX, double normalVectorY, double circleRadius){
        double upBeaconX = reefPosition.getX() + normalVectorX * (0.5*circleRadius + 0.5*Beacon.RADIUSBEACON);
        double upBeaconY = reefPosition.getY() + normalVectorY * (0.5*circleRadius + 0.5*Beacon.RADIUSBEACON);
        return new Beacon(new Position(upBeaconX, upBeaconY));
    }

    /**
     * Create a beacon at the bottom of the reef with a circle shape
     * @param reefPosition
     * @param normalVectorX
     * @param normalVectorY
     * @param circleRadius
     * @return a beacon place at the bottom of the circle reef
     */
    public static Beacon createDownBeaconUsingCircleReef(Position reefPosition, double normalVectorX, double normalVectorY, double circleRadius){
        double downBeaconX = reefPosition.getX() - normalVectorX * (0.5*circleRadius + 0.5*Beacon.RADIUSBEACON);
        double downBeaconY = reefPosition.getY() - normalVectorY * (0.5*circleRadius + 0.5*Beacon.RADIUSBEACON);
        return new Beacon(new Position(downBeaconX, downBeaconY));
    }

    /**
     * Place all around the reef some Beacons
     * @param upBeacon
     * @param downBeacon
     * @param normalVectorX
     * @param normalVectorY
     * @param beaconList
     */
    public static void placeBeaconsAroundReef(Position upBeacon, Position downBeacon, double normalVectorX, double normalVectorY, List<Beacon> beaconList){

        double upBeaconX = upBeacon.getX();
        double upBeaconY = upBeacon.getY();

        double downBeaconX = downBeacon.getX();
        double downBeaconY = downBeacon.getY();

        upBeaconX += normalVectorX * 0.1*Beacon.RADIUSBEACON ;
        upBeaconY += normalVectorY * 0.1*Beacon.RADIUSBEACON;
        beaconList.add(new Beacon(new Position(upBeaconX, upBeaconY)));

        downBeaconX -= normalVectorX * 0.1*Beacon.RADIUSBEACON;
        downBeaconY -= normalVectorY * 0.1*Beacon.RADIUSBEACON;
        beaconList.add(new Beacon(new Position(downBeaconX, downBeaconY)));
    }

    /**
     * Check if there are intersections between a given circle and line (from a segment)
     * @param circle a circle
     * @param initialSegment a segment
     * @return the list of the intersections (2 intersections, 1 intersection or empty)
     */
    public static List<Position> computeIntersectionWith(Segment initialSegment, Position circlePosition, Circle circle) {
        List<Position> intersectionList = new ArrayList<>();

        Segment segmentTranslated = cloneSegment(initialSegment);
        segmentTranslated = translateSegmentToCenter(segmentTranslated, circlePosition);


        Position saveCirclePosition = new Position(circlePosition.getX(), circlePosition.getY());
        double radius = circle.getRadius();

        double discriminant = 4 * Math.pow(segmentTranslated.getA(), 2) * Math.pow(segmentTranslated.getB(), 2) - 4 * (Math.pow(segmentTranslated.getA(), 2) + 1) * (Math.pow(segmentTranslated.getB(), 2) - Math.pow(radius, 2));


        Position initialSegmentPointA = segmentTranslated.getPointA();
        Position initialSegmentPointB = segmentTranslated.getPointB();


        if(discriminant > 0) {
            doubleIntersections(segmentTranslated, saveCirclePosition, initialSegmentPointA, initialSegmentPointB, intersectionList, discriminant);
        }
        else if (discriminant == 0) {
            singleIntersection(segmentTranslated, saveCirclePosition, initialSegmentPointA, initialSegmentPointB, intersectionList);
        }

        return intersectionList;
    }

    /**
     *
     * @param segment
     * @return
     */
    public static Segment cloneSegment(Segment segment){
        segment=new Segment(segment.getPointA(),segment.getPointB());
        segment=new Segment(segment.getPointA(),segment.getPointB());
        segment.setPointA(new Position(segment.getPointA().getX(),segment.getPointA().getY()));
        segment.setPointB(new Position(segment.getPointB().getX(),segment.getPointB().getY()));
        return segment;
    }

    public static Segment translateSegmentToCenter(Segment segment, Position circlePosition){
        segment.getPointA().setX(segment.getPointA().getX() - circlePosition.getX());
        segment.getPointB().setX(segment.getPointB().getX() - circlePosition.getX());

        segment.getPointA().setY(segment.getPointA().getY() - circlePosition.getY());
        segment.getPointB().setY(segment.getPointB().getY() - circlePosition.getY());

       return new Segment(segment.getPointA(), segment.getPointB());
    }

    public static void singleIntersection(Segment segment, Position saveCirclePosition, Position pointASave, Position pointBSave, List<Position> intersectionList){
        double x = saveCirclePosition.getX();
        double y = saveCirclePosition.getY();
        double a = segment.getA();
        double b = segment.getB();
        double onlySolution = (-2 * a * b) / (2 * (Math.pow(a, 2) + 1));

        Position position = new Position(onlySolution, a*onlySolution+b);
        Position realposition = new Position(onlySolution+x, a*onlySolution+b+y);

        if(segment.pointInSegment(position)) {
            if (!pointASave.equals(realposition) && !pointBSave.equals(realposition))
                intersectionList.add(new Position(onlySolution + x, a * onlySolution + b + y));
        }
    }
    public static void doubleIntersections( Segment segmentTranslated, Position saveCirclePosition, Position initialSegmentPointA, Position initialSegmentPointB, List<Position> intersectionList, double discriminant){
        double a = segmentTranslated.getA();
        double b = segmentTranslated.getB();
        double firstSolution = (-2 * a * b + Math.sqrt(discriminant)) / (2 * (Math.pow(a, 2) + 1));
        double secondSolution = (-2 * a * b - Math.sqrt(discriminant)) / (2 * (Math.pow(a, 2) + 1));

        Position position1 = new Position(firstSolution, a*firstSolution+b);
        Position position2 = new Position(secondSolution, a*secondSolution+b);
        Position position1real=new Position(firstSolution+saveCirclePosition.getX(), a*firstSolution+b+saveCirclePosition.getY());
        Position position2real=new Position(secondSolution+saveCirclePosition.getX(), a*secondSolution+b+saveCirclePosition.getY());
        if(segmentTranslated.pointInSegment(position1)&&segmentTranslated.pointInSegment(position2)){
            if (Mathematician.distanceFormula(position1,initialSegmentPointA)>Mathematician.distanceFormula(position2,initialSegmentPointA))
            {
                if (!initialSegmentPointA.equals(position1real)&&!initialSegmentPointB.equals(position1real))
                    intersectionList.add(position1real);
                if (!initialSegmentPointA.equals(position2real)&&!initialSegmentPointB.equals(position2real))
                    intersectionList.add(position2real);
            }
            else
            {
                if (!initialSegmentPointA.equals(position2real)&&!initialSegmentPointB.equals(position2real))
                    intersectionList.add(position2real);
                if (!initialSegmentPointA.equals(position1real)&&!initialSegmentPointB.equals(position1real))
                    intersectionList.add(position1real);
            }
        }
    }

    }
