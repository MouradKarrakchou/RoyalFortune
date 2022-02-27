package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class FictitiousCheckpoint {
    List<Checkpoint> originalCheckpoints;
    List<Checkpoint> fictitiousCheckpoints;

    public FictitiousCheckpoint(List<Checkpoint> originalCheckpoints) {
        this.originalCheckpoints = originalCheckpoints;
        fictitiousCheckpoints = createFictitiousCheckpoints();
    }

    public List<Checkpoint> createFictitiousCheckpoints() {
        if (originalCheckpoints == null)
            return null;

        if (originalCheckpoints.isEmpty())
            return originalCheckpoints;

        List<Checkpoint> fictitiousCheckpoints = new ArrayList<>();
        for (int i = 0; i < originalCheckpoints.size() - 1; i++) {
            Checkpoint currentCheckpoint = originalCheckpoints.get(i);
            Checkpoint nextCheckPoint = originalCheckpoints.get(i + 1);
            fictitiousCheckpoints.add(createFictitiousCheckpoint(currentCheckpoint, nextCheckPoint));
        }

        // The last checkpoint will not be changed
        fictitiousCheckpoints.add(originalCheckpoints.get(originalCheckpoints.size() - 1));
        return fictitiousCheckpoints;
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
        Circle fictitiousCheckpointShape = new Circle("Circle", fictitiousCheckpointRadius);
        Position fictitiousCheckpointPosition = new Position(currentCheckpointX + fictitiousCheckpointRadius * unitX,
                currentCheckpointY + fictitiousCheckpointRadius * unitY, 0);

        return new Checkpoint(fictitiousCheckpointPosition, fictitiousCheckpointShape);
    }

    public void nextCheckPoint() {
        fictitiousCheckpoints.remove(0);
    }

    public Checkpoint getCurrentCheckPoint() {
        return fictitiousCheckpoints.get(0);
    }
}
