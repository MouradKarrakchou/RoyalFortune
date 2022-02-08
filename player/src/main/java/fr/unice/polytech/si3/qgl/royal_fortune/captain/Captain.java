package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Shape;

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
        desasosiate();
        roundActions.clear();
        if(isConeTooSmall()||isInCone()) {
                associateSailorToOarEvenly();
                askSailorsToMove();
                askSailorsToOar();
        }
        else {
            double angleMove = getAngleMove();
                associateSailorToOar(angleMove);
                associateSailorToOarEvenly();
                askSailorsToMove();
                askSailorsToOar();}


        StringBuilder actionsToDo = new StringBuilder();
        for(Action action : roundActions)
            actionsToDo.append(action.toString()).append(",");
        String out = actionsToDo.substring(0, actionsToDo.length() - 1);
        return "[" + out + "]";
    }

    private void desasosiate() {
        sailors.stream().forEach(sailor -> sailor.setTargetEntity(null));
    }

    /**
     * Captain will associate the best number of sailors to proceed a rotation of the given angle.
     * @param orientation The rotation of the given angle.
     */
    public void associateSailorToOar(double orientation){
        int maxSailors = Math.abs((int) Math.ceil(orientation/(Math.PI / 4)));
        ArrayList<Oar> oarList = ship.getOarList(orientation > 0 ? "right" : "left");
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

        // We continue associating until we run out of sailors or oars
        while(oarIndex < leftOarList.size() && oarIndex < rightOarList.size() && sailorIndex + 1 < sailors.size()){
            Oar leftOar = leftOarList.get(oarIndex);
            Oar rightOar = rightOarList.get(oarIndex);
            sailors.get(sailorIndex).setTargetEntity(leftOar);
            leftOar.setSailor(sailors.get(sailorIndex));
            sailors.get(++sailorIndex).setTargetEntity(rightOar);
            rightOar.setSailor(sailors.get(sailorIndex));
            sailorIndex++;
            oarIndex++;
        }
    }

    /**
     * Check if every sailor are in place to rotate the boat.
     * @return true/false
     */
    public boolean sailorsAreInPlace(){
        long nbLeftSailors = sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> sailor.getTargetEntity() instanceof Oar)
                .filter(sailor -> ((Oar) sailor.getTargetEntity()).isLeft())
                .count();
        long nbRightSailors = sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> sailor.getTargetEntity() instanceof Oar)
                .filter(sailor -> !((Oar) sailor.getTargetEntity()).isLeft())
                .count();
        return nbLeftSailors == nbRightSailors;
    }

    /**
     * Check if every sailor are in place to make the ship move straight forward.
     * @return true/false
     */
    public boolean sailorsAreInPlace(double orientation){
        long nbSailorsInLeftOar = sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> sailor.getTargetEntity() instanceof Oar)
                .filter(sailor -> ((Oar) sailor.getTargetEntity()).isLeft())
                .count();

        long nbSailorsInRightOar = sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() != null)
                .filter(sailor -> sailor.getTargetEntity() instanceof Oar)
                .filter(sailor -> !((Oar) sailor.getTargetEntity()).isLeft())
                .count();
        if (nbSailorsInLeftOar == nbSailorsInRightOar)
            return false;
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
        double angleShip=ship.getPosition().getOrientation();
        Shape shape=goal.getCheckPoints().get(0).getShape();
        double radius =((Circle) shape).getRadius();


        double distanceSCX = goal.getCheckPoints().get(0).getPosition().getX() - ship.getPosition().getX();
        double distanceSCY = goal.getCheckPoints().get(0).getPosition().getY() - ship.getPosition().getY();
        double distanceSC = Math.sqrt(Math.pow(distanceSCX,2) + Math.pow(distanceSCY,2));

        double angleCone = Math.atan(radius / distanceSC);


        double num = distanceSCX*Math.cos(angleShip) + distanceSCY*Math.sin(angleShip);

        double angleMove = Math.acos(num / distanceSC);
        //double angleMove = Math.PI-Math.atan(distanceSCX / distanceSCY)-angleShip;


        while(angleMove > Math.PI){
            angleMove -= 2*Math.PI;
        }

        while(angleMove < -Math.PI){
            angleMove += 2*Math.PI;
        }

        double angles[] = {angleMove, angleCone};

        return angles;
    }

    boolean isInCone() {
        return (Math.abs(getAngleMove()) <= getAngleCone());
    }

    boolean isConeTooSmall() {
        return (Math.abs(getAngleMove() + getAngleCone()) < Math.PI/4);
    }

    double getAngleMove() { return angleCalculator()[0]; }

    double getAngleCone() { return angleCalculator()[1]; }

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
