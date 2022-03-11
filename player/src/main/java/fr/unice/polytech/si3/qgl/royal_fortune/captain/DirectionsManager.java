package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.target.FictitiousCheckpoint;

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
        double angleShip = ship.getPosition().getOrientation();
        Shape shape = fictitiousCheckpoints.getCurrentCheckPoint().getShape();
        double radius = ((Circle) shape).getRadius();


        double distanceSCX = fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getX() - ship.getPosition().getX();
        double distanceSCY = fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getY() - ship.getPosition().getY();
        double distanceSC = Math.sqrt(Math.pow(distanceSCX, 2) + Math.pow(distanceSCY, 2));
        double num = distanceSCX * Math.cos(angleShip) + distanceSCY * Math.sin(angleShip);

        double angleCone = Math.atan(radius / distanceSC);

        double angleMove = Math.acos(num / distanceSC);

        while (angleMove > Math.PI) {
            angleMove -= 2 * Math.PI;
        }

        while (angleMove < -Math.PI) {
            angleMove += 2 * Math.PI;
        }

        return new double[]{checkSign(angleMove), angleCone};
    }

    private double checkSign(double angleMove) {
        if (distToCheckPoint(angleMove) < distToCheckPoint(-angleMove))
            return angleMove;
        else return -angleMove;
    }

    private double distToCheckPoint(double angleMove) {
        double angleRot = angleMove + ship.getPosition().getOrientation();
        double newX = ship.getPosition().getX() + Math.cos(angleRot);
        double newY = ship.getPosition().getY() + Math.sin(angleRot);


        double distanceSCX = fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getX() - newX;
        double distanceSCY = fictitiousCheckpoints.getCurrentCheckPoint().getPosition().getY() - newY;
        return (Math.sqrt(Math.pow(distanceSCX, 2) + Math.pow(distanceSCY, 2)));
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
