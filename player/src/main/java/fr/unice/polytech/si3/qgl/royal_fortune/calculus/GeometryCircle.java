package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.ArrayList;
import java.util.List;

public class GeometryCircle {
    private final double beaconRadius = 50;
    private final double securityScaling = 20;
    private final int alignedBeacons = 3;

    public GeometryCircle() {}

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
    public List<Beacon> generateBeacon(Position shipPosition, Position checkpointPosition, Position reefPosition, Circle reefShape){
        List<Beacon> beaconList = new ArrayList<>();

        double vectorSheepCheckpointX = checkpointPosition.getX() - shipPosition.getX();
        double vectorSheepCheckpointY = checkpointPosition.getY() - shipPosition.getY();
        double normSheepCheckpoint = Math.sqrt(vectorSheepCheckpointX * vectorSheepCheckpointX + vectorSheepCheckpointY * vectorSheepCheckpointY);

        double normalVectorX = -vectorSheepCheckpointY / normSheepCheckpoint;
        double normalVectorY = vectorSheepCheckpointX / normSheepCheckpoint;

        double upBeaconX = reefPosition.getX() + normalVectorX * (securityScaling + reefShape.getRadius());
        double upBeaconY = reefPosition.getY() + normalVectorY * (securityScaling + reefShape.getRadius());
        double downBeaconX = reefPosition.getX() - normalVectorX * (securityScaling + reefShape.getRadius());;
        double downBeaconY = reefPosition.getY() - normalVectorY * (securityScaling + reefShape.getRadius());;

        for (int i = 0; i<alignedBeacons; i++){
            upBeaconX += normalVectorX * beaconRadius;
            upBeaconY += normalVectorY *  beaconRadius;

            beaconList.add(new Beacon(new Position(upBeaconX, upBeaconY), new Circle(beaconRadius)));

            downBeaconX -= normalVectorX * beaconRadius;
            downBeaconY -=  normalVectorY * beaconRadius;
            beaconList.add(new Beacon(new Position(downBeaconX, downBeaconY), new Circle(beaconRadius)));
        }

        return beaconList;
    }

    /**
     * Check if there are intersections between a given circle and line (from a segment)
     * @param circle a circle
     * @param segment a segment
     * @return the list of the intersections (2 intersections, 1 intersection or empty)
     */
    private List<Position> circleIntersection(Circle circle, Segment segment) {
        List<Position> intersectionList = new ArrayList<>();
        double a = segment.getA();
        double b = segment.getB();
        double radius = circle.getRadius();

        double discriminant = 4 * Math.pow(a, 2) * Math.pow(b, 2) - 4 * (Math.pow(a, 2) + 1) * (Math.pow(b, 2) - Math.pow(radius, 2));

        if(discriminant > 0) {
            double firstSolution = (-2 * a * b + Math.sqrt(discriminant)) / (2 * (Math.pow(a, 2) + 1));
            intersectionList.add(new Position(firstSolution, a*firstSolution+b));

            double secondSolution = (-2 * a * b - Math.sqrt(discriminant)) / (2 * (Math.pow(a, 2) + 1));
            intersectionList.add(new Position(secondSolution, a*secondSolution+b));
        }

        else if (discriminant == 0) {
            double onlySolution = (-2 * a * b) / (2 * (Math.pow(a, 2) + 1));
            intersectionList.add(new Position(onlySolution, a*onlySolution+b));
        }

        return intersectionList;
    }
}
