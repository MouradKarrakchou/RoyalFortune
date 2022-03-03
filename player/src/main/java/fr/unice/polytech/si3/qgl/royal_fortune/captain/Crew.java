package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Crew {
    private final List<Sailor> sailors;
    private final Ship ship;
    private final PreCalculator preCalculator;
    public Crew(List<Sailor> sailors,Ship ship,PreCalculator preCalculator){
        this.sailors=sailors;
        this.ship=ship;
        this.preCalculator=preCalculator;
    }
    /**
     * Make the sailor go to the oar and use them.
     * @return
     */
    public List<Action> makeBoatMove() {
        associateSailorToOarEvenly();
        sailorsMove();
        return(sailorsOar());
    }
    /**
     * Captain will associate the best number of sailors to proceed a rotation of the given angle.
     * @param numberOfSailors The rotation of the given angle.
     * @param whereToTurn 1 to turn right/ -1 to turn left
     */
    public int associateSailorToOar(int numberOfSailors,int whereToTurn){
        List<Oar> oarList = ship.getOarList(whereToTurn > 0 ? "right" : "left");
        int i = 0;

        // We continue associating until we run out of sailors or oars
        while(i < numberOfSailors){
            Oar oar = oarList.get(i);
            sailors.get(i).setTargetEntity(oar);
            oar.setSailor(sailors.get(i));
            i++;
        }
        return numberOfSailors;
    }
    /**
     * Associate the same amount of sailors to the left oars and the right oars of the ship.
     */
    public void associateSailorToOarEvenly(){
        List<Oar> leftOarList = ship.getOarList("left");
        List<Oar> rightOarList = ship.getOarList("right");
        int oarIndex = 0;
        int sailorIndex = 0;
        List<Sailor> listOfUnassignedSailors = getlistOfUnassignedSailors();
        int numberOfSailorNeeded=preCalculator.numberOfSailorToOarEvenly(getlistOfUnassignedSailors().size());
        // We continue associating until we run out of sailors or oars
        while (sailorIndex<numberOfSailorNeeded){
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
    public List<Action> sailorMoveToRudder(){
        List<Action> roundActions=new ArrayList<>();
        Rudder rudder = ship.getRudder();
        if (rudder == null)
            return roundActions;

        Optional<Sailor> sailorToMove = sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() == null)
                .min(Comparator.comparingInt(sailor -> sailor.getDistanceToEntity(rudder)));

        if(sailorToMove.isPresent()) {
            Sailor s = sailorToMove.get();
            s.setTargetEntity(rudder);
            roundActions.add(s.moveToTarget());
        }
        return roundActions;
    }

    /**
     * Ask all sailors associated to an Entity to move to
     * If the sailor is already on the Entity, sailor will not move
     * This method update list of Action (roundActions)
     * @return
     */
    public List<Action> sailorsMove(){
        List<Action> roundActions=new ArrayList<>();

        roundActions.addAll(sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> !sailor.isOnTheTargetEntity())
                .map(Sailor::moveToTarget)
                .toList());
        return roundActions;
    }

    /**
     * Ask all sailors associated to an Oar to oar
     * This method update list of Action (roundActions)
     */
    public List<Action> sailorsOar(){
        List<Action> roundActions=new ArrayList<>();
        roundActions.addAll(sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor-> sailor.getTargetEntity().isOar())
                .filter(Sailor::isOnTheTargetEntity)
                .map(Sailor::oar)
                .toList());
        return roundActions;
    }

    /**
     * Ask a sailor to turn with the rudder
     * This method update list of Action (roundActions)
     * @param rotationRudder
     */
    public List<Action> sailorsTurnWithRudder(double rotationRudder) {
        List<Action> roundActions=new ArrayList<>();
        roundActions.addAll(sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() instanceof Rudder)
                .filter(Sailor::isOnTheTargetEntity)
                .map(sailor -> sailor.turnWithRudder(rotationRudder))
                .toList());
        return roundActions;

    }
    /**
     * Give The list of Sailors that are not assigned to an entitie;
     * @return
     */
    public List<Sailor> getlistOfUnassignedSailors(){
        return((ArrayList<Sailor>) sailors.stream()
                .filter(sailor-> sailor.getTargetEntity()==null)
                .collect(Collectors.toList()));
    }

}
