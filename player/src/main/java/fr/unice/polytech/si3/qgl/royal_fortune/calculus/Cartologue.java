package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cartologue {
    private List<Stream> listStream;
    private List<Reef> listReef;
    private Wind wind;
    HashMap<Segment,SeaEntities> hashMap;

    public Cartologue(List<Stream> listStream, List<Reef> listReef, Wind wind) {
        this.hashMap=new HashMap<>();
        this.listStream = listStream;
        this.listReef = listReef;
        this.wind = wind;
    }
    /**
     * Compute the distance of a route
     * @param segment a segment
     * @return number of turn to do the road from the ship to the checkpoint, through the beacon
     */
    public double computeNumberOfRoundsNeeded(Segment segment) {
        double dist;
        //we considerate that numberOfSailors/numberOfOar=1
        if (hashMap.containsKey(segment))
        {   Stream stream= (Stream) hashMap.get(segment);
            double angle=segment.angleIntersectionBetweenSegmentAndRectangle((Rectangle)stream.getShape());
            dist=segment.getLength()/(165+stream.getStrength()*Math.cos(angle));}
        else
            dist=segment.getLength()/165;
        return dist;
    }

    /**
     * Slice the segment by collision with seaEntities
     * @param path a path
     * @return a list of segment that represent intersection
     */
    public List<Segment> sliceSegmentByInteraction (Segment path){
        return(cutSegment(path,positionIsOnAStream(path.getPointA())));
    }

    /**
     * We check where we cross a stream and we return the segments
     * @param path a path
     * @return list of segments
     */
    public List<Segment> cutSegment(Segment path,Boolean isOnStream){
        List<Segment> segments=new ArrayList<>();
        for (Stream stream:listStream){
            List<Position> intersections = new ArrayList<>(((Rectangle) stream.getShape()).computeIntersectionWith(path));
            if (intersections.size()==3)
            {
                segments.add(new Segment(intersections.get(0),intersections.get(1)));
                segments.add(new Segment(intersections.get(1),intersections.get(2)));
                if(isOnStream)
                    hashMap.put(segments.get(0),stream);
                else
                    hashMap.put(segments.get(1),stream);
                break;
            }
            else if (intersections.size()==4)
            {
                segments.add(new Segment(intersections.get(0),intersections.get(1)));
                segments.add(new Segment(intersections.get(1),intersections.get(2)));
                segments.add(new Segment(intersections.get(2),intersections.get(3)));
                hashMap.put(segments.get(1),stream);
                break;
            }
        }
        return (segments);
    }



    /**
     * check if the point is on a Stream
     * @return true if the point is in the rectangle
     * @param pointA a point
     */
    private boolean positionIsOnAStream(Position pointA) {
        for (Stream stream:listStream){
            if (((Rectangle)stream.getShape()).positionIsInTheRectangle(pointA))
                return true;
        }
        return false;
    }

    public HashMap<Segment, SeaEntities> getHashMap() {
        return hashMap;
    }
}