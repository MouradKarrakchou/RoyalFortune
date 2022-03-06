package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;

import java.util.*;
import java.util.stream.Collectors;


public class Crew {
    private final List<Sailor> sailors;
    private final Ship ship;
    private final PreCalculator preCalculator;

    public Crew(List<Sailor> sailors, Ship ship, PreCalculator preCalculator) {
        this.sailors = sailors;
        this.ship = ship;
        this.preCalculator = preCalculator;
    }

    /**
     * Make the remaining sailors oar evenly (if possible) to speed up the boat.
     * Make all the sailors associated to an oar to send a oar action.
     *
     * @return The list of action needed.
     */
    public List<Action> makeBoatMove() {
        List<Action> actions = new ArrayList<>();
        associateSailorToOarEvenly();
        actions.addAll(sailorsMove());
        actions.addAll(sailorsOar());
        return (actions);
    }

    /**
     * Captain will associate the best number of sailors to proceed a rotation of the given angle.
     *
     * @param numberOfSailors The rotation of the given angle.
     * @param whereToTurn     1 to turn right/ -1 to turn left
     */
    public void associateSailorToOar(int numberOfSailors, int whereToTurn) {
        //whereToTurn is always !=0
        List<Oar> oarList = ship.getOarList(whereToTurn > 0 ? DirectionsManager.RIGHT : DirectionsManager.LEFT);
        int i = 0;

        // We continue associating until we run out of sailors or oars
        while (i < numberOfSailors) {
            Oar oar = oarList.get(i);
            sailors.get(i).setTargetEntity(oar);
            oar.setSailor(sailors.get(i));
            i++;
        }
    }

    /**
     * Associate the same amount of sailors to the left oars and the right oars of the ship.
     */
    public void associateSailorToOarEvenly() {
        List<Oar> leftOarList = ship.getOarList(DirectionsManager.LEFT);
        List<Oar> rightOarList = ship.getOarList(DirectionsManager.RIGHT);
        int oarIndex = 0;
        int sailorIndex = 0;
        List<Sailor> listOfUnassignedSailors = getListOfUnassignedSailors();
        int numberOfSailorNeeded = preCalculator.numberOfSailorToOarEvenly(getListOfUnassignedSailors().size());
        // We continue associating until we run out of sailors or oars
        while (sailorIndex < numberOfSailorNeeded) {
            Oar leftOar = leftOarList.get(oarIndex);
            Oar rightOar = rightOarList.get(oarIndex);
            listOfUnassignedSailors.get(sailorIndex).setTargetEntity(leftOar);
            leftOar.setSailor(listOfUnassignedSailors.get(sailorIndex));
            listOfUnassignedSailors.get(++sailorIndex).setTargetEntity(rightOar);
            rightOar.setSailor(listOfUnassignedSailors.get(sailorIndex));
            sailorIndex++;
            oarIndex++;
        }
    }

    /**
     * Will ask the nearest sailor to the rudder to move to.
     */
    public List<Action> sailorMoveToRudder() {
        List<Action> roundActions = new ArrayList<>();
        Rudder rudder = ship.getRudder();
        if (rudder == null)
            return Collections.emptyList();

        Optional<Sailor> sailorToMove = sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() == null)
                .min(Comparator.comparingInt(sailor -> sailor.getDistanceToEntity(rudder)));

        if (sailorToMove.isPresent()) {
            Sailor s = sailorToMove.get();
            s.setTargetEntity(rudder);
            rudder.setSailor(s);
            roundActions.add(s.moveToTarget());
        }
        return roundActions;
    }

    /**
     * Ask all sailors associated to an Entity to move to
     * If the sailor is already on the Entity, sailor will not move
     * This method update list of Action (roundActions)
     *
     * @return The list of action
     */
    public List<Action> sailorsMove() {
        return new ArrayList<>(sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> !sailor.isOnTheTargetEntity())
                .map(Sailor::moveToTarget)
                .toList());
    }

    /**
     * Ask all sailors associated to an Oar to oar
     * This method update list of Action (roundActions)
     */
    public List<Action> sailorsOar() {
        return new ArrayList<>(sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> sailor.getTargetEntity().isOar())
                .filter(Sailor::isOnTheTargetEntity)
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
                .filter(sailor -> sailor.getTargetEntity() instanceof Rudder)
                .filter(Sailor::isOnTheTargetEntity)
                .map(sailor -> sailor.turnWithRudder(rotationRudder))
                .toList());

    }

    /**
     * Give The list of Sailors that are not assigned to an entitie;
     *
     * @return
     */
    public List<Sailor> getListOfUnassignedSailors() {
        return ((ArrayList<Sailor>) sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() == null)
                .collect(Collectors.toList()));
    }

}
