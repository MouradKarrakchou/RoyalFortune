package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.action.*;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;

import java.util.List;
//
public class Referee {/*
    private final Cockpit cockpit;
    private final List<Sailor> sailors;
    private Ship ship;
    int rightPush;
    int leftPush;
    double rudderRotation = 0.0;
    boolean sailOpenned = false;

    public Referee(Cockpit cockpit, Ship ship, List<Sailor> sailors) {
        this.cockpit = cockpit;
        this.ship=ship;
        this.sailors=sailors;
    }

    public Ship makeAdvance(Cockpit cockpit, List<Action> actions) {
        rightPush = 0;
        leftPush = 0;
        actions.forEach(this::doAction);
        return makeMooveShipByOaring(ship);
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
        return (orientationCalculus() * Math.PI / ship.getNbrOar()) +rudderRotation;
    }

    
    public int computeNorme(){
        //(nombre de voile ouverte / nombre de voile) x force du vent x cosinus(angle entre la direction du vent et la direction du bateau)
        Wind wind = cockpit.getCaptain().getWind();
        int norme = 165 * (rightPush + leftPush) / ship.getNbrOar();
        if(sailOpenned)
            norme+= wind.getStrength()*Math.cos(Math.abs(wind.getOrientation()-ship.getPosition().getOrientation()));
        return norme;
    }

    public double orientationCalculus() {
        return (double) rightPush - leftPush;
    }


    public void doAction(Action action) {
        if (action instanceof MovingAction)
            makeMove((MovingAction) action);
        else if (action instanceof OarAction)
            useOar((OarAction)action);
        else if (action instanceof RudderAction)
            rudderRotation = useRudder((RudderAction)action);
        else if (action instanceof SailAction)
            useSail((SailAction)action);
    }

    private void useSail(SailAction sailAction) {
        if (sailors.stream()
                .filter(sailor -> sailor.getId() == sailAction.getSailorId())
                .filter(sailor -> isOnASail(sailor))
                .count()>0)
                sailOpenned = sailAction.getAction().equals(SailAction.LOWER)?true:false;
    }



    public void makeMove(MovingAction movingAction) {
        sailors.stream()
                .filter(sailor -> sailor.getId() == movingAction.getSailorId())
                .forEach(sailor -> {
                    makeMoveToPosition(sailor,movingAction.getXdistance(),movingAction.getYdistance());
                });
    }

    private void makeMoveToPosition(Sailor sailor, int xdistance, int ydistance) {
        if (xdistance+ydistance<=5){
            sailor.setX(xdistance+sailor.getX());
            sailor.setY(ydistance+sailor.getY());
        }
    }

    /*
    public double useRudder(RudderAction rudderAction) {
        if (sailors.stream()
                .filter(sailor -> sailor.getId() == rudderAction.getSailorId())
                .filter(sailor -> isOnARudder(sailor))
                .count()>0)
        return rudderAction.getRotation();
        else return 0;
    }

    public void useOar(OarAction oarAction) {
        sailors.stream()
                .filter(sailor -> sailor.getId() == oarAction.getSailorId())
                .filter(sailor -> isOnAOar(sailor))
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

    */
}
