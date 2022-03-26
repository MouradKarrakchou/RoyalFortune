package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.*;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryRectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.NextRoundDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Game {
    private Ship ship;
    private Cockpit cockpit;
    private List<Sailor> sailors;
    private Goal goal;
    private Referee referee;
    private List<SeaEntities> allSeaEntities;
    private List<SeaEntities> visibleEntities;


    final Logger logger = Logger.getLogger(Game.class.getName());
    int i=0;

    public Game(String initialiser, String allSeaEntities){
        InitGameDAO initGameDAO = JsonManager.readInitGameDAOJson(initialiser);
        this.allSeaEntities = JsonManagerTool.readListSeaEntitiesJson(allSeaEntities);
        this.sailors = initGameDAO.getSailors();
        this.goal = initGameDAO.getGoal();
        this.cockpit = new Cockpit();
        this.cockpit.initGame(initialiser);
        this.goal=cockpit.getGoal();
        this.ship = new Ship(cockpit.getShip());
        this.referee=new Referee(cockpit,ship,sailors);
        visibleEntities = new ArrayList<>();
    }

    public Game(String initialiser){
    	InitGameDAO initGameToolDAO = JsonManager.readInitGameDAOJson(initialiser);
        this.sailors = initGameToolDAO.getSailors();
        this.goal = initGameToolDAO.getGoal();
        this.cockpit = new Cockpit();
        this.cockpit.initGame(initialiser);
        this.goal=cockpit.getGoal();
        this.ship = new Ship(cockpit.getShip());
        this.referee=new Referee(cockpit,ship,sailors);
        visibleEntities = new ArrayList<>();
        allSeaEntities= new ArrayList<>();
    }

    void nextRound(Wind wind){
        String jsonNextRound=createJson(wind);
        logger.info("-----------------------");
        String out = "jsonNextRound="+jsonNextRound;
        logger.info(out);
        String jsonverif=cockpit.nextRound(jsonNextRound);
        out = "jsonverif="+jsonverif;
        logger.info(out);
        logger.info("-----------------------");

        List<Action> actions=JsonManager.readActionJson(jsonverif);
        logger.info(String.valueOf(actions));
        this.ship = referee.makeAdvance(cockpit,actions);

    }

    public String createJson(Wind wind) {
        checkVisibleEntities();
        NextRoundDAO next = new NextRoundDAO(ship, visibleEntities, wind);
        return next.toString();
    }

    private void checkVisibleEntities() {
        visibleEntities.clear();
        for(SeaEntities currentSeaEntitie : allSeaEntities){
            Position shipPosition = ship.getPosition();
            Position currentEntitiePosition = currentSeaEntitie.getPosition();
            Double distance = Mathematician.distanceFormula(shipPosition, currentEntitiePosition);
            if(distance < Observer.MAX_RANGE)
                visibleEntities.add(currentSeaEntitie);
        }
    }

    @Override
    public String toString() {
        return ship.getPosition().getX()+";"+cockpit.getShip().getPosition().getY()+";"+ship.getPosition().getOrientation()+'\n';
    }

    public boolean isFinished() {
        double distanceSCX = goal.getCurrentCheckPoint().getPosition().getX() - ship.getPosition().getX();
        double distanceSCY = goal.getCurrentCheckPoint().getPosition().getY() - ship.getPosition().getY();
        double distanceSC = Math.sqrt(Math.pow(distanceSCX,2) + Math.pow(distanceSCY,2));
        double radius=((Circle)goal.getCurrentCheckPoint().getShape()).getRadius();
        String out = "Distance to the checkpoint: "+distanceSC;
        logger.info(out);
        return (distanceSC<=radius && goal.getCheckPoints().size() == 1);
    }


    public Goal getGoal() {
        return goal;
    }

    public List<SeaEntities> getAllSeaEntities() {
        return allSeaEntities;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public List<Beacon> computeAllBeacons(){
        List<Beacon> allBeacon = new ArrayList<>();
        for (SeaEntities seaEntity : allSeaEntities) {
            if(seaEntity.isStream())
                allBeacon.addAll(GeometryRectangle.generateBeacon(seaEntity.getPosition(), (Rectangle) seaEntity.getShape()));
        }
        return allBeacon;
    }


    
}