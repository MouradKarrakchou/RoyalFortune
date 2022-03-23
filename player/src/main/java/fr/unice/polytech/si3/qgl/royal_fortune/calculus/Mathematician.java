package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Route;

import java.util.*;
import java.util.List;
import java.util.Optional;

public class Mathematician {
    HashMap<Route, Beacon> beaconHashMap;
    Cartologue cartologue;
    public Mathematician(Cartologue cartologue){
        this.cartologue=cartologue;
    }

    /**
     * Compute the best path to reach the next checkpoint through a beacon or empty to follow th checkpoint
     * @param listBeacon list of beacons
     * @return the best beacon to go through or empty
     */
    public Optional<Beacon> computeTrajectory(List<Beacon> listBeacon,Position departure,Position arrival){
        ArrayList<Route> roads=new ArrayList<>();
        for (Beacon beacon :listBeacon){
            List<Segment> segments=new ArrayList<>();
            segments.add(new Segment(departure,beacon.getPosition()));
            segments.add(new Segment(beacon.getPosition(), arrival));
            Route route=new Route(segments,cartologue);
            roads.add(route);
            beaconHashMap.put(route,beacon);
        }
        Route route = null;
        if (!roads.isEmpty())
                route=Collections.max(roads);
        if (route!=null)
            return Optional.of(beaconHashMap.get(route));
        else return Optional.empty();
    }

    /**
     * Given a position of our Base and our movement (x,y) in this base return the new Position in the real Base.
     * @param position position in a base
     * @param x movement on x axis
     * @param y movement on y axis
     * @return the position in the sea base
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
