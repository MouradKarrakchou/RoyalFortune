package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.target.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Observer;

import java.util.List;

public class SeaMap {
    private final Goal goal;
    private FictitiousCheckpoint fictitiousCheckpoints;
    private final Position shipPosition;
    private Wind wind;
    private List<SeaEntities> seaEntities;

    public SeaMap(Goal goal,FictitiousCheckpoint fictitiousCheckpoints,Position shipPosition,Wind wind,List<SeaEntities> seaEntities){
        this.goal=goal;
        this.fictitiousCheckpoints=fictitiousCheckpoints;
        this.shipPosition=shipPosition;
        this.wind=wind;
        this.seaEntities=seaEntities;
    }
    public void updateCheckPoint(SeaEntities newSeaEntities) {
        if (isInCheckpoint(goal.getCurrentCheckPoint()))
        {goal.nextCheckPoint();
            fictitiousCheckpoints.nextCheckPoint();}
        Observer observer=new Observer(shipPosition, wind,fictitiousCheckpoints.getCurrentCheckPoint().getPosition());
        observer.watchSea(newSeaEntities);
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

}
