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

    private Ship makeMooveShipByOaring(Ship ship) {
        Position shipPosition = ship.getPosition();
        double angleInitial = shipPosition.getOrientation();
        int norme = 165 * (rightPush + leftPush) / cockpit.getShip().getNbrOar()  ;
        double newX = shipPosition.getX();
        double newY = shipPosition.getY();
        double angleCalcul = angleInitial;

        int k = 0;
        double anglerot = (orientationCalculus() * Math.PI / cockpit.getShip().getNbrOar()) +rudderRotation;
        while (k < 1000) {
            newX += norme * Math.cos(angleCalcul) / 1000;
            newY += norme * Math.sin(angleCalcul) / 1000;
            angleCalcul += anglerot / 1000;
            k++;
        }

        angleCalcul = fixInterval(angleCalcul);
        updatePosition(shipPosition, angleCalcul, newX, newY);

        return ship;
    }

    private double orientationCalculus() {
        return (double) rightPush - leftPush;
    }


    private void doAction(Action action) {
        if (action instanceof OarAction)
            oarA((OarAction)action);
        else if (action instanceof RudderAction)
            rudderA((RudderAction)action);
    }

    private double rudderA(RudderAction rudderAction) {
         return rudderAction.getRotationRudder();
    }

    private void oarA(OarAction oarAction) {
        cockpit.getSailors().stream()
                .filter(sailor -> sailor.getId() == oarAction.getSailorId())
                .forEach(sailor -> {
                    if (sailor.getY() > 0) rightPush += 1;
                    else leftPush += 1;
                });
    }

    private double fixInterval(double angleCalcul){
        while (angleCalcul > Math.PI) {
            angleCalcul -= 2 * Math.PI;
        }

        while (angleCalcul < -Math.PI) {
            angleCalcul += 2 * Math.PI;
        }
        return angleCalcul;
    }

    private void updatePosition(Position shipPosition, double angleCalcul, double newX, double newY){
        shipPosition.setOrientation(angleCalcul);
        shipPosition.setX(newX);
        shipPosition.setY(newY);
    }


}
