package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.PreCalculator;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Crew;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.SailorMovementStrategy;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.SailorPlacement;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaMap;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

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

    public Captain(Ship ship, List<Sailor> sailors, Goal goal, FictitiousCheckpoint fictitiousCheckpoints, Wind wind) {
        this.ship = ship;
        this.sailors = sailors;
        this.wind = wind;
        this.seaEntities= new ArrayList<>();
        associations = new Associations();
        roundActions = new ArrayList<>();
        directionsManager = new DirectionsManager(ship, fictitiousCheckpoints);
        seaMap = new SeaMap(goal, fictitiousCheckpoints, ship.getPosition(),wind,seaEntities);
        preCalculator = new PreCalculator(ship, sailors, seaMap,wind);
        crew = new Crew(sailors, associations);

    }

    public Captain() {
    }

    /**
     * The captain make all decisions of the round.
     *
     * @return The json file of the round actions
     */
    public String roundDecisions() {
        associations.dissociateAll();
        roundActions.clear();
        seaMap.updateCheckPoint(seaEntities);
        directionsManager.update();
        roundProceed();
        roundActions.addAll(crew.makeBoatMove());

        String out = "";
        if (!roundActions.isEmpty())
            out = createAction();

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
        int oarWeight = oarWeightNeeded(angleMove);
        double angleSailorsShouldMake = angleSailorsShouldMakeNeeded(oarWeight);

        Optional<Boolean> optionalSailDecision = getSailDecision();
        boolean useSail = optionalSailDecision.isPresent();
        boolean needRudder = getRudderDecision(angleMove, angleSailorsShouldMake);
        boolean needWatch = true;

        SailorPlacement sailorPlacement = new SailorPlacement(oarWeight, needRudder, useSail, needWatch);
        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations,preCalculator);
        SailorPlacement strategyAnswer = sailorMovementStrategy.askPlacement(sailorPlacement);

        if(strategyAnswer.hasSail() && optionalSailDecision.isPresent())
            roundActions.addAll(crew.sailorsUseSail(optionalSailDecision.get()));

        turnWithRudderRoundAction(strategyAnswer, angleMove);

        roundActions.addAll(crew.sailorsMove());
    }

    /**
     * Oar weight needed to calculate the angle sailors should make
     * @param angleMove the ship needs to turn to
     * @return oar weight
     */
    int oarWeightNeeded(double angleMove) {
        if (coneNotTooSmallAndNotInCone())
            return oarWeight(angleMove);

        return 0;
    }

    /**
     * Calculate the angle the sailors should make needed
     * @param oarWeight oar weight
     * @return the angle the sailors should make
     */
    public double angleSailorsShouldMakeNeeded(int oarWeight) {
        if (coneNotTooSmallAndNotInCone())
            return oarWeight * (Math.PI / ship.getNbrOar());

        return 0;
    }

    /**
     * Check if the cone is not too small and if we are not already in the cone (so we can turn)
     * @return true if we can turn in the cone
     */
    public boolean coneNotTooSmallAndNotInCone() {
        return !directionsManager.isConeTooSmall() && !directionsManager.isInCone();
    }

    /**
     * If there is a usable rudder, add a rudder action
     * @param strategyAnswer
     * @param angleMove
     */
    void turnWithRudderRoundAction(SailorPlacement strategyAnswer, double angleMove) {
        if(strategyAnswer.hasRudder()){
            double angleMadeBySailors = (strategyAnswer.getNbRightSailors() - strategyAnswer.getNbLeftSailors()) * (Math.PI / ship.getNbrOar());
            double angleToTurnRudder = computeAngleToTurnRudder(angleMove, angleMadeBySailors);
            roundActions.addAll(crew.sailorsTurnWithRudder(angleToTurnRudder));
        }
    }

    /**
     *
     * @param angleMove angle the ship has to make to be in the right orientation
     * @param angleMadeBySailors angle made by sailors when they oar
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

    void useWatch(SailorPlacement strategyAnswer) {
        if(strategyAnswer.hasWatch()){
            roundActions.addAll(crew.useWatch());
        }
    }

    /**
     * Check if the angle is different from 0
     * @param angleMove angle the ship has to make to be in the right orientation
     * @param angleSailorsShouldMake ideal angle the sailors should make by oaring
     * @return true if we need to use rudder, false in other case
     */
    public boolean getRudderDecision(double angleMove, double angleSailorsShouldMake) {
        return Math.abs(angleMove - angleSailorsShouldMake) > Math.pow(10, -3);
    }
        /**
         * If we need to use the sail return the action to do, in the other case return optional.empty
         * @return eventually true if we need the sail
         */
    public Optional<Boolean> getSailDecision() {
        if(wind.getStrength() == 0.0) return Optional.empty();

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

    public void setSeaEntities(List<SeaEntities> seaEntities) {
        this.seaEntities = seaEntities;
    }

    public void updateSeaEntities(List<SeaEntities> seaEntities) {this.seaEntities=seaEntities;
    }
}
