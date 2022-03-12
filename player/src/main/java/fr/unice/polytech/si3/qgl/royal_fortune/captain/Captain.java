package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.PreCalculator;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Crew;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.SailorMovementStrategy;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.SailorPlacement;
import fr.unice.polytech.si3.qgl.royal_fortune.target.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.LiftSailAction;
import fr.unice.polytech.si3.qgl.royal_fortune.action.LowerSailAction;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;

import java.util.ArrayList;
import java.util.List;


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
        associations.dissociateAll();
        roundActions.clear();
        seaMap.updateCheckPoint();
        directionsManager.update();
        roundProceed();
        roundActions.addAll(crew.makeBoatMove());
        System.out.println(roundActions.size());
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

        SailorPlacement sailorPlacement = new SailorPlacement(oarWeight, needRudder, needSail);
        SailorMovementStrategy sailorMovementStrategy = new SailorMovementStrategy(sailors, ship, associations,preCalculator);
        SailorPlacement strategyAnswer = sailorMovementStrategy.askPlacement(sailorPlacement);
        System.out.println(strategyAnswer);

        Rudder rudder = ship.getRudder();
        Sailor rudderSailor = associations.getAssociatedSailor(rudder);
        if(rudderSailor != null){
            System.out.println("Sailor: " + rudderSailor.getX() + ", " + rudderSailor.getY());
        }
        System.out.println("Rudder: " + rudder.getX() + ", " + rudder.getY());

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

        if(strategyAnswer.hasSail()) {
            Sailor sailorOfSail = associations.getAssociatedSailor(ship.getSail());
            if(takeWind)
                roundActions.add(new LowerSailAction(sailorOfSail.getId()));
            else
                roundActions.add(new LiftSailAction(sailorOfSail.getId()));

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

    public Associations getAssociations() {
        return associations;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
