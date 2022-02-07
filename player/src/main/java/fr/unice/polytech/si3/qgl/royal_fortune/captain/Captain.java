package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public String roundDecisions() {
        if(isInCone()) {
            if(sailorsAreInplace())
                askSailorsToOar();
            else
                associateSailorToOarEvenly();
        }

        else {
            double angleMove = getAngleMove();
            if(sailorsAreInplace(angleMove)) {
                askSailorsToOar();
            }
            else
                associateSailorToOar(angleMove);
        }

        String actionsToDo = "";
        for(Action action : roundActions)
            actionsToDo += action.toString();
        return actionsToDo;
    }

    /**
     * Captain will associate the best number of sailors to proceed a rotation of the given angle.
     * @param orientation The rotation of the given angle.
     */
    public void associateSailorToOar(double orientation){
        int maxSailors = Math.abs((int) Math.round(orientation/(Math.PI / 4)));
        ArrayList<Oar> oarList = ship.getOarList(orientation > 0 ? "right" : "left");
        int i = 0;

        // We continue associating until we run out of sailors or oars
        while(i < oarList.size() && i < sailors.size() && i < maxSailors){
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
     * Check if every sailor are in place to rotate the boat.
     * @return true/false
     */
    public boolean sailorsAreInPlace(){
        Stream<Sailor> oarList =  sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> sailor.getTargetEntity() instanceof Oar);
        return oarList.filter(sailor -> ((Oar) sailor.getTargetEntity()).isLeft()) == oarList.filter(sailor -> !((Oar) sailor.getTargetEntity()).isLeft());
    }

    /**
     * Check if every sailor are in place to make the ship move straight forward.
     * @return true/false
     */
    public boolean sailorsAreInPlace(double orientation){
        Stream<Sailor> oarList =  sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> sailor.getTargetEntity() instanceof Oar);

        long nbSailorsInLeftOar = oarList.filter(sailor -> ((Oar) sailor.getTargetEntity()).isLeft()).count();
        long nbSailorsInRightOar = oarList.filter(sailor -> !((Oar) sailor.getTargetEntity()).isLeft()).count();

        return (nbSailorsInLeftOar > nbSailorsInRightOar) == orientation < 0;
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

        while(angleMove > Math.PI){
            angleMove -= 2*Math.PI;
        }

        while(angleMove < Math.PI){
            angleMove += 2*Math.PI;
        }

        double angles[] = {angleMove, angleCone};

        return angles;
    }

    boolean isInCone() {
        return (Math.abs(getAngleMove()) <= getAngleCone());
    }

    double getAngleMove() { return angleCalculator()[0]; }

    double getAngleCone() { return angleCalculator()[1]; }

    /**
     * Ask all sailors associated to an Oar to oar
     * This method update list of Action (roundActions)
     */
    public void askSailorsTeOar(){
        roundActions.addAll(sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(Sailor::isOnTheTargetEntity)
                .map(Sailor::oar)
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

        while(angleMove > Math.PI){
            angleMove -= 2*Math.PI;
        }

        while(angleMove < Math.PI){
            angleMove += 2*Math.PI;
        }

        double angles[] = {angleMove, angleCone};

        return angles;
    }

    boolean isInCone(double angleMove, double angleCone) {
        return (Math.abs(angleMove) <= angleCone);
    }

    double getAngleMove() { return angleCalculator()[0]; }

    double getAngleCone() { return angleCalculator()[1]; }

    public ArrayList<Action> getRoundActions(){
        return roundActions;
    }
}
