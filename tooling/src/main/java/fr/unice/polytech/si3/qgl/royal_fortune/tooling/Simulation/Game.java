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
        cockpit.initGame(initialiser);
        ship=cockpit.getShip();
        referee=new Referee(cockpit);
    }

    void nextRound(){
        String jsonNextRound=createJson();
        String jsonverif=cockpit.nextRound(jsonNextRound);
        ArrayList<Action> actions=JsonManager.readActionJson(jsonverif);
        System.out.println(actions);
        referee.makeAdvance(cockpit,actions);

    }

    private String createJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode oarActionJSON = mapper.createObjectNode();
        oarActionJSON.put("ship", String.valueOf(ship));
        try {
            return mapper.writeValueAsString(oarActionJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String toString() {
        String string="Orientation: "+ship.getPosition().getOrientation()+'\n';
        string+="x,y: "+ship.getPosition().getX()+","+ship.getPosition().getY()+'\n';
        return(string);
    }
}
