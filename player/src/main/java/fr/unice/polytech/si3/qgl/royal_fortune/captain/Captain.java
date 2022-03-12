package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.PreCalculator;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Crew;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.SailorMovementStrategy;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.SailorPlacement;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.target.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.LiftSailAction;
import fr.unice.polytech.si3.qgl.royal_fortune.action.LowerSailAction;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Captain {
    private Ship ship;
    private List<Sailor> sailors;
    private ArrayList<Action> roundActions;
    private DirectionsManager directionsManager;
    private Crew crew;
    private PreCalculator preCalculator;
    private SeaMap seaMap;
    private Wind wind;
    private Associations associations;
    private List<SeaEntities> seaEntities;

    public Captain(Ship ship, List<Sailor> sailors, Goal goal, FictitiousCheckpoint fictitiousCheckpoints, Wind wind,List<SeaEntities> seaEntities) {
        this.ship = ship;
        this.sailors = sailors;
        this.wind = wind;
        this.seaEntities=seaEntities;
        associations = new Associations();
        roundActions = new ArrayList<>();
        directionsManager = new DirectionsManager(ship, fictitiousCheckpoints);
        seaMap = new SeaMap(goal, fictitiousCheckpoints, ship.getPosition(),wind,seaEntities);
        preCalculator = new PreCalculator(ship, sailors, seaMap,wind);
        crew = new Crew(sailors, ship, preCalculator, associations);

    }

    public Captain() {
    }

    /**
     * The captain make all decisions of the round.
     *
     * @return The json file of the round actions
     */
    public String roundDecisions() {
        System.out.println(ship.getSail().isOpenned());
        associations.dissociateAll();
        roundActions.clear();
        seaMap.updateCheckPoint(seaEntities);
        directionsManager.update();
        roundProceed();
        roundActions.addAll(crew.makeBoatMove());
        String out = createAction();
        System.out.println(roundActions);

        return "[" + out + "]";
    }

    /**
     * Create the Json of actions
     *
     * @return String wit the json
     */
    public String createAction() {
        StringBuilder actionsToDo = new StringBuilder();
        for (Action action : roundActions)
            actionsToDo.append(action.toString()).append(",");
        return (actionsToDo.substring(0, actionsToDo.length()-1));
    }


    public void roundProceed() {
        double angleMove = directionsManager.getAngleMove();
        double angleSailorsShouldMake = 0;

        int oarWeight = 0;

        if (!directionsManager.isConeTooSmall() && !directionsManager.isInCone()){
            oarWeight = oarWeight(angleMove);
            angleSailorsShouldMake = oarWeight * (Math.PI / ship.getNbrOar());
        }

        Optional<Boolean> optionalSailDecision = getSailDecision();
        boolean useSail = optionalSailDecision.isPresent();
        boolean needRudder = getRudderDecision(angleMove, angleSailorsShouldMake);


        SailorPlacement sailorPlacement = new SailorPlacement(oarWeight, needRudder, useSail);
        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations,preCalculator);
        SailorPlacement strategyAnswer = sailorMovementStrategy.askPlacement(sailorPlacement);
        System.out.println(strategyAnswer);

        if(strategyAnswer.hasSail())
            crew.sailorsUseSail(optionalSailDecision.get());

        if(strategyAnswer.hasRudder()){
            double angleMadeBySailors = (strategyAnswer.getNbRightSailors() - strategyAnswer.getNbLeftSailors()) * (Math.PI / ship.getNbrOar());
            double angleToTurnRudder = computeAngleToTurnRudder(angleMove, angleMadeBySailors);
            roundActions.addAll(crew.sailorsTurnWithRudder(angleToTurnRudder));
        }
        roundActions.addAll(crew.sailorsMove());


    }

    /**
     *
     * @param angleMove
     * @param angleMadeBySailors
     * @return the angle to turn to with the rudder
     */
    public double computeAngleToTurnRudder(double angleMove, double angleMadeBySailors) {
        boolean angleRemainingIsValid = (-Math.PI / 4 <= angleMove - angleMadeBySailors && angleMove - angleMadeBySailors <= Math.PI / 4);
        boolean angleIsNotZero = Math.abs(angleMove - angleMadeBySailors) > Math.pow(10, -3);

        if(angleRemainingIsValid && angleIsNotZero){
            return angleMove - angleMadeBySailors;
        }
        else{
            int signOfAngleMove = (int) (angleMove/Math.abs(angleMove));
            return signOfAngleMove * Math.PI/4;
        }
    }

    /**
     *
     * @param angleMove
     * @param angleSailorsShouldMake
     * @return true if we need to use rudder, false in other case
     */
    public boolean getRudderDecision(double angleMove, double angleSailorsShouldMake) {
        boolean angleIsNotZero = Math.abs(angleMove - angleSailorsShouldMake) > Math.pow(10, -3);
        return angleIsNotZero;
    }
        /**
         * If we need to use the sail return the action to do, in the other case return optional.empty
         * @return
         */
    public Optional<Boolean> getSailDecision() {
        if(wind.getStrength() == 0.0)return Optional.empty();

        boolean windGoodForUs =  (ship.getPosition().getOrientation()) < (wind.getOrientation() + Math.PI/2) && (ship.getPosition().getOrientation() > (wind.getOrientation() - Math.PI/2));
        boolean sailOpenned = ship.getSail().isOpenned();
        Optional<Boolean> openSail = Optional.empty();

        if(windGoodForUs && !sailOpenned){
            openSail = Optional.of(true);
        }
        else if(!windGoodForUs && sailOpenned){
            openSail = Optional.of(false);
        }

        return openSail;
    }

    /**
     * It gives the number of Sailor needed to turn
     *
     * @param orientation that we need to make our ship move
     * @return the number of Sailor that will oar in one direction
     */
    public int oarWeight(double orientation) {
        return Math.min((int) ((orientation * ship.getNbrOar()) / Math.PI), sailors.size());
    }

    public void updateWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return wind;
    }

    public List<Action> getRoundActions() {
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

    public Associations getAssociations() {
        return associations;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
