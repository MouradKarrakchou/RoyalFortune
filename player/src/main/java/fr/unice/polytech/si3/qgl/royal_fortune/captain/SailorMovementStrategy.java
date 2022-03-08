package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;

import java.util.*;

public class SailorMovementStrategy {
    private List<Sailor> sailors;
    private Crew crew;
    private Ship ship;

    private int nbAssociatedLeftSailors = 0;
    private int nbAssociatedRightSailors = 0;
    private boolean hasAssociatedSail = false;
    private boolean hasAssociatedRudder =false;

    public static final int MAX_MOVING_RANGE = 5;

    public SailorMovementStrategy(List<Sailor> sailors, Ship ship){
        this.sailors = sailors;
        this.ship = ship;
    }

    public void reset(){
        nbAssociatedLeftSailors = 0;
        nbAssociatedRightSailors = 0;
        hasAssociatedSail = false;
        hasAssociatedRudder = false;
    }

    public int getNbAssociations(){
        return nbAssociatedLeftSailors + nbAssociatedRightSailors
                + (hasAssociatedSail ? 1 : 0) + (hasAssociatedRudder ? 1 : 0);
    }

    /**
     * Will try to associate as best the requested parameters
     * Priority order : Rudder, Sail, Oar.
     *
     * @param sailorPlacement The preferred placement of the sailors.
     * @return ...
     */
    public SailorPlacement askPlacement(SailorPlacement sailorPlacement) {
        reset();
        int oarWeight = sailorPlacement.getOarWeight();
        boolean needRudder = sailorPlacement.hasRudder();
        boolean needSail = sailorPlacement.hasSail();

        /* We are associating every entity having only one sailor nearby
         * until every entity has more than 1 sailor nearby. */
        int nbAssociations = 0;
        onlyOnePossibleAssociation(oarWeight, needRudder, needSail);
        while (nbAssociations != getNbAssociations()){
            nbAssociations = getNbAssociations();
            onlyOnePossibleAssociation(oarWeight, needRudder, needSail);
        }

        // We are associating (if possible) the nearest sailor to the Rudder.
        if(needRudder && !hasAssociatedRudder){
            hasAssociatedRudder = associateNearestSailor(ship.getRudder());
        }

        // We are associating (if possible) the left or right oar to the nearest sailor according to the oarWeight.
        if(oarWeight > 0)
            nbAssociatedRightSailors = associateNearestSailorToOar(DirectionsManager.RIGHT,
                    Math.abs(oarWeight) - nbAssociatedRightSailors);
        if(oarWeight < 0)
            nbAssociatedLeftSailors = associateNearestSailorToOar(DirectionsManager.LEFT,
                    Math.abs(oarWeight) - nbAssociatedLeftSailors);

        associateNearestSailorToOarEvenly();

        System.out.println("End of turn, nbAssociation -> " + getNbAssociations());
        return new SailorPlacement(nbAssociatedLeftSailors, nbAssociatedRightSailors, hasAssociatedSail, hasAssociatedRudder);
    }

    /**
     * Will associate (if possible) the nearest sailors from the entity to it.
     * @param entity The entity to be associated.
     * @return The association has been proceeded.
     */
    public boolean associateNearestSailor(Entities entity){
        Optional<Sailor> nearestSailor = entity.getNearestSailor(sailors, MAX_MOVING_RANGE);

        if (nearestSailor.isEmpty())
            return false;

        nearestSailor.get().setTargetEntity(entity);
        entity.setSailor(nearestSailor.get());
        return true;
    }

    /**
     * Will associate the sailor only if the entity has only one possible sailor in range.
     * @param entity The entity to associate.
     * @return Boolean, the association can be made.
     */
    public boolean associateTheOnlyOnePossible(Entities entity){
        List<Sailor> possibleSailors = entity.getSailorsInRange(sailors, MAX_MOVING_RANGE);

        if (possibleSailors.size() != 1)
            return false;

        possibleSailors.get(0).setTargetEntity(entity);
        entity.setSailor(possibleSailors.get(0));
        return true;
    }

    /**
     * Will associate a sailor to an oar only if the oar has only one possible sailor in range.
     * @param direction LEFT - RIGHT, the direction to place the sailors to.
     * @param maxSailorsToAssociate The maximum of sailors to proceed the rotation.
     * @return The number of sailors successfully associated.
     */
    public int associateTheOnlyOnePossibleToOar(int direction, int maxSailorsToAssociate){
        List<Oar> oarList = ship.getOarList(direction);
        int oarIndex = 0;
        int successfullyAssociated = 0;

        while(oarIndex < oarList.size() && successfullyAssociated < maxSailorsToAssociate){
            if (associateTheOnlyOnePossible(oarList.get(oarIndex)))
                successfullyAssociated++;
            oarIndex++;
        }

        return successfullyAssociated;
    }

