package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.*;
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
        //"Orientation: "+ship.getPosition().getOrientation()+'\n';
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
    
    public StringBuilder getAllCheckpointsForOutput() {
    	StringBuilder out = new StringBuilder();
    	List<Checkpoint> checks = goal.getCheckPoints();
    	for(Checkpoint checkpoint : checks) {
    		Position pos = checkpoint.getPosition();
    		double x = pos.getX();
    		double y = pos.getY();
            double radius = ((Circle)checkpoint.getShape()).getRadius();
            out.append(x).append(";").append(y).append(";").append(radius).append("\n");
    	}
    	return out;
    }

    public List<SeaEntities> getAllSeaEntities() {
        return allSeaEntities;
    }

    public StringBuilder getAllSeaEntitiesForOutput() throws Exception {
        StringBuilder out = new StringBuilder();
        List<SeaEntities> list = allSeaEntities;
        for(SeaEntities seaEntities : list) {
            Boolean isStream = seaEntities.isStream();
            Boolean isReef = seaEntities.isReef();
            if(isStream){
                out = createOutForStream((Stream) seaEntities, out);
            }
            else if(isReef){
                out = createOutForReef((Reef) seaEntities, out);
            }
            out.append("\n");
        }
        return out;
    }

    public StringBuilder createOutForStream(Stream stream, StringBuilder out) throws Exception {
        Position streamPos = stream.getPosition();
        out.append("stream").append(";");
        Optional<Rectangle> isRectangle = stream.getShape().isRectangle();
        if(isRectangle.isPresent()){
            Rectangle rectangle = isRectangle.get();
            out.append(rectangle.getHeight()).append(";").append(rectangle.getWidth()).append(";");
            out.append(stream.getStrength()).append(";");
            out.append(streamPos.getX()).append(";").append(streamPos.getY()).append(";").append(streamPos.getOrientation());
        }
        else
            throw new Exception("Stream with other shape than rectangle");
        return out;
    }

    public StringBuilder createOutForReef(Reef reef, StringBuilder out) throws Exception {
        Position streamPos = reef.getPosition();
        out.append("reef").append(";");
        Optional<Rectangle> isRectangle = reef.getShape().isRectangle();
        Optional<Circle> isCircle = reef.getShape().isCircle();
        if(isRectangle.isPresent()){
            Rectangle rect = isRectangle.get();
            out.append("rect").append(";").append(rect.getHeight()).append(";").append(rect.getWidth()).append(";");
            out.append(streamPos.getX()).append(";").append(streamPos.getY()).append(";").append(streamPos.getOrientation());
        }
        else if(isCircle.isPresent()){
            Circle circle = isCircle.get();
            out.append("circle").append(";").append(circle.getRadius()).append(";");
            out.append(streamPos.getX()).append(";").append(streamPos.getY()).append(";").append(streamPos.getOrientation());
        }
        else
            throw new Exception("Stream with other shape than rectangle");
        return out;
    }
    

    public void setShip(Ship ship) {
        this.ship = ship;
    }


    
}