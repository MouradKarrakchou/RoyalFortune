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
        double directorVectorX = vectorShipCheckpoint[0];
        double directorVectorY = vectorShipCheckpoint[1];
        double normalVectorX = vectorShipCheckpoint[2];
        double normalVectorY = vectorShipCheckpoint[3];
        double circleRadius = reefShape.getRadius();

        createRightBeaconUsingCircleReef(reefPosition, directorVectorX, directorVectorY, circleRadius, beaconList);
        createLeftBeaconUsingCircleReef(reefPosition, directorVectorX, directorVectorY, circleRadius, beaconList);

        createUpBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, beaconList);
        createDownBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, circleRadius, beaconList);

        return beaconList;
    }

    /**
     * Compute the director and normal vectors on the trajectory ship - checkpoint
     * @param shipPosition ship position
     * @param checkpointPosition checkpoint position
     * @return a tab of double of size 4, having directorVectorX, directorVectorY, normalVectorX and normalVectorY (in that order)
     */
    public static double[] computeDirectorAndNormalVectorsShipCheckpoint(Position shipPosition, Position checkpointPosition){
        double directorVectorShipCheckpointX = checkpointPosition.getX() - shipPosition.getX();
        double directorVectorShipCheckpointY = checkpointPosition.getY() - shipPosition.getY();
        double normShipCheckpoint = Math.sqrt(directorVectorShipCheckpointX * directorVectorShipCheckpointX + directorVectorShipCheckpointY * directorVectorShipCheckpointY);

        double directorVectorNormedShipCheckpointX = directorVectorShipCheckpointX / normShipCheckpoint;
        double directorVectorNormedShipCheckpointY = directorVectorShipCheckpointY / normShipCheckpoint;

        return new double[] {directorVectorNormedShipCheckpointX, directorVectorNormedShipCheckpointY, -directorVectorNormedShipCheckpointY, directorVectorNormedShipCheckpointX};
    }

    /**
     * Create a beacon at the right of the reef with a circle shape
     * @param reefPosition position of a reef
     * @param directorVectorX x component of the director vector (director of the trajectory ship - checkpoint)
     * @param directorVectorY y component of the director vector (director of the trajectory ship - checkpoint)
     * @param circleRadius radius of the reef
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

        Segment segmentToWorkOn = segmentToWorkOn(segment, circlePosition);

        double circlePositionX = circlePosition.getX();
        double circlePositionY = circlePosition.getY();
        double radius = circle.getRadius();
        double segmentToWorkOnA = segmentToWorkOn.getA();
        double segmentToWorkOnB = segmentToWorkOn.getB();

        double discriminant = discriminant(segmentToWorkOnA, segmentToWorkOnB, radius);

        discriminantValue(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant, circlePositionX, circlePositionY, intersectionList);

        return intersectionList;
    }

    /**
     * Since we did our calculus considering the circle at the center of the map, we need to move the segment at
     * the position it would be if the circle was really at the center of the map.
     * Return a segment on which we can work in order not to destroy the real one we need
     * @param segment the segment we work on
     * @param circlePosition the circle position
     * @return a segment we can work on
     */
    static Segment segmentToWorkOn(Segment segment, Position circlePosition) {
        segment = new Segment(segment.getPointA(), segment.getPointB());

        segment.setPointA(new Position(segment.getPointA().getX(), segment.getPointA().getY()));
        segment.setPointB(new Position(segment.getPointB().getX(), segment.getPointB().getY()));

        segment.getPointA().setX(segment.getPointA().getX() - circlePosition.getX());
        segment.getPointB().setX(segment.getPointB().getX() - circlePosition.getX());

        segment.getPointA().setY(segment.getPointA().getY() - circlePosition.getY());
        segment.getPointB().setY(segment.getPointB().getY() - circlePosition.getY());

        segment = new Segment(segment.getPointA(), segment.getPointB());

        return segment;
    }

    /**
     * Discriminant of a quadratic equation
     * @param segmentToWorkOnA constant value of the quadratic equation
     * @param segmentToWorkOnB constant value of the quadratic equation
     * @param radius constant value of the quadratic equation
     * @return the positive, zero or negative value of the discriminant
     */
    static double discriminant(double segmentToWorkOnA, double segmentToWorkOnB, double radius) {
        return 4 * Math.pow(segmentToWorkOnA, 2) * Math.pow(segmentToWorkOnB, 2) - 4 * (Math.pow(segmentToWorkOnA, 2) + 1) * (Math.pow(segmentToWorkOnB, 2) - Math.pow(radius, 2));
    }

    /**
     * Call the right method to add the intersections points considering the discriminant value
     * @param segmentToWorkOn segment on which we can work
     * @param segmentToWorkOnA value of the line equation ax+b
     * @param segmentToWorkOnB value of the line equation ax+b
     * @param pointASave a save of the 'A' position of the segment
     * @param pointBSave a save of the 'B' position of the segment
     * @param discriminant discriminant value
     * @param circlePositionX x circle position
     * @param circlePositionY y circle position
     * @param intersectionList intersection List
     */
    static void discriminantValue(Segment segmentToWorkOn, double segmentToWorkOnA, double segmentToWorkOnB, Position pointASave, Position pointBSave, double discriminant, double circlePositionX, double circlePositionY, List<Position> intersectionList) {
        if(discriminant > 0) {
            positiveDiscriminant(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, discriminant, circlePositionX, circlePositionY, intersectionList);
        }

        else if (discriminant == 0) {
            zeroDiscriminant(segmentToWorkOn, segmentToWorkOnA, segmentToWorkOnB, pointASave, pointBSave, circlePositionX, circlePositionY, intersectionList);
        }
    }

    /**
     * Add the two intersections points to the intersectionList
     * @param segmentToWorkOn segment on which we can work
     * @param segmentToWorkOnA value of the line equation ax+b
     * @param segmentToWorkOnB value of the line equation ax+b
     * @param pointASave a save of the 'A' position of the segment
     * @param pointBSave a save of the 'B' position of the segment
     * @param discriminant discriminant value
     * @param circlePositionX x circle position
     * @param circlePositionY y circle position
     * @param intersectionList intersection List
     */
    public static void positiveDiscriminant(Segment segmentToWorkOn, double segmentToWorkOnA, double segmentToWorkOnB, Position pointASave, Position pointBSave, double discriminant, double circlePositionX, double circlePositionY, List<Position> intersectionList) {
        double firstSolution = (-2 * segmentToWorkOnA * segmentToWorkOnB + Math.sqrt(discriminant)) / (2 * (Math.pow(segmentToWorkOnA, 2) + 1));
        double secondSolution = (-2 * segmentToWorkOnA * segmentToWorkOnB - Math.sqrt(discriminant)) / (2 * (Math.pow(segmentToWorkOnA, 2) + 1));

        Position position1 = new Position(firstSolution, segmentToWorkOnA*firstSolution+segmentToWorkOnB);
        Position position2 = new Position(secondSolution, segmentToWorkOnA*secondSolution+segmentToWorkOnB);
        Position position1real = new Position(firstSolution+circlePositionX, segmentToWorkOnA*firstSolution+segmentToWorkOnB+circlePositionY);
        Position position2real = new Position(secondSolution+circlePositionX, segmentToWorkOnA*secondSolution+segmentToWorkOnB+circlePositionY);

        boolean conditionWithPosition1real = !pointASave.equals(position1real) && !pointBSave.equals(position1real);
        boolean conditionWithPosition2real = !pointASave.equals(position2real) && !pointBSave.equals(position2real);
        boolean Position1Position2InSegment = segmentToWorkOn.pointInSegment(position1) && segmentToWorkOn.pointInSegment(position2);
        boolean conditionOnDistances = Mathematician.distanceFormula(position1,pointASave) > Mathematician.distanceFormula(position2,pointASave);

        if(Position1Position2InSegment && conditionOnDistances) {
            if (conditionWithPosition1real) intersectionList.add(position1real);

            if (conditionWithPosition2real) intersectionList.add(position2real);
        }

        else if(Position1Position2InSegment){
                if (conditionWithPosition2real) intersectionList.add(position2real);

                if (conditionWithPosition1real) intersectionList.add(position1real);
        }
    }

    /**
     * Add the only intersection point to the intersectionList
     * @param segmentToWorkOn segment on which we can work
     * @param segmentToWorkOnA value of the line equation ax+b
     * @param segmentToWorkOnB value of the line equation ax+b
     * @param pointASave a save of the 'A' position of the segment
     * @param pointBSave a save of the 'B' position of the segment
     * @param circlePositionX x circle position
     * @param circlePositionY y circle position
     * @param intersectionList intersection List
     */
    public static void zeroDiscriminant(Segment segmentToWorkOn, double segmentToWorkOnA, double segmentToWorkOnB, Position pointASave, Position pointBSave, double circlePositionX, double circlePositionY, List<Position> intersectionList) {
        double onlySolution = (-2 * segmentToWorkOnA * segmentToWorkOnB) / (2 * (Math.pow(segmentToWorkOnA, 2) + 1));
        Position position = new Position(onlySolution, segmentToWorkOnA * onlySolution + segmentToWorkOnB);
        Position realPosition = new Position(onlySolution + circlePositionX, segmentToWorkOnA * onlySolution + segmentToWorkOnB + circlePositionY);

        if(segmentToWorkOn.pointInSegment(position) && !pointASave.equals(realPosition) && !pointBSave.equals(realPosition)) {
            intersectionList.add(new Position(onlySolution + circlePositionX, segmentToWorkOnA * onlySolution + segmentToWorkOnB + circlePositionY));
        }
    }
}