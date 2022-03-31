package fr.unice.polytech.si3.qgl.royal_fortune.calculus.dijkstra;

import fr.unice.polytech.si3.qgl.royal_fortune.IPositionable;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.Stack;

public class DijkstraNode implements Comparable<DijkstraNode> {
    private final IPositionable nodeObject;
    private DijkstraNode previousNode;
    private double nodeValue;

    /**
     * @param nodeObject - The object situated on the node
     *                    * A Position for the checkpoint & the ship
     *                    * A Beacon fot the beacons.
     */
    public DijkstraNode(IPositionable nodeObject){
        this.nodeObject = nodeObject;
        this.previousNode = null;
        this.nodeValue = Double.POSITIVE_INFINITY;
    }

    private DijkstraNode(IPositionable currentNode, double nodeValue){
        this.nodeObject = currentNode;
        this.previousNode = null;
        this.nodeValue = nodeValue;
    }

    public static DijkstraNode generateStartingNode(IPositionable currentNode){
        return new DijkstraNode(currentNode, 0);
    }

    public void setPreviousNode(DijkstraNode previousNode){
        this.previousNode = previousNode;
    }

    public void setNodeValue(double nodeValue){
        this.nodeValue = nodeValue;
    }

    public IPositionable getNode(){
        return this.nodeObject;
    }

    public double getNodeValue(){
        return this.nodeValue;
    }

    /**
     * @return The shortest path to access this Node.
     */
    public Stack<Beacon> getPath(){
        return getPath(new Stack<>(), this);
    }

    /**
     * RECURSIVE
     * For a given Path return
     * @param currentPath The current path already calculates.
     * @param currentNode - The node to retrieve the path
     * @return The shortest path to access the given node.
     */
    private Stack<Beacon> getPath(Stack<Beacon> currentPath, DijkstraNode currentNode){
        if (currentNode == null)
            return currentPath;

        // We only add Beacon to the beacon list. Ship position & checkpoint position are ignored.
        if (currentNode.nodeObject instanceof Beacon beaconNode)
            currentPath.add(beaconNode);

        return(getPath(currentPath, currentNode.previousNode));
    }


    @Override
    public int compareTo(DijkstraNode node) {
        return (int) (this.nodeValue - node.nodeValue);
    }
}
