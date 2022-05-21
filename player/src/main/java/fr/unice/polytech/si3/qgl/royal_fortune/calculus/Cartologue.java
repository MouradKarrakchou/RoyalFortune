package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Cartologue {
    private List<SeaEntities> listSeaEntities;
    HashMap<Segment,SeaEntities> map;

    public Cartologue(List<Stream> listStream, List<Reef> listReef) {
        this.map =new HashMap<>();
        this.listSeaEntities=new ArrayList<>();
        this.listSeaEntities.addAll(listStream);
        this.listSeaEntities.addAll(listReef);
    }

    /**
     * Compute the distance of a route
     * @param segment a segment
     * @return number of turn to do the road from the ship to the checkpoint, through the beacon
     */
    public double computeNumberOfRoundsNeeded(Segment segment) {
        double dist;
        //we considerate that numberOfSailors/numberOfOar=1
        if (map.containsKey(segment)) {
            if (map.get(segment).isStream()) {
                Stream stream = (Stream) map.get(segment);
                double angle = segment.angleIntersectionBetweenSegmentAndRectangle(stream.getPosition().getOrientation());
                double distancePushByStream = 165 + stream.getStrength() * Math.cos(angle);
                dist = segment.getLength() / distancePushByStream;
                if (distancePushByStream < 0)
                    return (Double.POSITIVE_INFINITY);
                return dist;
            }

            else {
                return (Double.POSITIVE_INFINITY);
            }
        }
        else {
            dist = segment.getLength() / 165;
        }
        return dist;
    }

    /**
     * Slice the segment by collision with seaEntities
     * @param path a path
     * @return a list of segment that represent intersection
     */
    public List<Segment> sliceSegmentByInteraction (Segment path){
        return cutSegment(path, positionIsOnASeaEntities(path.getPointA()));
    }

    /**
     * We check where we cross a stream and we return the segments
     * @param path a path
     * @return list of segments
     */
    public List<Segment> cutSegment(Segment path, Boolean isOnStream){
        List<Segment> segments = new ArrayList<>();
        List<Position> intersections;
        for (SeaEntities seaEntities : listSeaEntities){
            intersections = seaEntities.getShape().computeIntersectionWith(path, seaEntities.getPosition());

            if (seaEntities instanceof Stream) {
                if (intersections.size()==3)
                {
                    segments.add(new Segment(intersections.get(0),intersections.get(1)));
                    segments.add(new Segment(intersections.get(1),intersections.get(2)));
                    if(isOnStream)
                        map.put(segments.get(0),seaEntities);
                    else
                        map.put(segments.get(1),seaEntities);
                    return segments;
                }
                else if (intersections.size()==4)
                {
                    segments.add(new Segment(intersections.get(0),intersections.get(1)));
                    segments.add(new Segment(intersections.get(1),intersections.get(2)));
                    segments.add(new Segment(intersections.get(2),intersections.get(3)));
                    map.put(segments.get(1),seaEntities);
                    return segments;
                }
            }

            else if (intersections.size() > 2) {
                map.put(path, seaEntities);
                return segments;
            }

        }
        return segments;
    }

    /**
     * check if the point is on a Stream
     * @return true if the point is in the rectangle
     * @param pointA a point
     */
    public boolean positionIsOnASeaEntities(Position pointA) {
        for (SeaEntities seaEntities:listSeaEntities){
            if (seaEntities.getShape().positionIsInTheShape(pointA, seaEntities.getPosition())) return true;
        }
        return false;
    }

    public void setListSeaEntities(List<SeaEntities> seaEntities) {
        this.listSeaEntities = seaEntities;
    }

    public List<SeaEntities> getListSeaEntities() {
        return listSeaEntities;
    }

    public Map<Segment, SeaEntities> getMap() {
        return map;
    }
}
