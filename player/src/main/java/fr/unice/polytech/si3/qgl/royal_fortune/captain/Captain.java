package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Captain {
    private final Ship ship;
    private final Goal goal;
    private final List<Sailor> sailors;
    private final ArrayList<Action> roundActions;
    private final DirectionsManager directionsManager;
    final Logger logger = Logger.getLogger(Captain.class.getName());

    public Captain(Ship ship, List<Sailor> sailors, Goal goal){
        this.ship = ship;
        this.sailors = sailors;
        this.goal = goal;
        roundActions = new ArrayList<>();
        directionsManager=new DirectionsManager(ship,goal);
    }

    public String roundDecisions() {

        disassociate();
        roundActions.clear();
        updateCheckPoint();
        double angleMove = directionsManager.getAngleMove();
        double angleCone = directionsManager.getAngleCone();

        if (!directionsManager.isConeTooSmall(angleMove, angleCone) && !directionsManager.isInCone(angleMove, angleCone)) {
            associateSailorToOar(angleMove);
        }
        associateSailorToOarEvenly();
        askSailorsToMove();
        askSailorsToOar();


        StringBuilder actionsToDo = new StringBuilder();
        for(Action action : roundActions)
            actionsToDo.append(action.toString()).append(",");
        String out = actionsToDo.substring(0, actionsToDo.length() - 1);
        return "[" + out + "]";
    }

    private void updateCheckPoint() {
        Position checkpointPosition = goal.getCurrentCheckPoint().getPosition();
        double distanceSCX = checkpointPosition.getX() - ship.getPosition().getX();
        double distanceSCY = checkpointPosition.getY() - ship.getPosition().getY();
        double distanceSC = Math.sqrt(Math.pow(distanceSCX,2) + Math.pow(distanceSCY,2));
        double radius=((Circle)goal.getCurrentCheckPoint().getShape()).getRadius();
        if (distanceSC<=radius)
            goal.nextCheckPoint();
    }

    private void disassociate() {
        sailors.forEach(sailor -> sailor.setTargetEntity(null));
    }

    /**
     * Captain will associate the best number of sailors to proceed a rotation of the given angle.
     * @param orientation The rotation of the given angle.
     */
    public void associateSailorToOar(double orientation){
        int maxSailors = Math.abs((int) Math.ceil(orientation/(Math.PI / ship.getEntities().size())));
        List<Oar> oarList = ship.getOarList(orientation < 0 ? "right" : "left");
        int i = 0;

        // We continue associating until we run out of sailors or oars
        while(i < oarList.size() && i < sailors.size() && i < maxSailors){
            Oar oar = oarList.get(i);
            logger.info(String.valueOf(oar));
            sailors.get(i).setTargetEntity(oar);
            oar.setSailor(sailors.get(i));
            i++;
        }
//        List<Oar> allOars = ship.getAllOar();
//        List<Oar> leftOars;
//        for(Oar oar : allOars) {
//
//        }

    }

    /**
     * Associate the same amount of sailors to the left oars and the right oars of the ship.
     */
    public void associateSailorToOarEvenly(){
        List<Oar> leftOarList = ship.getOarList("left");
        List<Oar> rightOarList = ship.getOarList("right");
        int oarIndex = 0;
        int sailorIndex = 0;
        ArrayList<Sailor> listOfUnassignedSailors = (ArrayList<Sailor>) sailors.stream()
                .filter(sailor-> sailor.getTargetEntity()==null)
                .collect(Collectors.toList());

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
     * Will ask the nearest sailor to the rudder to move to.
     */
    public void askSailorToMoveToRudder(){
        Rudder rudder = ship.getRudder();
        if (rudder == null)
            return;

        Optional<Sailor> sailorToMove = sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() == null)
                .min(Comparator.comparingInt(sailor -> sailor.getDistanceToEntity(rudder)));

        if(sailorToMove.isPresent()) {
            Sailor s = sailorToMove.get();
            s.setTargetEntity(rudder);
            roundActions.add(s.moveToTarget());
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
                .toList());
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
                .toList());
    }

    /**
     * Ask a sailor to turn with the rudder
     * This method update list of Action (roundActions)
     * @param rotationRudder
     */
    void askSailorsToTurnWithRudder(double rotationRudder) {
        roundActions.addAll(sailors.stream()
                .filter(sailor -> sailor.getTargetEntity() instanceof Rudder)
                .filter(Sailor::isOnTheTargetEntity)
                .map(sailor -> sailor.turnWithRudder(rotationRudder))
                .toList());
    }

    public List<Action> getRoundActions(){
        return roundActions;
    }
}
