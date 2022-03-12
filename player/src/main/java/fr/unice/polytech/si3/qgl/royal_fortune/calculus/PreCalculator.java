package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.SeaMap;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

import java.util.List;

public class PreCalculator {
    private final Ship ship;
    private final List<Sailor> sailors;
    private final SeaMap seaMap;
    private  Wind wind;

    public PreCalculator(Ship ship, List<Sailor> sailors, SeaMap seaMap, Wind wind) {
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
        return (numberOfSailors<howManySailorsNeeded());
    }
    public int howManySailorsNeeded(){
        int numberOfSailors=0;
        double norm = 0;
        double newX = ship.getPosition().getX();
        double newY = ship.getPosition().getY();
        double angle = ship.getPosition().getOrientation();
        if(ship.getSail().isOpenned()) {
            Wind theWind = wind;
            double windNorm = theWind.getStrength() * Math.cos(theWind.getOrientation() - angle);

            newX += windNorm * Math.cos(angle);
            newY += windNorm * Math.sin(angle);
        }
        while(!seaMap.isInCheckpointShipPos(seaMap.getCurrentFictitiousCheckPoint(), newX, newY))
            norm = 165 * numberOfSailors / (double) ship.getNbrOar();
            newX += norm * Math.cos(angle);
            newY += norm * Math.sin(angle);
            numberOfSailors+=2;
        return numberOfSailors;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
