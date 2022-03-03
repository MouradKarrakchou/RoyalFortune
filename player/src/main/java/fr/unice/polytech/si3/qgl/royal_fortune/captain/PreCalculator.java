package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

import java.util.List;

public class PreCalculator {
    private Ship ship;
    private List<Sailor> sailors;
    private SeaMap seaMap;

    public PreCalculator(Ship ship,List<Sailor> sailors,SeaMap seaMap){
        this.ship=ship;
        this.sailors=sailors;
        this.seaMap=seaMap;
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
        return !seaMap.isInCheckpointShipPos(seaMap.getCurrentFictitiousCheckPoint(),newX,newY);
    }

    /**
     * Give the number Of Sailor needed
     * @return number of Sailor that will oar evenly
     */
    public int numberOfSailorToOarEvenly(int nbUnassignedSailors){
        int leftOarList = 2*ship.getOarList(DirectionsManager.LEFT).size();
        int rightOarList = 2*ship.getOarList(DirectionsManager.RIGHT).size();
        int listOfUnassignedSailors=2*((int)nbUnassignedSailors/2);
        int numberOfSailorToCheckPoint=numberOfSailorToOarToCheckPoint();
        return Math.min(Math.min(leftOarList,rightOarList),Math.min(listOfUnassignedSailors,numberOfSailorToCheckPoint));
    }
}
