package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.PreCalculator;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;

import java.util.*;

public class SailorMovementStrategy {
    private final List<Sailor> sailors;
    private final Ship ship;
    private final Associations associations;
    private final PreCalculator preCalculator;

    private SailorPlacement currentSailorPlacement;

    public static final int MAX_MOVING_RANGE = 5;

    public SailorMovementStrategy(List<Sailor> sailors, Ship ship, Associations associations,PreCalculator preCalculator){
        this.sailors = sailors;
        this.associations = associations;
        this.ship = ship;
        this.preCalculator = preCalculator;
        this.currentSailorPlacement = new SailorPlacement();
    }

    /**
     * Will try to associate as best the requested parameters
     * Priority order : Rudder, Sail, Oar.
     *
     * @return The SailorPlacement actually made by the SailorPlacementStrategy.
     */
    public SailorPlacement askPlacement(SailorPlacement requestedSailorPlacement) {
        currentSailorPlacement = new SailorPlacement();

        // We are associating every Sailor to a starving entity.
        continueAssociatingStarvingEntities(requestedSailorPlacement);

        // We are associating (if possible) the nearest sailor to the Rudder.
        if(requestedSailorPlacement.hasRudder()){
            // If an association has been made.
            if(associateNearestSailor(ship.getRudder())){
                requestedSailorPlacement.setRudder(false);
                currentSailorPlacement.setRudder(true);
            }
        }

        // We are associating (if possible) the nearest sailor to the Sail.
        if(requestedSailorPlacement.hasSail()){
            // If an association has been made.
            if(associateNearestSailor(ship.getSail())){
                requestedSailorPlacement.setSail(false);
                currentSailorPlacement.setSail(true);
            }
        }

        // We are associating (if possible) the left or right oar to the nearest sailor according to the oarWeight.
        associateNearestSailorToOars(requestedSailorPlacement);

        if (preCalculator.needSailorToOarToCheckpoint(
                Math.min(currentSailorPlacement.getNbLeftSailors(),
                        currentSailorPlacement.getNbLeftSailors()) *2+2)){
            associateSpecialistSailorToOarEvenly();
        }

        if (preCalculator.needSailorToOarToCheckpoint(
                Math.min(currentSailorPlacement.getNbLeftSailors(),
                        currentSailorPlacement.getNbLeftSailors()) *2+2)){
            associateSpecialistSailorAndSailorToOarEvenly();
        }

        if (preCalculator.needSailorToOarToCheckpoint(
                Math.min(currentSailorPlacement.getNbLeftSailors(),
                        currentSailorPlacement.getNbLeftSailors()) *2+2)){
            associateSailorsToOarEvenly();
        }

        return currentSailorPlacement;
    }

    /**
     * Will associate (if possible) the nearest sailors from the entity to it.
     * @param entity The entity to be associated.
     * @return True/False - The association has been proceeded.
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
     * Will associate (if possible) a Sailor to a starving entity.
     * A starving entity is an entity having only one sailor nearby.
     * If the entity isn't starving no association will be proceeded.
     *
     * @param entity The entity to associate.
     * @return Boolean, the association can be made.
     */
    public boolean associateStarvingEntity(Entities entity){
        List<Sailor> possibleSailors = entity.getSailorsInRange(sailors, MAX_MOVING_RANGE, associations);

        if (possibleSailors.size() != 1)
            return false;

        associations.addAssociation(possibleSailors.get(0), entity);
        return true;
    }

    /**
     * Will associate a sailor to an oar only if the oar is starving.
     *
     * @param direction LEFT - RIGHT, the direction to place the sailors to.
     * @return If a sailor has been associated.
     */
    public boolean associateStarvingOar(int direction){
        List<Oar> oarList = ship.getOarList(direction, associations);
        int oarIndex = 0;

        while(oarIndex < oarList.size()){
            if (associateStarvingEntity(oarList.get(oarIndex)))
                return true;
            oarIndex++;
        }
        return false;
    }

    /**
     * Associating (if possible) the nearest sailor from the oars until the number of requested sailor is reached or
     * until the list of oar is reached.
     *
     * @param requestedSailorPlacement the requested sailor placement.
     */
    public void associateNearestSailorToOars(SailorPlacement requestedSailorPlacement){
        int direction = requestedSailorPlacement.getOarWeight() > 0 ? DirectionsManager.RIGHT : DirectionsManager.LEFT;
        List<Oar> oarList = ship.getOarList(direction, associations);
        int i = 0;

        while (i < oarList.size() && requestedSailorPlacement.getOarWeight() != 0) {
            Optional<Sailor> possibleSailor = oarList.get(i).getNearestSailor(sailors, MAX_MOVING_RANGE, associations);
            if (possibleSailor.isPresent()) {
                associations.addAssociation(possibleSailor.get(), oarList.get(i));
                if (direction == DirectionsManager.LEFT)
                    currentSailorPlacement.incrementNbLeftSailor(1);
                else
                    currentSailorPlacement.incrementNbRightSailor(1);
                requestedSailorPlacement.incrementOarWeight(-direction);
            }
            i++;
        }
    }

