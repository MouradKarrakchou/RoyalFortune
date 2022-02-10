package fr.unice.polytech.si3.qgl.royal_fortune.tooling.Simulation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.OarAction;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
    Ship ship;
    Cockpit cockpit;
    ArrayList<Sailor> sailors;
    Goal goal;
    Referee referee;
    public Game(String initialiser){

        String sailorsJson = JsonManager.getNode(initialiser, "sailors");
        sailors = JsonManager.readSailorsJson(sailorsJson);

        String checkpointsJson = JsonManager.getNode(initialiser,"goal");
        goal = JsonManager.readGoalJson(checkpointsJson);
        //
        //String entitiesJson = JsonManager.getNode(initialiser, "entities");
        //entities=JsonManager.readEntitiesJson(entitiesJson);
        cockpit=new Cockpit();

        referee=new Referee(cockpit);
        cockpit.initGame(initialiser);
        ship = cockpit.getShip();

    }

    void nextRound(){
        String jsonNextRound=createJson();
        System.out.println("-----------------------");
        System.out.println("jsonNextRound="+jsonNextRound);
        String jsonverif=cockpit.nextRound(jsonNextRound);
        System.out.println("jsonverif="+jsonverif);
        System.out.println("-----------------------");

        ArrayList<Action> actions=JsonManager.readActionJson(jsonverif);
        System.out.println(actions);
        this.ship = referee.makeAdvance(cockpit,actions);

    }

    private String createJson() {
        return "{\"ship\":"+ cockpit.getShip().toString()+"}";
    }

    @Override
    public String toString() {
        //"Orientation: "+ship.getPosition().getOrientation()+'\n';
        String string=cockpit.getShip().getPosition().getX()+";"+cockpit.getShip().getPosition().getY()+'\n';
        return(string);
    }
}
