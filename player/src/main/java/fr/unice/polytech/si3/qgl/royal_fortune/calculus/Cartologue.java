package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.HashMap;
import java.util.List;

public class Cartologue {
    private List<Stream> listStream;
    private List<Reef> listReef;
    private Wind wind;

    public Cartologue(List<Stream> listStream, List<Reef> listReef, Wind wind) {
        this.listStream = listStream;
        this.listReef = listReef;
        this.wind = wind;
    }
    /**
     * Compute the distance of a route
     * @param beacon
     * @param start
     * @param end
     * @return distance of the route from the ship to the checkpoint, through the beacon
     */
    public static double computeDistance(Beacon beacon, Position start, Position end) {
        Position beaconPosition = beacon.getPosition();
        return Mathematician.distanceFormula(start, beaconPosition) + Mathematician.distanceFormula(beaconPosition, end);
    }

    /**
     * Slice the segment by collision with seaEntities
     *@param segment
     * @return a list of segment that represent intersection
     */
    public static List<Segment> sliceSegmentByInteraction (Segment segment){
        //use detect intersection
        return null;
    }

    /**
     *
     * @param path
     * @param listSeaEntities
     * @return a hashmap that
     */
    private static HashMap<SeaEntities, Segment> detectIntersection(Segment path, List<SeaEntities> listSeaEntities){
        //use rectangle intersection method
        return null;
    }
}
