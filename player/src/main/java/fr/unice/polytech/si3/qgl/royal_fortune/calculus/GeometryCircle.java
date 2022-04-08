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

        double[] vectorShipCheckpoint = computeDirectorAndNormalVectorsShipCheckpoint(shipPosition, checkpointPosition);
        double normalVectorX = vectorShipCheckpoint[0];
        double normalVectorY = vectorShipCheckpoint[1];
        double directorVectorX = -vectorShipCheckpoint[1];
        double directorVectorY = vectorShipCheckpoint[0];
        double circleRadius = reefShape.getRadius();

        createRightBeaconUsingCircleReef(reefPosition, directorVectorX, directorVectorY, circleRadius, beaconList);
        createLeftBeaconUsingCircleReef(reefPosition, directorVectorX, directorVectorY, circleRadius, beaconList);

        createUpBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, beaconList);
        createDownBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, beaconList);

        return beaconList;
    }

    /**
     * Compute the director and normal vectors on the trajectory ship - checkpoint
     * @param shipPosition
     * @param checkpointPosition
     * @return a tab of double of size 4, having directorVectorX, directorVectorY, normalVectorX and normalVectorY (in that order)
     */
    public static double[] computeDirectorAndNormalVectorsShipCheckpoint(Position shipPosition, Position checkpointPosition){
        double directorVectorShipCheckpointX = checkpointPosition.getX() - shipPosition.getX();
        double directorVectorShipCheckpointY = checkpointPosition.getY() - shipPosition.getY();
        double normShipCheckpoint = Math.sqrt(directorVectorShipCheckpointX * directorVectorShipCheckpointX + directorVectorShipCheckpointY * directorVectorShipCheckpointY);

        double normalVectorX = -directorVectorShipCheckpointY / normShipCheckpoint;
        double normalVectorY = directorVectorShipCheckpointX / normShipCheckpoint;
        return new double[]{directorVectorShipCheckpointX, directorVectorShipCheckpointY, normalVectorX, normalVectorY};
    }

    /**
     * Create a beacon at the right of the reef with a circle shape
     * @param reefPosition position of a reef
     * @param directorVectorX x component of the director vector (director of the trajectory ship - checkpoint)
     * @param directorVectorY y component of the director vector (director of the trajectory ship - checkpoint)
     * @param circleRadius radius of the reef
     * @return a beacon place at the right of the circle reef
     */
    public static void createRightBeaconUsingCircleReef(Position reefPosition, double directorVectorX, double directorVectorY, double circleRadius, List<Beacon> beaconList) {
        double rightBeaconX = reefPosition.getX() + directorVectorX * (circleRadius + Beacon.RADIUSBEACON);
        double rightBeaconY = reefPosition.getY() + directorVectorY * (circleRadius + Beacon.RADIUSBEACON);
        Beacon rightBeacon = new Beacon(new Position(rightBeaconX, rightBeaconY));
        beaconList.add(rightBeacon);
    }

    /**
     * Create a beacon at the left of the reef with a circle shape
     * @param reefPosition position of a reef
     * @param directorVectorX x component of the director vector (director of the trajectory ship - checkpoint)
     * @param directorVectorY y component of the director vector (director of the trajectory ship - checkpoint)
     * @param circleRadius radius of the reef
     * @return a beacon place at the left of the circle reef
     */
    public static void createLeftBeaconUsingCircleReef(Position reefPosition, double directorVectorX, double directorVectorY, double circleRadius, List<Beacon> beaconList) {
        double leftBeaconX = reefPosition.getX() - directorVectorX * (circleRadius + Beacon.RADIUSBEACON);
        double leftBeaconY = reefPosition.getY() - directorVectorY * (circleRadius + Beacon.RADIUSBEACON);
        Beacon leftBeacon = new Beacon(new Position(leftBeaconX, leftBeaconY));
        beaconList.add(leftBeacon);
    }

    /**
     * Create a beacon at the top of the reef with a circle shape
     * @param reefPosition position of a reef
     * @param normalVectorX x component of the normal vector (normal of the trajectory ship - checkpoint)
     * @param normalVectorY y component of the normal vector (normal of the trajectory ship - checkpoint)
     * @param circleRadius radius of the reef
     * @return a beacon place at the top of the circle reef
     */
    public static void createUpBeaconUsingCircleReef(Position reefPosition, double normalVectorX, double normalVectorY, double circleRadius, List<Beacon> beaconList){
        double upBeaconX = reefPosition.getX() + normalVectorX * (circleRadius + Beacon.RADIUSBEACON);
        double upBeaconY = reefPosition.getY() + normalVectorY * (circleRadius + Beacon.RADIUSBEACON);
        Beacon upBeacon = new Beacon(new Position(upBeaconX, upBeaconY));
        beaconList.add(upBeacon);
    }

    /**
     * Create a beacon at the bottom of the reef with a circle shape
     * @param reefPosition position of a reef
     * @param normalVectorX x component of the normal vector (normal of the trajectory ship - checkpoint)
     * @param normalVectorY y component of the normal vector (normal of the trajectory ship - checkpoint)
     * @param circleRadius radius of the reef
     * @return a beacon place at the bottom of the circle reef
     */
    public static void createDownBeaconUsingCircleReef(Position reefPosition, double normalVectorX, double normalVectorY, double circleRadius, List<Beacon> beaconList){
        double downBeaconX = reefPosition.getX() - normalVectorX * (circleRadius + Beacon.RADIUSBEACON);
        double downBeaconY = reefPosition.getY() - normalVectorY * (circleRadius + Beacon.RADIUSBEACON);
        Beacon downBeacon = new Beacon(new Position(downBeaconX, downBeaconY));
        beaconList.add(downBeacon);
    }

    /**
     * Check if there are intersections between a given circle and line (from a segment)
     * @param circle a circle
     * @param segment a segment
     * @return the list of the intersections (2 intersections, 1 intersection or empty)
     */
    public static List<Position> computeIntersectionWith(Segment segment, Position circlePosition, Circle circle) {
        List<Position> intersectionList = new ArrayList<>();
        Position pointASave = segment.getPointA();
        Position pointBSave = segment.getPointB();

        segment = new Segment(segment.getPointA(),segment.getPointB());
        segment.setPointA(new Position(segment.getPointA().getX(),segment.getPointA().getY()));
        segment.setPointB(new Position(segment.getPointB().getX(),segment.getPointB().getY()));

        segment.getPointA().setX(segment.getPointA().getX() - circlePosition.getX());
        segment.getPointB().setX(segment.getPointB().getX() - circlePosition.getX());

        segment.getPointA().setY(segment.getPointA().getY() - circlePosition.getY());
        segment.getPointB().setY(segment.getPointB().getY() - circlePosition.getY());

        segment = new Segment(segment.getPointA(), segment.getPointB());

        double x=circlePosition.getX();
        double y=circlePosition.getY();
        double radius = circle.getRadius();
        double a = segment.getA();
        double b = segment.getB();

        double discriminant = 4 * Math.pow(a, 2) * Math.pow(b, 2) - 4 * (Math.pow(a, 2) + 1) * (Math.pow(b, 2) - Math.pow(radius, 2));

        if(discriminant > 0) {
            double firstSolution = (-2 * a * b + Math.sqrt(discriminant)) / (2 * (Math.pow(a, 2) + 1));
            double secondSolution = (-2 * a * b - Math.sqrt(discriminant)) / (2 * (Math.pow(a, 2) + 1));

            Position position1 = new Position(firstSolution, a*firstSolution+b);
            Position position2 = new Position(secondSolution, a*secondSolution+b);
            Position position1real = new Position(firstSolution+x, a*firstSolution+b+y);
            Position position2real = new Position(secondSolution+x, a*secondSolution+b+y);
            if(segment.pointInSegment(position1)&&segment.pointInSegment(position2)){
                if (Mathematician.distanceFormula(position1,pointASave)>Mathematician.distanceFormula(position2,pointASave))
                {
                    if (!pointASave.equals(position1real) && !pointBSave.equals(position1real))
                        intersectionList.add(position1real);
                    if (!pointASave.equals(position2real) && !pointBSave.equals(position2real))
                        intersectionList.add(position2real);
                }
                else
                {
                    if (!pointASave.equals(position2real) && !pointBSave.equals(position2real))
                        intersectionList.add(position2real);
                    if (!pointASave.equals(position1real) && !pointBSave.equals(position1real))
                        intersectionList.add(position1real);
                }}
        }

        else if (discriminant == 0) {
            double onlySolution = (-2 * a * b) / (2 * (Math.pow(a, 2) + 1));
            Position position = new Position(onlySolution, a*onlySolution+b);
            Position realposition = new Position(onlySolution+x, a*onlySolution+b+y);

            if(segment.pointInSegment(position) && !pointASave.equals(realposition) && !pointBSave.equals(realposition)) {
                intersectionList.add(new Position(onlySolution + x, a * onlySolution + b + y));
            }
        }

        return intersectionList;
    }
}