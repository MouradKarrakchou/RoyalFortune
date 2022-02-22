package fr.unice.polytech.si3.qgl.royal_fortune.tooling.Simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;

import java.util.ArrayList;

public class Game {
    Ship ship;
    Cockpit cockpit;
    ArrayList<Sailor> sailors;
    Goal goal;
    Referee referee;
    int numberOfCheckpointVisited =0;
    public Game(String initialiser){

        String sailorsJson = JsonManager.getNode(initialiser, "sailors");
        sailors = JsonManager.readSailorsJson(sailorsJson);

        //
        //String entitiesJson = JsonManager.getNode(initialiser, "entities");
        //entities=JsonManager.readEntitiesJson(entitiesJson);
        cockpit=new Cockpit();
        referee=new Referee(cockpit);
        cockpit.initGame(initialiser);
        goal=cockpit.getGoal();
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
        double distanceSCX = goal.getCurrentCheckPoint().getPosition().getX() - ship.getPosition().getX();
        double distanceSCY = goal.getCurrentCheckPoint().getPosition().getY() - ship.getPosition().getY();
        double distanceSC = Math.sqrt(Math.pow(distanceSCX,2) + Math.pow(distanceSCY,2));
        double radius=((Circle)goal.getCurrentCheckPoint().getShape()).getRadius();
        System.out.println("Distance to the checkpoint: "+distanceSC);
        return distanceSC<=radius && goal.getCheckPoints().size() == 1;
        	
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
