package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

import java.util.List;

public class PreCalculator {
    private final Ship ship;
    private final List<Sailor> sailors;
    private final SeaMap seaMap;
    private final Wind wind;

    public PreCalculator(Ship ship, List<Sailor> sailors, SeaMap seaMap,Wind wind) {
        this.ship = ship;
        this.sailors = sailors;
        this.seaMap = seaMap;
        this.wind=wind;
    }

    /**
     * give the number of sailor needed to go to the next checkpoint(with max at sailors.size())
     *
     * @return number of sailors needed
     */
    public int numberOfSailorToOarToCheckPoint() {
        int numberOfSailor = 2;
        while (needSailorToOarToCheckpoint(numberOfSailor) && numberOfSailor <= sailors.size()) {
            numberOfSailor += 2;
        }
        return numberOfSailor;
    }

    /**
     * Predicts if we are going to be in the fictitious checkpoint.
     *
     * @param numberOfSailors The number of sailor to oar.
     * @return if with this numberOfSailors we are in the checkpoint or not
     */
    public boolean needSailorToOarToCheckpoint(int numberOfSailors) {
        double norm = 165 * numberOfSailors / ship.getNbrOar();
        double newX = ship.getPosition().getX();
        double newY = ship.getPosition().getY();
        double angle = ship.getPosition().getOrientation();
        newX += norm * Math.cos(angle);
        newY += norm * Math.sin(angle);

        if(ship.getSail().isOpenned()) {
            Wind theWind = wind;
            double windNorm = Math.abs(theWind.getStrength() * Math.cos(theWind.getOrientation() - ship.getPosition().getOrientation()));

            newX += windNorm * Math.cos(angle);
            newY += windNorm * Math.sin(angle);
        }

        return !seaMap.isInCheckpointShipPos(seaMap.getCurrentFictitiousCheckPoint(), newX, newY);
    }

    /**
     * Give the number Of Sailor needed
     *
     * @return number of Sailor that will oar evenly
     */
    public int numberOfSailorToOarEvenly(int nbUnassignedSailors, Associations associations) {
        int leftOarList = 2 * ship.getOarList(DirectionsManager.LEFT, associations).size();
        int rightOarList = 2 * ship.getOarList(DirectionsManager.RIGHT, associations).size();
        int nbUnassignedSailorsCanOar = 2 * (nbUnassignedSailors / 2);
        int numberOfSailorToCheckPoint = numberOfSailorToOarToCheckPoint();
        return Math.min(Math.min(leftOarList, rightOarList), Math.min(nbUnassignedSailorsCanOar, numberOfSailorToCheckPoint));
    }
}
