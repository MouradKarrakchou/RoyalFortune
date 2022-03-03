package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class Captain {
    private Ship ship;
    private Goal goal;
    private List<Sailor> sailors;
    private FictitiousCheckpoint fictitiousCheckpoints;
    private ArrayList<Action> roundActions;
    private DirectionsManager directionsManager;
    private Crew crew;
    private PreCalculator preCalculator;
    private SeaMap seaMap;

    public Captain(Ship ship, List<Sailor> sailors, Goal goal, FictitiousCheckpoint fictitiousCheckpoints){
        this.ship = ship;
        this.sailors = sailors;
        this.goal = goal;
        this.fictitiousCheckpoints = fictitiousCheckpoints;
        roundActions = new ArrayList<>();
        directionsManager = new DirectionsManager(ship, fictitiousCheckpoints);
        seaMap=new SeaMap(goal,fictitiousCheckpoints, ship.getPosition());
        preCalculator=new PreCalculator(ship,sailors,seaMap);
        crew=new Crew(sailors,ship,preCalculator);
    }
    public Captain(){}


    public String roundDecisions() {

        disassociate();
        roundActions.clear();
        seaMap.updateCheckPoint();
        directionsManager.update();
        roundProceed();
        roundActions.addAll(crew.makeBoatMove());

        String out = createAction();
        return "[" + out + "]";
    }

    /**
     * Create the Json of actions
     * @return String wit the json
     */
    public String createAction(){
        StringBuilder actionsToDo = new StringBuilder();
        for(Action action : roundActions)
            actionsToDo.append(action.toString()).append(",");
        return(actionsToDo.substring(0, actionsToDo.length() - 1));
    }


    public void roundProceed(){
        double angleMove = directionsManager.getAngleMove();
        double angleMadeBySailors = 0;
        double signOfAngleMove = (angleMove/Math.abs(angleMove));

        if (!directionsManager.isConeTooSmall() && !directionsManager.isInCone()) {
            int numberOfSailorOaring=crew.associateSailorToOar(numberOfSailorToTurn(angleMove),directionsManager.getDirection());
            angleMadeBySailors = numberOfSailorOaring*angleMove/Math.abs(angleMove)*(Math.PI/ship.getNbrOar());
        }

        if(-Math.PI/4 <= angleMove - angleMadeBySailors && angleMove - angleMadeBySailors <= Math.PI/4 && Math.abs(angleMove - angleMadeBySailors)>Math.pow(10,-3)) {
            roundActions.addAll(crew.sailorMoveToRudder());
            roundActions.addAll(crew.sailorsTurnWithRudder(angleMove - angleMadeBySailors));
        }
        else if(angleMove - angleMadeBySailors < -Math.PI/4 || Math.PI/4 < angleMove - angleMadeBySailors) {
            roundActions.addAll(crew.sailorMoveToRudder());
            roundActions.addAll(crew.sailorsTurnWithRudder(signOfAngleMove*Math.PI/4));
        }
    }


    private void disassociate() {
        sailors.forEach(sailor -> sailor.setTargetEntity(null));
    }



    /**
     * It gives the number of Sailor needed to turn
     * @param orientation that we need to make our ship move
     * @return the number of Sailor that will oar in one direction
     */
    public int numberOfSailorToTurn(double orientation){
        int sizeOfOarList = ship.getOarList(orientation > 0 ? "right" : "left").size();
        int maxSailorsToMoveAngle=Math.abs((int) Math.ceil(orientation/(Math.PI / ship.getNbrOar())));
        int numberOfSailors=sailors.size();
        return(Math.min(numberOfSailors,Math.min(sizeOfOarList,maxSailorsToMoveAngle)));
    }


    public List<Action> getRoundActions(){
        return roundActions;
    }

    public Crew getCrew() {
        return crew;
    }

    public PreCalculator getPreCalculator() {
        return preCalculator;
    }

    public SeaMap getSeaMap() {
        return seaMap;
    }
}
