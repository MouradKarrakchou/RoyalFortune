package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Observer;

import java.util.List;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class SeaMap {
    private final Goal goal;
    private FictitiousCheckpoint fictitiousCheckpoints;
    private final Position shipPosition;
    int roundSinceLastCheckpoint;
    Observer observer;

    public SeaMap(Goal goal,FictitiousCheckpoint fictitiousCheckpoints,Position shipPosition){
        this.goal=goal;
        this.fictitiousCheckpoints=fictitiousCheckpoints;
        this.shipPosition=shipPosition;
        observer=new Observer();
    }

    /**
     * Update checkpoints to target
     * @param newSeaEntities sea entities
     */
    public void updateCheckPoint(List<SeaEntities> newSeaEntities) {
        if (isInCheckpoint(goal.getCheckPoints().get(0)))
        {
            goal.nextCheckPoint();
            fictitiousCheckpoints.nextCheckPoint();
            roundSinceLastCheckpoint=0;
        }
        if (isInCheckpoint(fictitiousCheckpoints.getCurrentCheckPoint())) {
            fictitiousCheckpoints.nextCheckPoint();
        }
        observer.setNextCheckPointPosition(goal.getCurrentCheckPoint().getPosition());
        observer.setShipPosition(shipPosition);

        observer.updateSeaEntities(newSeaEntities);
        List<Beacon> beaconList =observer.watchSea(observer.getCurrentSeaEntities());
        fictitiousCheckpoints.addBeacons(beaconList);
        roundSinceLastCheckpoint++;
    }

    /**
     * Checks if the ship is in the checkpoint
     * @param checkpoint checkpoint
     * @return true if so
     */
    public boolean isInCheckpoint(Checkpoint checkpoint) {
        return(isInCheckpointShipPos(checkpoint,shipPosition.getX(),shipPosition.getY()));
    }

    /**
     * Checks if the ship position is in the checkpoint
     * @param checkpoint checkpoint
     * @param shipX ship position on x
     * @param shipY ship position on y
     * @return true if so
     */
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
