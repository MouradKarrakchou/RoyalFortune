package fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates;

import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.MovingAction;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.PreCalculator;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;

import java.util.*;
import java.util.stream.Collectors;


public class Crew {
    private final List<Sailor> sailors;
    private final Ship ship;
    private final PreCalculator preCalculator;
    private final Associations associations;

    public Crew(List<Sailor> sailors, Ship ship, PreCalculator preCalculator, Associations associations) {
        this.sailors = sailors;
        this.ship = ship;
        this.preCalculator = preCalculator;
        this.associations = associations;
    }

    /**
     * Make the remaining sailors oar evenly (if possible) to speed up the boat.
     * Make all the sailors associated to an oar to send a oar action.
     *
     * @return The list of action needed.
     */
    public List<Action> makeBoatMove() {
        List<Action> actions = new ArrayList<>();
        actions.addAll(sailorsMove());
        actions.addAll(sailorsOar());
        return (actions);
    }

    /**
     * Ask all sailors associated to an Entity to move to
     * If the sailor is already on the Entity, sailor will not move
     * This method update list of Action (roundActions)
     *
     * @return The list of action
     */
    public List<MovingAction> sailorsMove() {
        return sailors.stream()
                .filter(sailor -> !associations.isFree(sailor))
                .filter(sailor -> !sailor.isOnTheTargetEntity(associations))
                .map(sailor -> sailor.moveToTarget(associations))
                .toList();
    }

    /**
     * Ask all sailors associated to an Oar to oar
     * This method update list of Action (roundActions)
     */
    public List<Action> sailorsOar() {
        return new ArrayList<>(sailors.stream()
                .filter(sailor -> !associations.isFree(sailor))
                .filter(sailor -> associations.getAssociatedEntity(sailor).isOar())
                .filter(sailor -> sailor.isOnTheTargetEntity(associations))
                .map(Sailor::oar)
                .toList());
    }

    /**
     * Ask a sailor to turn with the rudder
     * This method update list of Action (roundActions)
     *
     * @param rotationRudder
     */
    public List<Action> sailorsTurnWithRudder(double rotationRudder) {
        return new ArrayList<>(sailors.stream()
                .filter(sailor -> associations.getAssociatedEntity(sailor) instanceof Rudder)
                .filter(sailor -> sailor.isOnTheTargetEntity(associations))
                .map(sailor -> sailor.turnWithRudder(rotationRudder))
                .toList());
    }
}
