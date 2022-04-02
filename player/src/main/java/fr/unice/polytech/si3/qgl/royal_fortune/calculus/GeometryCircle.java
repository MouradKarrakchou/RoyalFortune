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
        double securityScaling = 0;
        List<Beacon> beaconList = new ArrayList<>();

        double vectorShipCheckpointX = checkpointPosition.getX() - shipPosition.getX();
        double vectorShipCheckpointY = checkpointPosition.getY() - shipPosition.getY();
        double normShipCheckpoint = Math.sqrt(vectorShipCheckpointX * vectorShipCheckpointX + vectorShipCheckpointY * vectorShipCheckpointY);

        double normalVectorX = -vectorShipCheckpointY / normShipCheckpoint;
        double normalVectorY = vectorShipCheckpointX / normShipCheckpoint;

        Beacon upBeacon = createUpBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, securityScaling, reefShape.getRadius());
        Beacon downBeacon = createDownBeaconUsingCircleReef(reefPosition, normalVectorX, normalVectorY, securityScaling, reefShape.getRadius());
        beaconList.add(upBeacon);
        beaconList.add(downBeacon);
        placeBeaconsAroundReef(upBeacon.getPosition(), downBeacon.getPosition(),normalVectorX, normalVectorY, beaconList);

        return beaconList;
    }



    /**
     * Create a beacon at the top of the reef with a circle shape
     * @param reefPosition
     * @param normalVectorX
     * @param normalVectorY
     * @param securityScaling
     * @param circleRadius
     * @return a beacon place at the top of the circle reef
     */
    public static Beacon createUpBeaconUsingCircleReef(Position reefPosition, double normalVectorX, double normalVectorY, double securityScaling, double circleRadius){
        double upBeaconX = reefPosition.getX() + normalVectorX * (securityScaling + circleRadius/2+ Beacon.RADIUSBEACON);
        double upBeaconY = reefPosition.getY() + normalVectorY * (securityScaling + circleRadius/2+ Beacon.RADIUSBEACON);
        return new Beacon(new Position(upBeaconX, upBeaconY));
    }

    /**
     * Create a beacon at the bottom of the reef with a circle shape
     * @param reefPosition
     * @param normalVectorX
     * @param normalVectorY
     * @param securityScaling
     * @param circleRadius
     * @return a beacon place at the bottom of the circle reef
     */
    public static Beacon createDownBeaconUsingCircleReef(Position reefPosition, double normalVectorX, double normalVectorY, double securityScaling, double circleRadius){
        double downBeaconX = reefPosition.getX() - normalVectorX * (securityScaling + circleRadius/2+ Beacon.RADIUSBEACON);
        double downBeaconY = reefPosition.getY() - normalVectorY * (securityScaling + circleRadius/2+ Beacon.RADIUSBEACON);
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

        int alignedBeacons = 2;
        double upBeaconX = upBeacon.getX();
        double upBeaconY = upBeacon.getY();
        double downBeaconX = downBeacon.getX();
        double downBeaconY = downBeacon.getY();

        for (int i = 0; i < alignedBeacons; i++){
            upBeaconX += 0.5 * normalVectorX * Beacon.RADIUSBEACON ;
            upBeaconY += 0.5 * normalVectorY * Beacon.RADIUSBEACON;

            beaconList.add(new Beacon(new Position(upBeaconX, upBeaconY)));

            downBeaconX -= 0.5 * normalVectorX * Beacon.RADIUSBEACON;
            downBeaconY -= 0.5 * normalVectorY * Beacon.RADIUSBEACON;
            beaconList.add(new Beacon(new Position(downBeaconX, downBeaconY)));
        }
    }

    /**
     * Check if there are intersections between a given circle and line (from a segment)
     * @param circle a circle
     * @param segment a segment
     * @return the list of the intersections (2 intersections, 1 intersection or empty)
     */
    public static List<Position> computeIntersectionWith(Segment segment, Position circlePosition, Circle circle) {
        List<Position> intersectionList = new ArrayList<>();
        Position pointASave=segment.getPointA();
        Position pointBSave=segment.getPointB();

        segment=new Segment(segment.getPointA(),segment.getPointB());
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
            Position position1real=new Position(firstSolution+x, a*firstSolution+b+y);
            Position position2real=new Position(secondSolution+x, a*secondSolution+b+y);
            if(segment.pointInSegment(position1)&&segment.pointInSegment(position2)){
                if (Mathematician.distanceFormula(position1,pointASave)>Mathematician.distanceFormula(position2,pointASave))
                {
                    if (!pointASave.equals(position1real)&&!pointBSave.equals(position1real))
                        intersectionList.add(position1real);
                    if (!pointASave.equals(position2real)&&!pointBSave.equals(position2real))
                        intersectionList.add(position2real);
                }
                else
                {
                    if (!pointASave.equals(position2real)&&!pointBSave.equals(position2real))
                        intersectionList.add(position2real);
                    if (!pointASave.equals(position1real)&&!pointBSave.equals(position1real))
                        intersectionList.add(position1real);
                }}
        }

        else if (discriminant == 0) {
            double onlySolution = (-2 * a * b) / (2 * (Math.pow(a, 2) + 1));
            Position position = new Position(onlySolution, a*onlySolution+b);
            Position realposition = new Position(onlySolution+x, a*onlySolution+b+y);

            if(segment.pointInSegment(position)) {
                if (!pointASave.equals(realposition) && !pointBSave.equals(realposition))
                    intersectionList.add(new Position(onlySolution + x, a * onlySolution + b + y));
            }
        }

        return intersectionList;
    }
}
