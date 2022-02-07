package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Captain {
    private final Ship ship;
    private final Goal goal;
    private final ArrayList<Sailor> sailors;
    private final ArrayList<Action> roundActions;

    public Captain(Ship ship, ArrayList<Sailor> sailors, Goal goal){
        this.ship = ship;
        this.sailors = sailors;
        this.goal = goal;
        roundActions = new ArrayList<>();
    }

    /**
     * Associate the maximum of sailors to a specific oar orientation.
     * @param orientation The orientation to target ("left" or "right")
     */
    public void associateSailorToOar(String orientation){
        ArrayList<Oar> oarList = ship.getOarList(orientation);
        int i = 0;

        // We continue associating until we run out of sailors or oars
        while(i < oarList.size() && i < sailors.size()){
            Oar oar = oarList.get(i);
            sailors.get(i).setTargetEntity(oar);
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

        // We continue associating until we run out of sailors or oars
        while(oarIndex < leftOarList.size() && oarIndex < rightOarList.size() && sailorIndex + 1 < sailors.size()){
            Oar leftOar = leftOarList.get(oarIndex);
            Oar rightOar = rightOarList.get(oarIndex);
            sailors.get(sailorIndex).setTargetEntity(leftOar);
            sailors.get(++sailorIndex).setTargetEntity(rightOar);
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

    double[] angleCalculator() {
        double radius = ((Circle) goal.getCheckPoints().get(0).getShape()).getRadius();

        double distanceSCY = goal.getCheckPoints().get(0).getPosition().getY() - ship.getPosition().getY();
        double distanceSCX = goal.getCheckPoints().get(0).getPosition().getX() - ship.getPosition().getX();
        double distanceSC = Math.sqrt(Math.pow(distanceSCY,2) + Math.pow(distanceSCX,2));

        double angleCone = Math.atan(radius / distanceSC);


        double num = distanceSCY*Math.cos(ship.getPosition().getOrientation()) + distanceSCX*Math.sin(ship.getPosition().getOrientation());

        double angleMove = Math.acos(num / distanceSC);

        double angles[] = {angleCone, angleMove};

        return angles;
    }

    public ArrayList<Action> getRoundActions(){
        return roundActions;
    }
}
