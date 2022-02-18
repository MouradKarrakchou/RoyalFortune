package fr.unice.polytech.si3.qgl.royal_fortune.tooling.Simulation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.DAO.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.OarAction;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
    Ship ship;
    Cockpit cockpit;
    ArrayList<Sailor> sailors;
    Goal goal;
    Referee referee;
    public Game(String initialiser){
    	
    	InitGameDAO initGameDAO = JsonManager.readInitGameDAOJson(initialiser);
        sailors = initGameDAO.getSailors();
        goal = initGameDAO.getGoal();
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
        String string=cockpit.getShip().getPosition().getX()+";"+cockpit.getShip().getPosition().getY()+";"+ship.getPosition().getOrientation()+'\n';
        return(string);
    }

    public boolean isFinished() {
        double distanceSCX = goal.getCheckPoints().get(0).getPosition().getX() - ship.getPosition().getX();
        double distanceSCY = goal.getCheckPoints().get(0).getPosition().getY() - ship.getPosition().getY();
        double distanceSC = Math.sqrt(Math.pow(distanceSCX,2) + Math.pow(distanceSCY,2));
        double radius=((Circle)goal.getCheckPoints().get(0).getShape()).getRadius();
        System.out.println("Distance to the checkpoint: "+distanceSC);
        if (distanceSC<=radius)
            return true;
        else
            return false;
    }
    
    public String getAllCheckpointsForOutput() {
    	String out = "";
    	ArrayList<Checkpoint> checks = goal.getCheckPoints();
    	for(Checkpoint checkpoint : checks) {
    		Position pos = checkpoint.getPosition();
    		double x = pos.getX();
    		double y = pos.getY();
    		out+=x+";"+y+"\n";
    	}
    	return out;
    }
}
