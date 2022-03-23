package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.FictitiousCheckpoint;

public class DirectionsManager {
    private final Ship ship;
    private final FictitiousCheckpoint fictitiousCheckpoints;
    private double angleMove;
    private double angleCone;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    public DirectionsManager(Ship ship, FictitiousCheckpoint fictitiousCheckpoints) {
        this.ship = ship;
        this.fictitiousCheckpoints = fictitiousCheckpoints;
    }

    /**
     * Calculate 2 angles:
     * -The angle between the direction vector of the ship and the axis from the ship to the checkpoint
     * -Half of the angle between the axis from the ship to the edges of the checkpoint
     *
     * @return the angle which the ship must turn, the angle in which the ship is in the right direction
     */
    public double[] angleCalculator() {
        Checkpoint currentCheckpoint = fictitiousCheckpoints.getCurrentCheckPoint();
        angleMove = computeAngleMove(currentCheckpoint.getPosition(), ship.getPosition());
        angleMove = adjustAccuracy(angleMove);
        angleMove = checkSign(angleMove,currentCheckpoint.getPosition(), ship.getPosition());
        angleCone = computeAngleCone(currentCheckpoint, ship.getPosition());

        return new double[]{angleMove, angleCone};
    }

    public double computeDistanceBetweenTwoPosition(Position currentCheckpointPosition, Position shipPosition){
        double distanceSCX = currentCheckpointPosition.getX() - shipPosition.getX();
        double distanceSCY = currentCheckpointPosition.getY() - shipPosition.getY();
        return Math.sqrt(Math.pow(distanceSCX, 2) + Math.pow(distanceSCY, 2));
    }

    public double computeNumerateur(Position currentCheckpointPosition, Position shipPosition){
        double distanceSCX = currentCheckpointPosition.getX()- shipPosition.getX();
        double distanceSCY = currentCheckpointPosition.getY() - shipPosition.getY();
        double angleShip = shipPosition.getOrientation();
        return distanceSCX * Math.cos(angleShip) + distanceSCY * Math.sin(angleShip);
    }

    public double computeAngleCone(Checkpoint currentCheckpoint, Position shipPosition){
        Position currentCheckpointPosition = currentCheckpoint.getPosition();
        double radius = ((Circle) currentCheckpoint.getShape()).getRadius();
        double distanceShipCheckpoint = computeDistanceBetweenTwoPosition(currentCheckpointPosition, shipPosition);
        if(distanceShipCheckpoint == 0)
            throw new ArithmeticException();
        return Math.atan(radius / distanceShipCheckpoint);
    }

    public double computeAngleMove(Position currentCheckpointPosition, Position shipPosition){
        double distanceShipCheckpoint = computeDistanceBetweenTwoPosition(currentCheckpointPosition, shipPosition);
        double numerateur = computeNumerateur(currentCheckpointPosition, shipPosition);
        if(distanceShipCheckpoint == 0)
            throw new ArithmeticException();
        return Math.acos(numerateur / distanceShipCheckpoint);
    }

    public double adjustAccuracy(double angleMove){
        while (angleMove > Math.PI) {
            angleMove -= 2 * Math.PI;
        }
        while (angleMove < -Math.PI) {
            angleMove += 2 * Math.PI;
        }
        return angleMove;
    }

    public double checkSign(double angleMove, Position checkpointPosition, Position shipPosition) {
        double distanceAngleMove = distToCheckPoint(angleMove, checkpointPosition, shipPosition);
        double distanceMinusAngleMove = distToCheckPoint(-angleMove, checkpointPosition, shipPosition);

        if (distanceAngleMove < distanceMinusAngleMove)
            return angleMove;
        else return -angleMove;
    }

    public double distToCheckPoint(double angleMove, Position checkpointPosition, Position shipPosition) {
        double angleToRotate = angleMove + shipPosition.getOrientation();
        double newX = shipPosition.getX() + Math.cos(angleToRotate);
        double newY = shipPosition.getY() + Math.sin(angleToRotate);
        return computeDistanceBetweenTwoPosition(checkpointPosition, new Position(newX, newY,0));
    }

    /**
     * Check if the ship is facing the cone.
     *
     * @return true/false The ship is facing the cone.
     */
    public boolean isInCone() {
        return (Math.abs(angleMove) <= angleCone);
    }

    /**
     * Check if the next turn of the ship will exceed the right direction
     *
     * @return true if the next turn of the boat exceed the right direction
     */
    public boolean isConeTooSmall() {
        return (Math.abs(Math.abs(angleMove) + angleCone) < Math.PI / ship.getNbrOar());
    }

    public void update() {
        angleMove = angleCalculator()[0];
        angleCone = angleCalculator()[1];

    }

    public double getAngleMove() {
        return angleMove;
    }

    public double getAngleCone() {
        return angleCone;
    }

    public int getDirection() {
        return angleMove > 0 ? RIGHT : LEFT;
    }
}
