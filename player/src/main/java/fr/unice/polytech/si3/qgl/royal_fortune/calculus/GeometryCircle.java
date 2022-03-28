package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.ArrayList;
import java.util.List;

public class GeometryCircle {
    private final double beaconRadius = 50;
    private final double securityScaling = 20;

    private GeometryCircle() {}

    public List<Beacon> generateBeacon(Position sheepPosition, Position checkpointPosition, Position reefPosition, Circle reefShape){
        List<Beacon> beaconList = new ArrayList<>();

        double vectorSheepCheckpointX = checkpointPosition.getX() - sheepPosition.getX();
        double vectorSheepCheckpointY = checkpointPosition.getY() - sheepPosition.getY();
        double normSheepCheckpoint = Math.sqrt(vectorSheepCheckpointX * vectorSheepCheckpointX + vectorSheepCheckpointY * vectorSheepCheckpointY);

        double normalVectorX = -vectorSheepCheckpointY / normSheepCheckpoint;
        double normalVectorY = vectorSheepCheckpointX / normSheepCheckpoint;

        double firstBeaconX = reefPosition.getX() + normalVectorX * (reefShape.getRadius() + beaconRadius + securityScaling);
        double firstBeaconY = reefPosition.getY() + normalVectorY * (reefShape.getRadius() + beaconRadius + securityScaling);
        beaconList.add(new Beacon(new Position(firstBeaconX, firstBeaconY), new Circle(beaconRadius)));

        double secondBeaconX = reefPosition.getX() - normalVectorX * (reefShape.getRadius() + beaconRadius + securityScaling);
        double secondBeaconY = reefPosition.getY() - normalVectorY * (reefShape.getRadius() + beaconRadius + securityScaling);
        beaconList.add(new Beacon(new Position(secondBeaconX, secondBeaconY), new Circle(beaconRadius)));

        return beaconList;
    }
}
