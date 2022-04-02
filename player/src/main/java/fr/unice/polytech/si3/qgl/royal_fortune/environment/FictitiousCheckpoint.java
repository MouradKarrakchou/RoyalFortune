package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class FictitiousCheckpoint {
    private final List<Checkpoint> fictitiousCheckpoints;

    public FictitiousCheckpoint(List<Checkpoint> originalCheckpoints) {
        fictitiousCheckpoints = createFictitiousCheckpoints(originalCheckpoints);
    }

    /**
     * For a given list of checkpoints, will create the associated list of fictitious checkpoints.
     * @param originalCheckpoints The list of original checkpoints.
     * @return The list of fictitious checkpoints.
     */
    public List<Checkpoint> createFictitiousCheckpoints(List<Checkpoint> originalCheckpoints) {
        if (originalCheckpoints == null)
            return Collections.emptyList();

        if (originalCheckpoints.isEmpty())
            return originalCheckpoints;

        List<Checkpoint> theFictitiousCheckpoints = new ArrayList<>();
        for (int i = 0; i < originalCheckpoints.size() - 1; i++) {
            Checkpoint currentCheckpoint = originalCheckpoints.get(i);
            Checkpoint nextCheckPoint = originalCheckpoints.get(i + 1);
            theFictitiousCheckpoints.add(createFictitiousCheckpoint(currentCheckpoint, nextCheckPoint));
        }

        // The last checkpoint will not be changed
        theFictitiousCheckpoints.add(originalCheckpoints.get(originalCheckpoints.size() - 1));
        return theFictitiousCheckpoints;
    }

    /**
     * Create a fictional checkpoint HALF smaller than the original.
     * @param currentCheckpoint The current checkpoint.
     * @param nextCheckPoint The checkpoint to reach after the current checkpoint is reached.
     * @return The fictional checkpoint.
     */
    public Checkpoint createFictitiousCheckpoint(Checkpoint currentCheckpoint, Checkpoint nextCheckPoint) {
        double currentCheckpointX = currentCheckpoint.getPosition().getX();
        double currentCheckpointY = currentCheckpoint.getPosition().getY();
        double nextCheckPointX = nextCheckPoint.getPosition().getX();
        double nextCheckPointY = nextCheckPoint.getPosition().getY();

        double vectorX = nextCheckPointX - currentCheckpointX;
        double vectorY = nextCheckPointY - currentCheckpointY;
        double norm = Math.sqrt(vectorX * vectorX + vectorY * vectorY);

        double unitX = vectorX/norm;
        double unitY = vectorY/norm;

        double fictitiousCheckpointRadius = ((Circle) currentCheckpoint.getShape()).getRadius() / 2;
        Circle fictitiousCheckpointShape = new Circle(fictitiousCheckpointRadius);
        Position fictitiousCheckpointPosition = new Position(currentCheckpointX + fictitiousCheckpointRadius * unitX,
                currentCheckpointY + fictitiousCheckpointRadius * unitY, 0);
        return new Checkpoint(fictitiousCheckpointPosition, fictitiousCheckpointShape);
    }

    public void nextCheckPoint() {
        if (!fictitiousCheckpoints.isEmpty())
            fictitiousCheckpoints.remove(0);
    }

    public Checkpoint getCurrentCheckPoint() {
        if (fictitiousCheckpoints.isEmpty())
            return null;
        int i=0;
        while (fictitiousCheckpoints.get(i) instanceof Beacon)
        {
            i++;
            
        }
        return fictitiousCheckpoints.get(i);
    }

    public void addFictitiousCheckpoint(Checkpoint checkpoint){
        fictitiousCheckpoints.add(0,checkpoint);
    }

    public List<Checkpoint> getFictitiousCheckpoints() {
        return fictitiousCheckpoints;
    }

    public void addBeacons(Stack<Beacon> beaconStack) {
        removeAllBeacons();
        int i=0;
        while (beaconStack.size()>0){
            fictitiousCheckpoints.add(i,beaconStack.pop());
            i++;
        }
    }
    public void removeAllBeacons(){
        Checkpoint checkpoint=fictitiousCheckpoints.get(0);
        while (checkpoint instanceof Beacon){
            fictitiousCheckpoints.remove(0);
            checkpoint=fictitiousCheckpoints.get(0);
        }
    }
}