    /**
     * Will associate (if possible) a Sailor to a starving entity.
     * A starving entity is an entity having only one sailor nearby.
     */
    public boolean associateStarvingEntities(SailorPlacement requestedSailorPlacement){
        // If we need a rudder.
        if (requestedSailorPlacement.hasRudder()){
            Rudder rudder = ship.getRudder();
            // If an association has been made
            if(associateStarvingEntity(rudder)){
                requestedSailorPlacement.setRudder(false);
                currentSailorPlacement.setRudder(true);
                return true; // We are returning to be sure to keep the association priority.
            }
        }

        // If we need a sail.
        if (requestedSailorPlacement.hasSail()){
            Sail sail = ship.getSail();
            // If an association has been made.
            if(associateStarvingEntity(sail)){
                requestedSailorPlacement.setSail(false);
                currentSailorPlacement.setSail(true);
                return true; // We are returning to be sure to keep the association priority.
            }
        }

        // If we need at least on right oar.
        if(requestedSailorPlacement.getOarWeight() > 0) {
            // If an association has been made.
            if (associateStarvingOar(DirectionsManager.RIGHT)){
                currentSailorPlacement.incrementNbRightSailor(1);
                requestedSailorPlacement.incrementOarWeight(-1);
                return true; // We are returning to be sure to keep the association priority.
            }
        }

        // If we need at least on left oar.
        if(requestedSailorPlacement.getOarWeight() < 0) {
            // If an association has been made.
            if (associateStarvingOar(DirectionsManager.LEFT)){
                currentSailorPlacement.incrementNbLeftSailor(1);
                requestedSailorPlacement.incrementOarWeight(1);
                return true; // We are returning to be sure to keep the association priority.
            }
        }

        return false;
    }

    /**
     * RECURSIVE
     * If a sailor is specialist, (meaning they can only go to left or right oars) associate
     * specific oar to this sailor and put on the other side sailor who can go both.
     *
     * If there is no two more specialist or normal sailor, the recursive call stops.
     */
    public void associateSpecialistSailorAndSailorToOarEvenly(){
        Set<Sailor> sailorsCanGoLeft = getSailorNearToOar(DirectionsManager.LEFT);
        Set<Sailor> sailorsCanGoRight = getSailorNearToOar(DirectionsManager.RIGHT);

        List<Sailor> sailorsCanGoBoth = sailorsCanGoLeft.stream()
                .filter(sailorsCanGoRight::contains)
                .toList();

        if (sailorsCanGoBoth.isEmpty())
            return;

        List<Sailor> sailorsCanOnlyGoLeft = new ArrayList<>(sailorsCanGoLeft);
        sailorsCanOnlyGoLeft.removeAll(sailorsCanGoRight);

        List<Sailor> sailorsCanOnlyGoRight = new ArrayList<>(sailorsCanGoRight);
        sailorsCanOnlyGoRight.removeAll(sailorsCanGoLeft);


        if (!sailorsCanOnlyGoLeft.isEmpty()){
            Sailor specialistSailor = sailorsCanOnlyGoLeft.get(0);
            Oar nearestLeftOar = specialistSailor.getNearestOar(ship.getOarList(DirectionsManager.LEFT, associations), associations);
            Sailor normalSailor = sailorsCanGoBoth.get(0);
            Oar nearestRightOar = specialistSailor.getNearestOar(ship.getOarList(DirectionsManager.RIGHT, associations), associations);

            associations.addAssociation(normalSailor, nearestRightOar);
            associations.addAssociation(specialistSailor, nearestLeftOar);
            currentSailorPlacement.incrementNbRightSailor(1);
            currentSailorPlacement.incrementNbLeftSailor(1);

            if (preCalculator.needSailorToOarToCheckpoint(Math.min(currentSailorPlacement.getNbLeftSailors(),
                    currentSailorPlacement.getNbLeftSailors()) *2+2)){
                associateSpecialistSailorAndSailorToOarEvenly();
            }
            return;
        }

        if (!sailorsCanOnlyGoRight.isEmpty()){
            Sailor specialistSailor = sailorsCanOnlyGoRight.get(0);
            Oar nearestRightOar = specialistSailor.getNearestOar(ship.getOarList(DirectionsManager.RIGHT, associations), associations);
            Sailor normalSailor = sailorsCanGoBoth.get(0);
            Oar nearestLeftOar = specialistSailor.getNearestOar(ship.getOarList(DirectionsManager.LEFT, associations), associations);

            associations.addAssociation(specialistSailor, nearestRightOar);
            associations.addAssociation(normalSailor, nearestLeftOar);
            currentSailorPlacement.incrementNbRightSailor(1);
            currentSailorPlacement.incrementNbLeftSailor(1);

            if (preCalculator.needSailorToOarToCheckpoint(Math.min(currentSailorPlacement.getNbLeftSailors(),
                    currentSailorPlacement.getNbLeftSailors()) *2+2)){
                associateSpecialistSailorAndSailorToOarEvenly();
            }
        }
    }

