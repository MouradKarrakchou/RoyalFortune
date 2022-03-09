package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;

import java.util.*;

public class SailorMovementStrategy {
    private List<Sailor> sailors;
    private Ship ship;
    private Associations associations;
    private PreCalculator preCalculator;

    private int nbAssociatedLeftSailors = 0;
    private int nbAssociatedRightSailors = 0;
    private boolean hasAssociatedSail = false;
    private boolean hasAssociatedRudder =false;

    public static final int MAX_MOVING_RANGE = 5;

    public SailorMovementStrategy(List<Sailor> sailors, Ship ship, Associations associations,PreCalculator preCalculator){
        this.sailors = sailors;
        this.associations = associations;
        this.ship = ship;
        this.preCalculator=preCalculator;
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

        // We are associating (if possible) the nearest sailor to the Sail.
        if(needSail && !hasAssociatedSail){
            hasAssociatedSail = associateNearestSailor(ship.getSail());
        }


        // We are associating (if possible) the left or right oar to the nearest sailor according to the oarWeight.
        if(oarWeight > 0)
            nbAssociatedRightSailors = associateNearestSailorToOar(DirectionsManager.RIGHT,
                    Math.abs(oarWeight) - nbAssociatedRightSailors);
        if(oarWeight < 0)
            nbAssociatedLeftSailors = associateNearestSailorToOar(DirectionsManager.LEFT,
                    Math.abs(oarWeight) - nbAssociatedLeftSailors);

        if (preCalculator.needSailorToOarToCheckpoint(Math.min(nbAssociatedLeftSailors,nbAssociatedRightSailors)*2+2))
        associateNearestSailorToOarEvenlyRecursive();
        if (preCalculator.needSailorToOarToCheckpoint(Math.min(nbAssociatedLeftSailors,nbAssociatedRightSailors)*2+2))
            associateNearestSailorToOarEvenly();

        System.out.println("End of turn, nbAssociation -> " + getNbAssociations());
        return new SailorPlacement(nbAssociatedLeftSailors, nbAssociatedRightSailors, hasAssociatedRudder,hasAssociatedSail);
    }

    /**
     * Will associate (if possible) the nearest sailors from the entity to it.
     * @param entity The entity to be associated.
     * @return The association has been proceeded.
     */
    public boolean associateNearestSailor(Entities entity){
        if(!associations.isFree(entity))
            return false;

        Optional<Sailor> nearestSailor = entity.getNearestSailor(sailors, MAX_MOVING_RANGE, associations);

        if (nearestSailor.isEmpty())
            return false;

        associations.addAssociation(nearestSailor.get(), entity);
        return true;
    }

    /**
     * Will associate the sailor only if the entity has only one possible sailor in range.
     * @param entity The entity to associate.
     * @return Boolean, the association can be made.
     */
    public boolean associateTheOnlyOnePossible(Entities entity){
        List<Sailor> possibleSailors = entity.getSailorsInRange(sailors, MAX_MOVING_RANGE, associations);

        if (possibleSailors.size() != 1)
            return false;

        associations.addAssociation(possibleSailors.get(0), entity);
        return true;
    }

    /**
     * Will associate a sailor to an oar only if the oar has only one possible sailor in range.
     * @param direction LEFT - RIGHT, the direction to place the sailors to.
     * @param maxSailorsToAssociate The maximum of sailors to proceed the rotation.
     * @return The number of sailors successfully associated.
     */
    public int associateTheOnlyOnePossibleToOar(int direction, int maxSailorsToAssociate){
        List<Oar> oarList = ship.getOarList(direction, associations);
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
        List<Oar> oarList = ship.getOarList(direction, associations);
        int nbSuccessfulAssociation = 0;
        int i = 0;

        while(i < oarList.size() && nbSuccessfulAssociation < maxSailorsToAssociate) {
            Optional<Sailor> possibleSailor = oarList.get(i).getNearestSailor(sailors, MAX_MOVING_RANGE, associations);
            if (possibleSailor.isPresent()) {
                associations.addAssociation(possibleSailor.get(), oarList.get(i));
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
        if (needRudder){
            Rudder rudder = ship.getRudder();
            hasAssociatedRudder = associateTheOnlyOnePossible(rudder);
        }

        if(needSail){
            Sail sail = ship.getSail();
            hasAssociatedSail = associateTheOnlyOnePossible(sail);
        }

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

        List<Sailor> sailorsCanGoBoth = sailorsCanGoLeft.stream()
                .filter(sailorsCanGoRight::contains)
                .toList();
        if (sailorsCanGoBoth.size() > 2){
            Sailor rightSailor = sailorsCanGoBoth.get(0);
            Oar bestRightOar = rightSailor.getNearestOar(ship.getOarList(DirectionsManager.RIGHT,associations), associations);
            associations.addAssociation(rightSailor, bestRightOar);
            nbAssociatedRightSailors++;

            Sailor leftSailor = sailorsCanGoBoth.get(1);
            Oar bestLeftOar = leftSailor.getNearestOar(ship.getOarList(DirectionsManager.LEFT,associations),associations);
            associations.addAssociation(leftSailor, bestLeftOar);
            nbAssociatedLeftSailors++;
            if (preCalculator.needSailorToOarToCheckpoint(Math.min(nbAssociatedLeftSailors,nbAssociatedRightSailors)*2+2))
                associateNearestSailorToOarEvenly();
        }
    }



    public void associateNearestSailorToOarEvenlyRecursive(){
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
            Oar bestLeftOar = leftSailor.getNearestOar(ship.getAllOar(), associations);
            Oar bestRightOar = rightSailor.getNearestOar(ship.getAllOar(), associations);

            associations.addAssociation(rightSailor, bestRightOar);
            nbAssociatedRightSailors++;

            associations.addAssociation(leftSailor, bestLeftOar);
            nbAssociatedLeftSailors++;
            if (preCalculator.needSailorToOarToCheckpoint(Math.min(nbAssociatedLeftSailors,nbAssociatedRightSailors)*2+2))
                associateNearestSailorToOarEvenlyRecursive();
        }
    }

    public Set<Sailor> getSailorNearToOar(int direction){
        Set<Sailor> nearbySailors = new HashSet<>();

        for(Oar unassignedOar : ship.getOarList(direction, associations)){
            nearbySailors.addAll(unassignedOar.getSailorsInRange(sailors, MAX_MOVING_RANGE, associations));
        }
        return nearbySailors;
    }
}