    /**
     * Associating (if possible) the nearest sailor from the oar until the maxSailorsToAssociate is reach or
     * until the list of oar is reached.
     *
     * @param direction LEFT/RIGHT - The direction to oar.
     * @param maxSailorsToAssociate The maximum of sailors required to turn the ship.
     * @return The number of associates sailors.
     */
    public int associateNearestSailorToOar(int direction, int maxSailorsToAssociate){
        List<Oar> oarList = ship.getOarList(direction);
        int nbSuccessfulAssociation = 0;
        int i = 0;

        while(i < oarList.size() && nbSuccessfulAssociation < maxSailorsToAssociate) {
            Optional<Sailor> possibleSailor = oarList.get(i).getNearestSailor(sailors, MAX_MOVING_RANGE);
            if (possibleSailor.isPresent()) {
                oarList.get(i).setSailor(possibleSailor.get());
                possibleSailor.get().setTargetEntity(oarList.get(i));
                nbSuccessfulAssociation++;
            }
            i++;
        }
        return nbSuccessfulAssociation;
    }

    /**
     * Will apply the onlyOnePossible methods.
     */
    public void onlyOnePossibleAssociation(int oarWeight, boolean needRudder , boolean needSail){
        Rudder rudder = ship.getRudder();

        if (needRudder)
            hasAssociatedRudder = associateTheOnlyOnePossible(rudder);

        if(oarWeight > 0)
            nbAssociatedRightSailors = associateTheOnlyOnePossibleToOar(DirectionsManager.RIGHT,
                    Math.abs(oarWeight) - nbAssociatedRightSailors);

        if(oarWeight < 0)
            nbAssociatedLeftSailors = associateTheOnlyOnePossibleToOar(DirectionsManager.LEFT,
                    Math.abs(oarWeight) - nbAssociatedLeftSailors);
    }

    public void associateNearestSailorToOarEvenly(){
        Set<Sailor> sailorsCanGoLeft = getSailorNearToOar(DirectionsManager.LEFT);
        Set<Sailor> sailorsCanGoRight = getSailorNearToOar(DirectionsManager.RIGHT);

        System.out.println("-- Left right --");
        System.out.println("Left -> " + sailorsCanGoLeft);
        System.out.println("Right -> " +sailorsCanGoRight);

        List<Sailor> sailorsCanOnlyGoLeft = new ArrayList<>(sailorsCanGoLeft);
        sailorsCanOnlyGoLeft.removeAll(sailorsCanGoRight);

        List<Sailor> sailorsCanOnlyGoRight = new ArrayList<>(sailorsCanGoRight);
        sailorsCanOnlyGoRight.removeAll(sailorsCanGoLeft);

        System.out.println("-- Left right remove double--");
        System.out.println("Left -> " + sailorsCanOnlyGoLeft);
        System.out.println("Right -> " + sailorsCanOnlyGoRight);

        List<Sailor> sailorsCanGoBoth = sailorsCanGoLeft.stream()
                .filter(sailorsCanGoRight::contains)
                .toList();

        // If we can associate both oar
        if(Math.min(sailorsCanOnlyGoLeft.size(), sailorsCanOnlyGoRight.size()) > 0) {
            Sailor rightSailor = sailorsCanOnlyGoRight.get(0);
            Sailor leftSailor = sailorsCanOnlyGoLeft.get(0);
            Oar bestLeftOar = leftSailor.getNearestOar(ship.getAllOar(), MAX_MOVING_RANGE);
            Oar bestRightOar = rightSailor.getNearestOar(ship.getAllOar(), MAX_MOVING_RANGE);

            rightSailor.setTargetEntity(bestRightOar);
            bestRightOar.setSailor(rightSailor);
            nbAssociatedRightSailors++;

            leftSailor.setTargetEntity(bestLeftOar);
            bestLeftOar.setSailor(leftSailor);
            nbAssociatedLeftSailors++;

            associateNearestSailorToOarEvenly();
        }
    }

    public Set<Sailor> getSailorNearToOar(int direction){
        Set<Sailor> nearbySailors = new HashSet<>();

        for(Oar unassignedOar : getUnassignedOar(direction)){
            Optional<Sailor> nearbySailor =  unassignedOar.getNearestSailor(sailors, MAX_MOVING_RANGE);
            nearbySailor.ifPresent(nearbySailors::add);
        }
        return nearbySailors;
    }

    public List<Oar> getUnassignedOar(int direction){
        return ship.getOarList(direction).stream()
                .filter(Oar::isFree)
                .toList();
    }

}
