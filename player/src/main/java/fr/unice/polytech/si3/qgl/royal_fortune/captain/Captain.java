package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Captain {
    private final Ship ship;
    private final ArrayList<Sailor> sailors;
    private final ArrayList<Action> roundActions;

    public Captain(Ship ship, ArrayList<Sailor> sailors){
        this.ship = ship;
        this.sailors = sailors;
        roundActions = new ArrayList<>();
    }

    /**
     * roundDecisions will decide what the sailors have to do regarding the directions in order to go to the right path
     *
     * @param directionsManager which allows to do the calculus in order to decide where the ship has to go
     * @return String of the actions the sailors must do
     */
    public String roundDecisions(DirectionsManager directionsManager) {

        disassociate();
        roundActions.clear();
        directionsManager.updateCheckPoint();
        double angleMove = directionsManager.getAngleMove();
        double angleCone = directionsManager.getAngleCone();

        if(!(directionsManager.isConeTooSmall(angleMove, angleCone)||directionsManager.isInCone(angleMove, angleCone))) {
            associateSailorToOar(angleMove);
        }

        associateSailorToOarEvenly();
        askSailorsToMove();
        askSailorsToOar();

        return orders();
    }

    /**
     * Build the string of actions that the sailors must do
     * @return the string of actions
     */
    String orders() {
        StringBuilder actionsToDo = new StringBuilder();
        for(Action action : roundActions)
            actionsToDo.append(action.toString()).append(",");
        String out = actionsToDo.substring(0, actionsToDo.length() - 1);
        return "[" + out + "]";
    }

    /**
     * Remove the target entities from the sailors
     */
    private void disassociate() {
        sailors.forEach(sailor -> sailor.setTargetEntity(null));
    }

    /**
     * Captain will associate the best number of sailors to proceed a rotation of the given angle.
     * @param orientation The rotation of the given angle.
     */
    public void associateSailorToOar(double orientation){
        int maxSailors = Math.abs((int) Math.ceil(orientation/(Math.PI / 4)));
        ArrayList<Oar> oarList = ship.getOarList(orientation < 0 ? "right" : "left");
        int i = 0;

        // We continue associating until we run out of sailors or oars
        while(i < oarList.size() && i < sailors.size() && i < maxSailors){
            Oar oar = oarList.get(i);
            System.out.println(oar);
            sailors.get(i).setTargetEntity(oar);
            oar.setSailor(sailors.get(i));
            i++;
        }
    }

    /**
     * Associate the same amount of sailors to the left oars and the right oars of the ship.
     */
    public void associateSailorToOarEvenly(){
        ArrayList<Oar> leftOarList = ship.getOarList("left");
        ArrayList<Oar> rightOarList = ship.getOarList("right");
        int oarIndex = 0;
        int sailorIndex = 0;
        ArrayList<Sailor> listOfUnassignedSailors=(ArrayList<Sailor>) sailors.stream().filter(sailor-> sailor.getTargetEntity()==null).collect(Collectors.toList());

        // We continue associating until we run out of sailors or oars
        while(oarIndex < leftOarList.size() && oarIndex < rightOarList.size() && sailorIndex + 1 < listOfUnassignedSailors.size()){
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
     * Ask all sailors associated to an Entity to move to
     * If the sailor is already on the Entity, sailor will not move
     * This method update list of Action (roundActions)
     */
    public void askSailorsToMove(){
        roundActions.addAll(sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> !sailor.isOnTheTargetEntity())
                .map(Sailor::moveToTarget)
                .collect(Collectors.toList()));
    }

    /**
     * Ask all sailors associated to an Oar to oar
     * This method update list of Action (roundActions)
     */
    public void askSailorsToOar(){
        roundActions.addAll(sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(Sailor::isOnTheTargetEntity)
                .map(Sailor::oar)
                .collect(Collectors.toList()));
    }

    public ArrayList<Action> getRoundActions(){
        return roundActions;
    }
}
