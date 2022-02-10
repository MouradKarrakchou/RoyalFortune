package fr.unice.polytech.si3.qgl.royal_fortune.tooling.Simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.OarAction;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

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
        double angle=shipPosition.getOrientation()+calculorientation()*Math.PI/4;
        int norme=165*(rightPush+leftPush)/cockpit.getShip().getEntities().size();
        double newX=norme*Math.sin(angle);
        double newY=norme*Math.cos(angle);
        shipPosition.setOrientation(angle);
        shipPosition.setX(shipPosition.getX()+newX);
        shipPosition.setY(shipPosition.getY()+newY);
        return ship;
    }

    private double calculorientation() {
        if (rightPush>leftPush) return rightPush-leftPush;
        else if (rightPush<leftPush) return leftPush-rightPush;
        else return 0;
    }



    private void doAction(Action action) {
        if(action instanceof OarAction){
        cockpit.getSailors().stream()
                .filter(sailor -> sailor.getId()==action.getSailorId())
                .forEach(sailor -> {if(sailor.getX()>0) rightPush+=1;
                else leftPush+=1;});}
    }

}
