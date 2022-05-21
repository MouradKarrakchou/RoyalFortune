package fr.unice.polytech.si3.qgl.royal_fortune.target;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryCircle;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryRectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.dijkstra.Dijkstra;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.util.*;

public class Observer {
    public static final int MAX_RANGE = 1000000000;
    private List<SeaEntities> currentSeaEntities;
    private Position shipPosition;
    private Mathematician mathematician;
    private Cartologue cartologue;
    private Position nextCheckPointPosition;
    private int RANGE=40;

    public Observer(){
        this.currentSeaEntities=new ArrayList<>();
        this.cartologue=new Cartologue(getStream(currentSeaEntities),getReef(currentSeaEntities));
        this.mathematician = new Mathematician(cartologue);
    }

    public List<Stream> getStream(List<SeaEntities> newSeaEntities){
        List<Stream> listOfStream=new ArrayList<>();
        for(SeaEntities seaEntities:newSeaEntities){
            if (seaEntities instanceof Stream)
                listOfStream.add((Stream) seaEntities);
        }
        return listOfStream;
    }
    public List<Reef> getReef(List<SeaEntities> newSeaEntities){
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
     */
    public void updateSeaEntities(List<SeaEntities> newSeaEntities){
        for (SeaEntities newSeaEntity : newSeaEntities){
            newSeaEntity.getShape();
            if (!currentSeaEntities.contains(newSeaEntity)){
                Dijkstra.clearMap();
                currentSeaEntities.add(newSeaEntity);
            }
        }
    }

    /**
     *If there is new Entities in RANGE ask Mathematician to find the best path through a beacon.
     * @param newSeaEntities a potential new sea entity
     * @return If return empty we target the checkpoint else we target the Beacon
     */
    public List<Beacon> watchSea(List<SeaEntities> newSeaEntities){
        //this.currentSeaEntities=newSeaEntities;
        List<Beacon> beacons=new ArrayList<>();
        for (SeaEntities seaEntities:newSeaEntities){
            if (seaEntities.getShape() instanceof Circle)
                beacons.addAll(GeometryCircle.generateBeacon(seaEntities.getPosition(),(Circle) seaEntities.getShape()));
            else
                beacons.addAll(seaEntities.getShape().generateBeacon(seaEntities.getPosition(),seaEntities.isReef()));
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

    public Cartologue getCartologue() {
        return cartologue;
    }

    public List<SeaEntities> getCurrentSeaEntities() {
        return currentSeaEntities;
    }
}
