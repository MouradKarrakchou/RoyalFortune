package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Shape;

public class DirectionsManager {
    Ship ship;
    Goal goal;

    public DirectionsManager(Ship ship, Goal goal) {
        this.ship = ship;
        this.goal = goal;
    }

    /**
     * Calculate 2 angles:
     * -The angle between the direction vector of the ship and the axis from the ship to the checkpoint
     * -Half of the angle between the axis from the ship to the edges of the checkpoint
     *
     * @return the angle which the ship must turn, the angle in which the ship is in the right direction
     */
    public double[] angleCalculator() {
        double angleShip=ship.getPosition().getOrientation();
        Shape shape=goal.getCurrentCheckPoint().getShape();
        double radius =((Circle) shape).getRadius();


        double distanceSCX = goal.getCurrentCheckPoint().getPosition().getX() - ship.getPosition().getX();
        double distanceSCY = goal.getCurrentCheckPoint().getPosition().getY() - ship.getPosition().getY();
        double distanceSC = Math.sqrt(Math.pow(distanceSCX,2) + Math.pow(distanceSCY,2));
        double num = distanceSCX*Math.cos(angleShip) + distanceSCY*Math.sin(angleShip);

        double angleCone = Math.atan(radius / distanceSC);

        double angleMove = Math.acos(num / distanceSC);

        while(angleMove > Math.PI){
            angleMove -= 2*Math.PI;
        }

        while(angleMove < -Math.PI){
            angleMove += 2*Math.PI;
        }

        return new double[]{checkSign(angleMove), angleCone};
    }

    private double checkSign(double angleMove) {
        if (calculDistToCheckPoint(angleMove)<calculDistToCheckPoint(-angleMove))
            return angleMove;
        else return -angleMove;
    }

    private double calculDistToCheckPoint(double angleMove) {
        double anglerot=angleMove+ship.getPosition().getOrientation();
        double newX= ship.getPosition().getX()+Math.cos(anglerot);
        double newY= ship.getPosition().getY()+Math.sin(anglerot);


        double distanceSCX = goal.getCurrentCheckPoint().getPosition().getX() - newX;
        double distanceSCY = goal.getCurrentCheckPoint().getPosition().getY() - newY;
        return(Math.sqrt(Math.pow(distanceSCX,2) + Math.pow(distanceSCY,2)));
    }

    /**
     * Check if the ship is in the right direction
     * @param angleMove is the angle between the direction vector of the ship and the axis from the ship and the checkpoint
     * @param angleCone half of the angle between the axis from the ship and the edges of the checkpoint
     * @return true if the ship is in the right direction
     */
    public boolean isInCone(double angleMove, double angleCone) {
        return (Math.abs(angleMove) <= angleCone);
    }
    /**
     * Check if the next turn of the ship will exceed the right direction
     * @param angleMove is the angle between the direction vector of the ship and the axis from the ship and the checkpoint
     * @param angleCone half of the angle between the axis from the ship and the edges of the checkpoint
     * @return true if the next turn of the boat exceed the right direction
     */
    public boolean isConeTooSmall(double angleMove, double angleCone) {
        return (Math.abs(Math.abs(angleMove) + angleCone) < Math.PI/ship.getEntities().size());
    }

    double getAngleMove() { return angleCalculator()[0]; }

    double getAngleCone() { return angleCalculator()[1]; }

}
