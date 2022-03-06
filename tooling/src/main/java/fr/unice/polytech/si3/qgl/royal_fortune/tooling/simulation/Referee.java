package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.OarAction;
import fr.unice.polytech.si3.qgl.royal_fortune.action.RudderAction;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

import java.util.List;

public class Referee {
    Cockpit cockpit;
    int rightPush;
    int leftPush;
    double rudderRotation = 0.0;

    public Referee(Cockpit cockpit) {
        this.cockpit = cockpit;
    }

    public Ship makeAdvance(Cockpit cockpit, List<Action> actions) {
        rightPush = 0;
        leftPush = 0;
        actions.forEach(this::doAction);
        return makeMooveShipByOaring(cockpit.getShip());
    }

    public Ship makeMooveShipByOaring(Ship ship) {
        Position shipPosition = ship.getPosition();
        double angleInitial = shipPosition.getOrientation();
        double anglerot = computeAngleRotate();
        int norme = computeNorme();
        double newX = shipPosition.getX();
        double newY = shipPosition.getY();
        double angleCalcul = angleInitial;

        int k = 0;
        while (k < 1000) {
            newX += norme * Math.cos(angleCalcul) / 1000;
            newY += norme * Math.sin(angleCalcul) / 1000;
            angleCalcul += anglerot / 1000;
            k++;
        }

        angleCalcul = fixInterval(angleCalcul);
        updatePosition(shipPosition, angleCalcul, newX, newY);
        rudderRotation = 0.0;
        return ship;
    }

    public double computeAngleRotate(){
        return (orientationCalculus() * Math.PI / cockpit.getShip().getNbrOar()) +rudderRotation;
    }

    public int computeNorme(){
        return 165 * (rightPush + leftPush) / cockpit.getShip().getNbrOar();
    }

    public double orientationCalculus() {
        return (double) rightPush - leftPush;
    }


    public void doAction(Action action) {
        if (action instanceof OarAction)
            oarA((OarAction)action);
        else if (action instanceof RudderAction)
            rudderRotation = rudderA((RudderAction)action);
    }

    private double rudderA(RudderAction rudderAction) {
        return rudderAction.getRotation();
    }

    private void oarA(OarAction oarAction) {
        cockpit.getSailors().stream()
                .filter(sailor -> sailor.getId() == oarAction.getSailorId())
                .forEach(sailor -> {
                    if (sailor.getY() > 0) rightPush += 1;
                    else leftPush += 1;
                });
    }

    public double fixInterval(double angleCalcul){
        if (angleCalcul>-Math.PI)
        while (angleCalcul > Math.PI) {
            angleCalcul -= 2 * Math.PI;
        }
        else
        while (angleCalcul <= -Math.PI) {
            angleCalcul += 2 * Math.PI;
        }
        return angleCalcul;
    }

    private void updatePosition(Position shipPosition, double angleCalcul, double newX, double newY){
        shipPosition.setOrientation(angleCalcul);
        shipPosition.setX(newX);
        shipPosition.setY(newY);
    }


    public Cockpit getCockpit() {
        return cockpit;
    }

    public int getRightPush() {
        return rightPush;
    }

    public int getLeftPush() {
        return leftPush;
    }

    public double getRudderRotation() {
        return rudderRotation;
    }

    public void setRightPush(int rightPush) {
        this.rightPush = rightPush;
    }

    public void setLeftPush(int leftPush) {
        this.leftPush = leftPush;
    }

    public void setRudderRotation(double rudderRotation) {
        this.rudderRotation = rudderRotation;
    }
}
