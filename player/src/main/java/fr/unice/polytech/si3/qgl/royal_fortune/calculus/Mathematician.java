package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Mathematician {
    public Mathematician(){}

    /**
     * Compute the best path to reach the next checkpoint through a beacon or empty to follow th checkpoint
     * @param listReefs
     * @return the best beacon to go through or empty
     */
    public Optional<Beacon> computeTrajectory(List<Beacon> listBeacon, List<Reef> listReefs){
        //getHashBeaconOfListStream()
        //for each beacon on list<Beacon> use computeDistance() of geometer
        //compare distance of best balise et distance avec checkpoint
        return null;
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


    public static Position changeBase(Position position,double x,double y){
        Position newPosition=new Position();
        newPosition.setX(Math.sqrt(Math.pow(Math.cos(position.getOrientation()),2)*x+Math.pow((Math.sin(position.getOrientation())*y),2)));
        newPosition.setY(Math.sqrt(Math.pow(-Math.sin(position.getOrientation()),2)*x+Math.pow((Math.cos(position.getOrientation())*y),2)));
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
    static double distanceFormula(Position a, Position b) {
        double aX = a.getX();
        double aY = a.getY();

        double bX = b.getX();
        double bY = b.getY();

        return Math.sqrt( Math.pow(bX - aX, 2) + Math.pow(bY - aY, 2) );
    }

}
