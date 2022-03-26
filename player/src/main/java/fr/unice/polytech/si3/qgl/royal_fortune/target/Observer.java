package fr.unice.polytech.si3.qgl.royal_fortune.target;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryRectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.util.*;

public class Observer {
    public static final int MAX_RANGE = 1000;
    private List<SeaEntities> currentSeaEntities;
    private Position shipPosition;
    private Mathematician mathematician;
    private Cartologue cartologue;
    private Position nextCheckPointPosition;
    private int RANGE=40;

    public Observer(){
        this.currentSeaEntities=new ArrayList<>();
        cartologue=new Cartologue(getStream(currentSeaEntities),getReef(currentSeaEntities));
        this.mathematician = new Mathematician(cartologue);
    }

    private List<Stream> getStream(List<SeaEntities> newSeaEntities){
        List<Stream> listOfStream=new ArrayList<>();
        for(SeaEntities seaEntities:newSeaEntities){
            if (seaEntities instanceof Stream)
                listOfStream.add((Stream) seaEntities);
        }
        return listOfStream;
    }
    private List<Reef> getReef(List<SeaEntities> newSeaEntities){
        List<Reef> listOfReef=new ArrayList<>();
        for(SeaEntities seaEntities:newSeaEntities){
            if (seaEntities instanceof Reef)
                listOfReef.add((Reef) seaEntities);
        }
        return listOfReef;
    }
    /**
     * Check in half-circle all the seaEntities in RANGE. If newSeaEntities is different from currentSeaEntities return true.
     * @param newSeaEntities a potential new sea entity
     * @return boolean
     */
    public Boolean checkIfNewSeaEntities(List<SeaEntities> newSeaEntities){
        for (SeaEntities seaEntitie: newSeaEntities){
            if (!currentSeaEntities.contains(seaEntitie))
                return true;
        }
        return false;
    }

    /**
     *If there is new Entities in RANGE ask Mathematician to find the best path through a beacon.
     * @param newSeaEntities a potential new sea entity
     * @return If return empty we target the checkpoint else we target the Beacon
     */
    public Optional<Beacon> watchSea(List<SeaEntities> newSeaEntities){
        currentSeaEntities=newSeaEntities;
        List<Beacon> beacons=new ArrayList<>();
        for (SeaEntities seaEntities:newSeaEntities){
            if (seaEntities.getShape() instanceof Rectangle)
                beacons.addAll(GeometryRectangle.generateBeacon(seaEntities.getPosition(), (Rectangle) seaEntities.getShape()));
        }
        cartologue.setListSeaEntities(newSeaEntities);
        return mathematician.computeTrajectory(beacons,shipPosition,nextCheckPointPosition);
    }

    public void setNextCheckPointPosition(Position nextCheckPointPosition) {
        this.nextCheckPointPosition = nextCheckPointPosition;
    }

    public void setShipPosition(Position shipPosition) {
        this.shipPosition = shipPosition;
    }
}