    /**
     * RECURSIVE
     * Will associate two sailors until we run out of sailors.
     */
    public void associateSailorsToOarEvenly(){
        Set<Sailor> sailorsCanGoLeft = getSailorNearToOar(DirectionsManager.LEFT);
        Set<Sailor> sailorsCanGoRight = getSailorNearToOar(DirectionsManager.RIGHT);

        List<Sailor> sailorsCanGoBoth = sailorsCanGoLeft.stream()
                .filter(sailorsCanGoRight::contains)
                .toList();

        if (sailorsCanGoBoth.isEmpty())
            return;

        if (sailorsCanGoBoth.size() >= 2){
            Sailor rightSailor = sailorsCanGoBoth.get(0);
            Oar bestRightOar = rightSailor.getNearestOar(ship.getOarList(DirectionsManager.RIGHT,associations), associations);
            associations.addAssociation(rightSailor, bestRightOar);
            currentSailorPlacement.incrementNbRightSailor(1);

            Sailor leftSailor = sailorsCanGoBoth.get(1);
            Oar bestLeftOar = leftSailor.getNearestOar(ship.getOarList(DirectionsManager.LEFT,associations), associations);
            associations.addAssociation(leftSailor, bestLeftOar);
            currentSailorPlacement.incrementNbLeftSailor(1);

            if (preCalculator.needSailorToOarToCheckpoint(Math.min(currentSailorPlacement.getNbLeftSailors(),
                    currentSailorPlacement.getNbLeftSailors()) *2+2)){
                associateSailorsToOarEvenly();
            }
        }
    }

    /**
     * RECURSIVE
     * If two opposite sailors are specialist (meaning they can only go to left or right oars) they will both join their nearest
     * specific oar and call the associateSpecialistSailorToOar again.
     * If there is no two opposite sailors are specialist the recursive method stops.
     */
    public void associateSpecialistSailorToOarEvenly(){
        Set<Sailor> sailorsCanGoLeft = getSailorNearToOar(DirectionsManager.LEFT);
        Set<Sailor> sailorsCanGoRight = getSailorNearToOar(DirectionsManager.RIGHT);

        List<Sailor> sailorsCanOnlyGoLeft = new ArrayList<>(sailorsCanGoLeft);
        sailorsCanOnlyGoLeft.removeAll(sailorsCanGoRight);

        List<Sailor> sailorsCanOnlyGoRight = new ArrayList<>(sailorsCanGoRight);
        sailorsCanOnlyGoRight.removeAll(sailorsCanGoLeft);

        // If we have at least one sailor who can only go left and one sailor who can only go right.
        if(Math.min(sailorsCanOnlyGoLeft.size(), sailorsCanOnlyGoRight.size()) > 0) {
            Sailor rightSailor = sailorsCanOnlyGoRight.get(0);
            Sailor leftSailor = sailorsCanOnlyGoLeft.get(0);
            Oar bestLeftOar = leftSailor.getNearestOar(ship.getAllOar(), associations);
            Oar bestRightOar = rightSailor.getNearestOar(ship.getAllOar(), associations);

            associations.addAssociation(rightSailor, bestRightOar);
            currentSailorPlacement.incrementNbRightSailor(1);

            associations.addAssociation(leftSailor, bestLeftOar);
            currentSailorPlacement.incrementNbLeftSailor(1);

            // If the pre-calculator thinks adding two more sailor is not worth.
            if (preCalculator.needSailorToOarToCheckpoint(Math.min(currentSailorPlacement.getNbLeftSailors(),
                    currentSailorPlacement.getNbLeftSailors()) *2+2)){
                associateSpecialistSailorToOarEvenly();
            }
        }
    }

    public Set<Sailor> getSailorNearToOar(int direction){
        Set<Sailor> nearbySailors = new HashSet<>();

        for(Oar unassignedOar : ship.getOarList(direction, associations)){
            nearbySailors.addAll(unassignedOar.getSailorsInRange(sailors, MAX_MOVING_RANGE, associations));
        }
        return nearbySailors;
    }

    /**
     * We are associating every starving entities.
     * until every entity has more than 1 sailor nearby.
     */
    public void continueAssociatingStarvingEntities(SailorPlacement requestedSailorPlacement){
        while(associateStarvingEntities(requestedSailorPlacement));
    }
}
