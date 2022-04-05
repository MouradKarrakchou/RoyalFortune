package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Observer;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class SeaMap {
    private final Goal goal;
    private FictitiousCheckpoint fictitiousCheckpoints;
    private final Position shipPosition;
    private Wind wind;
    private List<SeaEntities> seaEntities;
    Observer observer;

    public SeaMap(Goal goal,FictitiousCheckpoint fictitiousCheckpoints,Position shipPosition,Wind wind,List<SeaEntities> seaEntities){
        this.goal=goal;
        this.fictitiousCheckpoints=fictitiousCheckpoints;
        this.shipPosition=shipPosition;
        this.wind=wind;
        this.seaEntities=seaEntities;
        observer=new Observer();
    }
    public void updateCheckPoint(List<SeaEntities> newSeaEntities) {
        if (isInCheckpoint(goal.getCheckPoints().get(0)))
        {
            goal.nextCheckPoint();
            fictitiousCheckpoints.nextCheckPoint();
            observer.getCartologue().getListSeaEntities().clear();
            this.seaEntities.clear();
        }
        if (isInCheckpoint(fictitiousCheckpoints.getCurrentCheckPoint()))
        {

            observer.getCartologue().getListSeaEntities().clear();
            this.seaEntities.clear();
            fictitiousCheckpoints.nextCheckPoint();
        }

        observer.setNextCheckPointPosition(goal.getCurrentCheckPoint().getPosition());
        observer.setShipPosition(shipPosition);
        if (observer.checkIfNewSeaEntities(newSeaEntities)){
            Stack<Beacon> beaconStack=observer.watchSea(newSeaEntities);
            fictitiousCheckpoints.addBeacons(beaconStack);
        }
    }
    public boolean isInCheckpoint(Checkpoint checkpoint) {
        return(isInCheckpointShipPos(checkpoint,shipPosition.getX(),shipPosition.getY()));
    }

    public boolean isInCheckpointShipPos(Checkpoint checkpoint,double shipX,double shipY) {
        double distanceSCX = checkpoint.getPosition().getX() - shipX;
        double distanceSCY = checkpoint.getPosition().getY() - shipY;
        double distanceSC = Math.sqrt(Math.pow(distanceSCX,2) + Math.pow(distanceSCY,2));
        double radius=((Circle)checkpoint.getShape()).getRadius();
        return(distanceSC<=radius);
    }

    public void setFictitiousCheckpoints(FictitiousCheckpoint fictitiousCheckpoints) {
        this.fictitiousCheckpoints=fictitiousCheckpoints;
    }

    public Checkpoint getCurrentFictitiousCheckPoint() {
        return(fictitiousCheckpoints.getCurrentCheckPoint());
    }

    public FictitiousCheckpoint getFictitiousCheckpoints() {
        return fictitiousCheckpoints;
    }

    public Checkpoint getCurrentFictitiousToSlowCheckPoint() {
        if (fictitiousCheckpoints.getCurrentCheckPoint() instanceof Beacon)
            return fictitiousCheckpoints.getFictitiousCheckpoints().get(1);
        else
            return fictitiousCheckpoints.getCurrentCheckPoint();
    }
}
