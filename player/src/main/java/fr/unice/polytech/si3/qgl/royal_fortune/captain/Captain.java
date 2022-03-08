package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Captain(Ship ship, List<Sailor> sailors, Goal goal, FictitiousCheckpoint fictitiousCheckpoints, Wind wind) {
        this.ship = ship;
        this.sailors = sailors;
        this.wind = wind;
        associations = new Associations();
        roundActions = new ArrayList<>();
        directionsManager = new DirectionsManager(ship, fictitiousCheckpoints);
        seaMap = new SeaMap(goal, fictitiousCheckpoints, ship.getPosition());
        preCalculator = new PreCalculator(ship, sailors, seaMap);
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
        associations.dissociateAll();
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
     *
     * @return String wit the json
     */
    public String createAction() {
        StringBuilder actionsToDo = new StringBuilder();
        for (Action action : roundActions)
            actionsToDo.append(action.toString()).append(",");
        return (actionsToDo.substring(0, actionsToDo.length() - 1));
    }


    public void roundProceed() {
        double angleMove = directionsManager.getAngleMove();
        double angleSailorsShouldMake = 0;
        double signOfAngleMove = (angleMove / Math.abs(angleMove));

        int oarWeight = 0;
        boolean needRudder = false;
        boolean needSail = false;

        boolean takeWind = false;

        if (!directionsManager.isConeTooSmall() && !directionsManager.isInCone()){
            oarWeight = oarWeight(angleMove);
            angleSailorsShouldMake = oarWeight * (Math.PI / ship.getNbrOar());
        }

        if ((-Math.PI / 4 <= angleMove - angleSailorsShouldMake && angleMove - angleSailorsShouldMake <= Math.PI / 4 && Math.abs(angleMove - angleSailorsShouldMake) > Math.pow(10, -3)) || ((angleMove - angleSailorsShouldMake < -Math.PI / 4 || Math.PI / 4 < angleMove - angleSailorsShouldMake)))
        {
            needRudder = true;
        }

        if( (wind.getOrientation() + Math.PI/2) > ship.getPosition().getOrientation()
                && ship.getPosition().getOrientation() > (wind.getOrientation() - Math.PI/2)
                && !ship.getSail().isOpenned() ) {
            needSail = true;
            takeWind = true;
        }

        if( (ship.getPosition().getOrientation() > (wind.getOrientation() + Math.PI/2)
                || (wind.getOrientation() - Math.PI/2) > ship.getPosition().getOrientation())
                && ship.getSail().isOpenned() ) {
            needSail = true;
            takeWind = false;
        }

        if(needSail) {
            Sail mySail = ship.getSail();
            Sailor sailorOfSail = mySail.getSailor();

            roundActions.add(new SailAction(sailorOfSail.getId(), takeWind));
        }

        SailorPlacement sailorPlacement = new SailorPlacement(oarWeight, needRudder, needSail);
        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations,preCalculator);

        SailorPlacement strategyAnswer = sailorMovementStrategy.askPlacement(sailorPlacement);
        double angleMadeBySailors = (strategyAnswer.getNbRightSailors() - strategyAnswer.getNbLeftSailors()) * (Math.PI / ship.getNbrOar());
        roundActions.addAll(crew.sailorsMove());

        if (-Math.PI / 4 <= angleMove - angleMadeBySailors
                && angleMove - angleMadeBySailors <= Math.PI / 4
                && Math.abs(angleMove - angleMadeBySailors) > Math.pow(10, -3)
                && strategyAnswer.hasRudder()) {
            roundActions.addAll(crew.sailorsTurnWithRudder(angleMove - angleMadeBySailors));
        } else if ((angleMove - angleSailorsShouldMake < -Math.PI / 4 || Math.PI / 4 < angleMove - angleSailorsShouldMake) && strategyAnswer.hasRudder()) {
            roundActions.addAll(crew.sailorsTurnWithRudder(signOfAngleMove * Math.PI/4));
        }

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
}
