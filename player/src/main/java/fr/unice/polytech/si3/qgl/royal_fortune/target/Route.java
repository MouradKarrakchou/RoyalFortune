package fr.unice.polytech.si3.qgl.royal_fortune.target;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Route implements Comparable{
    private Route firstRoute;
    private Route secondRoute;
    private double value;
    private List<Segment> listSegment;
    private Cartologue cartologue;
    private Boolean reefCollision;

    /**
     * Create a route using a single segment
     * We try to slice the segment by its intersection with SeaEntities.<br>
     * If no intersection the route is a leaf route else we distribute the segment list between the first and the second route.
     * @param segment a segment
     */
    public Route(Segment segment,Cartologue cartologue){
        this.cartologue = cartologue;
        this.listSegment = new ArrayList<>();
        this.listSegment.add(segment);
        List<Segment> slicedSegments = sliceSegment(segment);
        if(slicedSegments.size() > 0){
            listSegment = slicedSegments;
            distributeSegments(listSegment);
            value=firstRoute.getValue()+ secondRoute.getValue();
        }
        else{
            value=cartologue.computeNumberOfRoundsNeeded(segment);
        }
    }

    /**
     * Create a route using a list of segment, we distribute all the segment between the two lower route of the current route to build other route
     * @param listSegment a list of segments
     * @param cartologue a cartologue
     */
    public Route(List<Segment> listSegment, Cartologue cartologue) {
        this.cartologue = cartologue;
        this.listSegment = listSegment;
        distributeSegments(listSegment);
        value=firstRoute.getValue()+ secondRoute.getValue();
    }

    /**
     * Cut the segment list in two part by using /2.<br>
     * If we have a list like <strong>{AB,BC,CD}</strong> the first segment AB will go in firstRoute and the other <strong>(BC,CD)</strong> in the second.<br>
     * If we have a list like <strong>{AB,BC,CD,DE}</strong> the two first segment AB and BC will go in firstRoute and the other <strong>(CD,DE)</strong> in the second.<br>
     * @param listSegment list of segments
     */
    public void distributeSegments(List<Segment> listSegment) {
        List<Segment> segmentFirstRoute = new ArrayList<>();
        List<Segment> segmentSecondRoute = new ArrayList<>();
        for(int i = 0 ; i < listSegment.size()/2;i++)segmentFirstRoute.add(listSegment.get(i));
        for(int i = listSegment.size()/2 ; i <listSegment.size() ;i++)segmentSecondRoute.add(listSegment.get(i));
        if (segmentFirstRoute.size()==1)
            firstRoute = new Route(segmentFirstRoute.get(0), cartologue);
        else
            firstRoute = new Route(segmentFirstRoute, cartologue);
        if (segmentSecondRoute.size()==1)
            secondRoute = new Route(segmentSecondRoute.get(0), cartologue);
        else
            secondRoute = new Route(segmentSecondRoute, cartologue);
    }


    /**
     * slice the segment by intersection between seaEntities using the cartologue
     * @return list of segments
     */
    public List<Segment> sliceSegment(Segment segment) {
        //use cartologue to slice segment
        return cartologue.sliceSegmentByInteraction(segment);
    }

    public List<Segment> getListSegment() {
        return listSegment;
    }

    public Route getFirstRoute() {
        return firstRoute;
    }

    public Route getSecondRoute() {
        return secondRoute;
    }

    public double getValue() {
        return value;
    }

    public Cartologue getCartologue() {
        return cartologue;
    }


    @Override
    public int compareTo(Object o) {
        return Double.compare(this.value, ((Route) o).getValue());
    }

    @Override
    public String toString() {
        return toString(0,0);
    }


        public String toString(int i, int indentValue){
        if(firstRoute == null && secondRoute == null){
            return indent(indentValue)+"R"+i+"* -> "+listSegment.toString()+"\n";
        }
        else {
            return indent(indentValue)+"R"+i+":\n" +
                    firstRoute.toString(i+1,indentValue+1)+
                    secondRoute.toString(i+2, indentValue+1);
        }
    }


    public String indent(int j) {
        String out = "";
        for(int i = 0 ; i < j ; i++) out += "\t";
        return out;
    }
}
