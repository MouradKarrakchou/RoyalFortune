package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.OarAction;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

import java.util.ArrayList;

public class Referee {
    Cockpit cockpit;
    int rightPush;
    int leftPush;

    public Referee (Cockpit cockpit){
        this.cockpit=cockpit;
    }
    public void verif(String jsonverif) {
    }

    public Ship makeAdvance(Cockpit cockpit, ArrayList<Action> actions) {
        rightPush=0;
        leftPush=0;
        actions.stream().forEach(action -> doAction(action));
        return makeMooveShip(cockpit.getShip());
    }

    private Ship makeMooveShip(Ship ship) {
        Position shipPosition=ship.getPosition();
        double angleInitial=shipPosition.getOrientation();
        double anglerot= orientationCalculus()*Math.PI/cockpit.getShip().getEntities().size();
        int k=0;
        int norme=165*(rightPush+leftPush)/cockpit.getShip().getEntities().size();
        double newX=shipPosition.getX();
        double newY= shipPosition.getY();
        double angleCalcul=angleInitial;

        while (k<1000){
        newX+=norme*Math.cos(angleCalcul)/1000;
        newY+=norme*Math.sin(angleCalcul)/1000;
        angleCalcul+=anglerot/1000;
        k++;}

        while(angleCalcul > Math.PI){
            angleCalcul -= 2*Math.PI;
        }

        while(angleCalcul < -Math.PI){
            angleCalcul += 2*Math.PI;
        }

        shipPosition.setOrientation(angleCalcul);
        shipPosition.setX(newX);
        shipPosition.setY(newY);
        return ship;
    }

    private double orientationCalculus() {
        return (double) rightPush-leftPush;
    }



    private void doAction(Action action) {
        if(action instanceof OarAction){
        cockpit.getSailors().stream()
                .filter(sailor -> sailor.getId()==action.getSailorId())
                .forEach(sailor -> {if(sailor.getY()>0) rightPush+=1;
                else leftPush+=1;});}
    }

}
