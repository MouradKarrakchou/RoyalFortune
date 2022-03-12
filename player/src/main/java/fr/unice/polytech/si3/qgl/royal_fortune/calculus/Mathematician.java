package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Route;

import java.util.*;

public class Mathematician {
    HashMap<Route, Beacon> beaconHashMap;
    Cartologue cartologue;
    public Mathematician(Cartologue cartologue){
        this.cartologue=cartologue;
    }

    /**
     * Compute the best path to reach the next checkpoint through a beacon or empty to follow th checkpoint
     * @param listBeacon
     * @return the best beacon to go through or empty
     */
    public Optional<Beacon> computeTrajectory(List<Beacon> listBeacon,Position departure,Position arrival){
        //getHashBeaconOfListStream()
        //for each beacon on list<Beacon> use computeDistance() of geometer
        //compare distance of best balise et distance avec checkpoint
        ArrayList<Route> roads=new ArrayList<>();
        for (Beacon beacon :listBeacon){
            List<Segment> segments=new ArrayList<>();
            segments.add(new Segment(departure,beacon.getPosition()));
            segments.add(new Segment(beacon.getPosition(), arrival));
            Route route=new Route(segments,cartologue);
            roads.add(route);
            beaconHashMap.put(route,beacon);
        }
        Route route=Collections.max(roads);
        if (route!=null)
            return Optional.of(beaconHashMap.get(route));
        else return Optional.empty();
    }

    /**
     *Link streams and their beacons
     * @param listStream
     * @return Return a Hashmap that link streams with their lists of beacons
     */
    public List<Beacon> getHashBeaconOfListStream(List<Stream> listStream){
        //utilise les beacon générés par les formes elles même
        return null;
    }

    /**
     * Given a position of our Base and our movement (x,y) in this base return the new Position in the real Base.
     * @param position
     * @param x
     * @param y
     * @return
     */
    public static Position changeBase(Position position,double x,double y){
        Position newPosition=new Position();
        newPosition.setX(Math.cos(position.getOrientation())*x-Math.sin(position.getOrientation())*y);
        newPosition.setY(Math.sin(position.getOrientation())*x+Math.cos(position.getOrientation())*y);
        newPosition.setX(newPosition.getX()+position.getX());
        newPosition.setY(newPosition.getY()+position.getY());
        return newPosition;
    }

    /**
     * Calculate the distance between two given positions.
     * @param a the starting position
     * @param b the ending position
     * @return The distance between the starting and ending position (double)
     */
    public static double distanceFormula(Position a, Position b) {
        double aX = a.getX();
        double aY = a.getY();

        double bX = b.getX();
        double bY = b.getY();

        return Math.sqrt( Math.pow(bX - aX, 2) + Math.pow(bY - aY, 2) );
    }

}
