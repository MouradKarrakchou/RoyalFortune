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
     * Generates beacons all around the circle.
     * @param reefPosition reef position
     * @param reefShape reef position
     * @return the list containing the beacons
     */
    public static List<Beacon> generateBeacon(Position reefPosition, Circle reefShape){
        List<Beacon> beaconList = new ArrayList<>();
        double circleRadius = reefShape.getRadius();
        double reefPositionX = reefPosition.getX();
        double reefPositionY = reefPosition.getY();

        int numberOfBeacons = (int) Math.max(10, Math.ceil(50 * circleRadius / 1000));
        double beaconShift = (2 * Math.PI) / numberOfBeacons;

        for(int i = 0; i < numberOfBeacons; i++) {
            double rightBeaconX = reefPositionX + Math.cos(i * beaconShift) * (circleRadius * 1.1);
            double rightBeaconY = reefPositionY + Math.sin(i * beaconShift) * (circleRadius * 1.1);
            Beacon rightBeacon = new Beacon(new Position(rightBeaconX, rightBeaconY));
            beaconList.add(rightBeacon);
        }

        return beaconList;
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

        discriminantValue(segmentToWorkOn,segment,discriminant,circlePosition, intersectionList);

        intersectionList.add(0,segment.getPointA());
        intersectionList.add( segment.getPointB());
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
    public static Segment segmentToWorkOn(Segment segment, Position circlePosition) {
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
    public static double discriminant(double segmentToWorkOnA, double segmentToWorkOnB, double radius) {
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
    public static void discriminantValue(Segment segmentToWorkOn, Segment segment,double discriminant, Position circlePosition, List<Position> intersectionList) {
        if(discriminant > 0) {
            positiveDiscriminant(segmentToWorkOn, segment, discriminant, circlePosition, intersectionList);
        }

        else if (discriminant == 0) {
            zeroDiscriminant(segmentToWorkOn, segment, circlePosition, intersectionList);
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
    public static void positiveDiscriminant(Segment segmentToWorkOn, Segment segment,double discriminant, Position circlePosition, List<Position> intersectionList) {
        Position pointASave = segment.getPointA();
        Position pointBSave = segment.getPointB();

        double circlePositionX = circlePosition.getX();
        double circlePositionY = circlePosition.getY();

        double segmentToWorkOnA = segmentToWorkOn.getA();
        double segmentToWorkOnB = segmentToWorkOn.getB();



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
    public static void zeroDiscriminant(Segment segmentToWorkOn, Segment segment, Position circlePosition, List<Position> intersectionList) {
        Position pointASave = segment.getPointA();
        Position pointBSave = segment.getPointB();

        double circlePositionX = circlePosition.getX();
        double circlePositionY = circlePosition.getY();

        double segmentToWorkOnA = segmentToWorkOn.getA();
        double segmentToWorkOnB = segmentToWorkOn.getB();
        double onlySolution = (-2 * segmentToWorkOnA * segmentToWorkOnB) / (2 * (Math.pow(segmentToWorkOnA, 2) + 1));
        Position position = new Position(onlySolution, segmentToWorkOnA * onlySolution + segmentToWorkOnB);
        Position realPosition = realPosition(onlySolution, segmentToWorkOnA, segmentToWorkOnB, circlePositionX, circlePositionY);

        boolean inSegment = segmentToWorkOn.pointInSegment(position);
        boolean aNotEquals = !pointASave.equals(realPosition);
        boolean bNotEquals = !pointBSave.equals(realPosition);

        if(inSegment && aNotEquals && bNotEquals) {
            intersectionList.add(new Position(onlySolution + circlePositionX, segmentToWorkOnA * onlySolution + segmentToWorkOnB + circlePositionY));
        }
    }

    /**
     * Compute the real position of the intersection
     * @param onlySolution solution of the only intersection possible
     * @param segmentToWorkOnA value of the line equation ax+b
     * @param segmentToWorkOnB value of the line equation ax+b
     * @param circlePositionX x component of the circle position
     * @param circlePositionY y component of the circle position
     * @return the real position of the intersection
     */
    public static Position realPosition(double onlySolution, double segmentToWorkOnA, double segmentToWorkOnB, double circlePositionX, double circlePositionY) {
        return new Position(onlySolution + circlePositionX, segmentToWorkOnA * onlySolution + segmentToWorkOnB + circlePositionY);
    }

    public static boolean positionIsInTheCircle(Position pointA, Position position, Circle shape) {
        return Mathematician.distanceFormula(pointA,position)< shape.getRadius();
    }
}