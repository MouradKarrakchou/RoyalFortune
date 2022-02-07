package fr.unice.polytech.si3.qgl.royal_fortune.tooling.Simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

import java.util.ArrayList;

public class Referee {
    Cockpit cockpit;
    int rightPush;
    int leftPush;
    Entities entities;

    public Referee (Cockpit cockpit,Entities entities){
        this.cockpit=cockpit;
        this.entities=entities;
    }
    public void verif(String jsonverif) {
    }

    public void makeAdvance(Cockpit cockpit, ArrayList<Action> actions) {
        rightPush=0;
        leftPush=0;
        actions.stream().forEach(action -> doAction(action));
        makeMooveShip(cockpit.getShip());
    }

    private void makeMooveShip(Ship ship) {
        Position shipPosition=ship.getPosition();
        double angle=shipPosition.getOrientation();
        int norme=165*calculnorme();
        double orientaton=calculorientation()*Math.PI/4;
        double newX=norme*Math.cos(angle);
        double newY=norme*Math.cos(angle);
        shipPosition.setOrientation(orientaton+angle);
        shipPosition.setX(shipPosition.getX()+newX);
        shipPosition.setY(shipPosition.getY()+newY);
    }

    private double calculorientation() {
        if (rightPush>leftPush) return leftPush;
        else if (rightPush<leftPush) return rightPush;
        else return 0;
    }

    private int calculnorme() {
        if (rightPush>leftPush) return rightPush-leftPush;
        else if (rightPush<leftPush) return leftPush-rightPush;
        else return 0;
    }


    private void doAction(Action action) {
        action.getType().equals("oar");
        cockpit.getSailors().stream()
                .filter(sailor -> sailor.getId()==action.getSailorId())
                .forEach(sailor -> {if(sailor.getX()>0) rightPush+=1;
                else leftPush+=1;});
    }

}
