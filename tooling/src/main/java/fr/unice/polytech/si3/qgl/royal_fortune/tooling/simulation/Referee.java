package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.action.*;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;

import java.util.List;
//
public class Referee {
    private final Cockpit cockpit;
    private final List<Sailor> sailors;
    private Ship ship;
    private Captain captain;
    private int rightPush;
    private int leftPush;
    private double rudderRotation = 0.0;
    private boolean sailOpenned = false;
    private Associations associations;

    public Referee(Cockpit cockpit, Ship ship, List<Sailor> sailors) {
        this.cockpit = cockpit;
        this.ship=ship;
        this.sailors=sailors;
        this.captain = cockpit.getCaptain();
        this.associations=new Associations();
    }

    public Ship makeAdvance(Cockpit cockpit, List<Action> actions) {
        associations.dissociateAll();
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
        else if (action instanceof LiftSailAction)
            useLiftSail((LiftSailAction)action);
        else if (action instanceof LowerSailAction)
            useLowerSail((LowerSailAction)action);
    }
    public double useRudder(RudderAction rudderAction) {
        if (sailors.stream()
                .filter(sailor -> sailor.getId() == rudderAction.getSailorId())
                .filter(sailor -> isOnARudder(sailor))
                .count()>0)
            return rudderAction.getRotation();
        return 0;
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

    public boolean isOnAOar(Sailor sailor) {
        ship.getAllOar().stream()
                .filter(oar -> associations.getAssociatedSailor(oar)==null)
                .filter(oar -> oar.getX()==sailor.getX()&&oar.getY()==sailor.getY())
                .forEach(oar -> associations.addAssociation(sailor,oar));
        return(associations.getAssociatedEntity(sailor)!=null && associations.getAssociatedEntity(sailor) instanceof Oar);
    }

    public boolean isOnARudder(Sailor sailor) {
        ship.getEntities().stream()
                .filter(rudder -> associations.getAssociatedSailor(rudder)==null)
                .filter(rudder -> rudder.isRudder())
                .filter(rudder -> rudder.getX()==sailor.getX()&&rudder.getY()==sailor.getY())
                .forEach(rudder -> associations.addAssociation(sailor,rudder));
        return (associations.getAssociatedEntity(sailor)!=null && associations.getAssociatedEntity(sailor) instanceof Rudder);
    }
    private void useLowerSail(LowerSailAction action) {
        if (sailors.stream()
                .filter(sailor -> sailor.getId() == action.getSailorId())
                .filter(sailor -> isOnSail(sailor))
                .count()>0) {
            sailOpenned = true;
            ship.getSail().get(0).setOpenned(true);
        }
    }

    private void useLiftSail(LiftSailAction action) {
        if (sailors.stream()
                .filter(sailor -> sailor.getId() == action.getSailorId())
                .filter(sailor -> isOnSail(sailor))
                .count()>0)
        {sailOpenned = false;
            ship.getSail().get(0).setOpenned(false);
        }

    }

    private boolean isOnSail(Sailor sailor){
        ship.getEntities().stream()
                .filter(sail -> associations.getAssociatedSailor(sail)==null)
                .filter(sail -> sail.isSail())
                .filter(sail -> sail.getX()==sailor.getX()&&sail.getY()==sailor.getY())
                .forEach(sail -> associations.addAssociation(sailor,sail));
        return (associations.getAssociatedEntity(sailor)!=null && associations.getAssociatedEntity(sailor).isSail());
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

    public Associations getAssociations() {
        return associations;
    }
}
