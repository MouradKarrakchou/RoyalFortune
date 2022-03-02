package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
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
    private final FictitiousCheckpoint fictitiousCheckpoints;
    private final ArrayList<Action> roundActions;
    private final DirectionsManager directionsManager;
    final Logger logger = Logger.getLogger(Captain.class.getName());

    public Captain(Ship ship, List<Sailor> sailors, Goal goal, FictitiousCheckpoint fictitiousCheckpoints){
        this.ship = ship;
        this.sailors = sailors;
        this.goal = goal;
        this.fictitiousCheckpoints = fictitiousCheckpoints;
        roundActions = new ArrayList<>();
        directionsManager = new DirectionsManager(ship, fictitiousCheckpoints);
    }

    public String roundDecisions() {

        disassociate();
        roundActions.clear();
        updateCheckPoint();
        directionsManager.update();
        roundProceed();
        makeBoatMove();

        String out = createAction();
        return "[" + out + "]";
    }

    /**
     * Create the Json of actions
     * @return String wit the json
     */
    public String createAction(){
        StringBuilder actionsToDo = new StringBuilder();
        for(Action action : roundActions)
            actionsToDo.append(action.toString()).append(",");
        return(actionsToDo.substring(0, actionsToDo.length() - 1));
    }


    public void roundProceed(){
        double angleMove = directionsManager.getAngleMove();
        double angleMadeBySailors = 0;
        double signOfAngleMove = (angleMove/Math.abs(angleMove));

        if (!directionsManager.isConeTooSmall() && !directionsManager.isInCone()) {
            int numberOfSailorOaring=associateSailorToOar(numberOfSailorToTurn(angleMove),directionsManager.getDirection());
            angleMadeBySailors = numberOfSailorOaring*angleMove/Math.abs(angleMove)*(Math.PI/ship.getNbrOar());
        }

        if(-Math.PI/4 <= angleMove - angleMadeBySailors && angleMove - angleMadeBySailors <= Math.PI/4 && Math.abs(angleMove - angleMadeBySailors)>Math.pow(10,-3)) {
            askSailorToMoveToRudder();
            askSailorsToTurnWithRudder(angleMove - angleMadeBySailors);
        }
        else if(angleMove - angleMadeBySailors < -Math.PI/4 || Math.PI/4 < angleMove - angleMadeBySailors) {
            askSailorToMoveToRudder();
            askSailorsToTurnWithRudder(signOfAngleMove*Math.PI/4);
        }
    }

    /**
     * Make the sailor go to the oar and use them.
     */
    private void makeBoatMove() {
        associateSailorToOarEvenly();
        askSailorsToMove();
        askSailorsToOar();
    }

    private void updateCheckPoint() {
        if (isInCheckpoint(goal.getCurrentCheckPoint()))
        {goal.nextCheckPoint();
            fictitiousCheckpoints.nextCheckPoint();}
        }
    private boolean isInCheckpoint(Checkpoint checkpoint) {
        return(isInCheckpointShipPos(checkpoint,ship.getPosition().getX(),ship.getPosition().getY()));
    }

    private boolean isInCheckpointShipPos(Checkpoint checkpoint,double shipX,double shipY) {
        double distanceSCX = checkpoint.getPosition().getX() - shipX;
        double distanceSCY = checkpoint.getPosition().getY() - shipY;
        double distanceSC = Math.sqrt(Math.pow(distanceSCX,2) + Math.pow(distanceSCY,2));
        double radius=((Circle)checkpoint.getShape()).getRadius();
        return(distanceSC<=radius);
    }

    private void disassociate() {
        sailors.forEach(sailor -> sailor.setTargetEntity(null));
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
            logger.info(String.valueOf(oar));
            sailors.get(i).setTargetEntity(oar);
            oar.setSailor(sailors.get(i));
            i++;
        }
        return numberOfSailors;
    }

    /**
     * It gives the number of Sailor needed to turn
     * @param orientation that we need to make our ship move
     * @return the number of Sailor that will oar in one direction
     */
    public int numberOfSailorToTurn(double orientation){
        int sizeOfOarList = ship.getOarList(orientation > 0 ? "right" : "left").size();
        int maxSailorsToMoveAngle=Math.abs((int) Math.ceil(orientation/(Math.PI / ship.getNbrOar())));
        int numberOfSailors=sailors.size();
        return(Math.min(numberOfSailors,Math.min(sizeOfOarList,maxSailorsToMoveAngle)));
    }

    /**
     * Associate the same amount of sailors to the left oars and the right oars of the ship.
     */
    public void associateSailorToOarEvenly(){
        List<Oar> leftOarList = ship.getOarList("left");
        List<Oar> rightOarList = ship.getOarList("right");
        int oarIndex = 0;
        int sailorIndex = 0;
        ArrayList<Sailor> listOfUnassignedSailors = getlistOfUnassignedSailors();
        int numberOfSailorNeeded=numberOfSailorToOarEvenly();
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
     * Give The list of Sailors that are not assigned to an entitie;
     * @return
     */
    ArrayList<Sailor> getlistOfUnassignedSailors(){
        return((ArrayList<Sailor>) sailors.stream()
                .filter(sailor-> sailor.getTargetEntity()==null)
                .collect(Collectors.toList()));
    }


    public int numberOfSailorToOarEvenly(){
        int leftOarList = 2*ship.getOarList("left").size();
        int rightOarList = 2*ship.getOarList("right").size();
        int listOfUnassignedSailors=2*((int)getlistOfUnassignedSailors().size()/2);
        int numberOfSailorToCheckPoint=numberOfSailorToOarToCheckPoint();
        return Math.min(Math.min(leftOarList,rightOarList),Math.min(listOfUnassignedSailors,numberOfSailorToCheckPoint));
    }

    /**
     * give the number of sailor needed to go to the next checkpoint(with max at sailors.size())
     * @return number of sailors needed
     */
    public int numberOfSailorToOarToCheckPoint(){
        int numberOfSailor=2;
        while (needSailorToOarToCheckpoint(numberOfSailor)&& numberOfSailor<=sailors.size()){
            numberOfSailor+=2;
        }
        return numberOfSailor;
    }
    /**
     * It predict if we are going to be in the modified checkpoint
     * @param numberOfSailors
     * @return if with this numberOfSailors we are in the checkpoint or not
     */
    public boolean needSailorToOarToCheckpoint(int numberOfSailors){
        int norme=165*numberOfSailors/ship.getNbrOar();
        double newX=ship.getPosition().getX();
        double newY= ship.getPosition().getY();
        double angleCalcul=ship.getPosition().getOrientation();
        newX+=norme*Math.cos(angleCalcul);
        newY+=norme*Math.sin(angleCalcul);
        return !isInCheckpointShipPos(fictitiousCheckpoints.getCurrentCheckPoint(),newX,newY);
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
                .filter(sailor-> sailor.getTargetEntity().isOar())
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
