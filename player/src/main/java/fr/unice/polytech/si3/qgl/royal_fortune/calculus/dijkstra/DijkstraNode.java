package fr.unice.polytech.si3.qgl.royal_fortune.calculus.dijkstra;

import fr.unice.polytech.si3.qgl.royal_fortune.IPositionable;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.Stack;

public class DijkstraNode implements Comparable<DijkstraNode> {
    private final IPositionable nodeObject;
    private DijkstraNode previousNode;
    private double nodeValue;

    /**
     * Create a DijkstraNode with a given IPositionable nodeObject.
     * Previous node is null and node value is Infinite.
     * @param nodeObject - The IPositionable object situated on the node, can be :
     *                    * A Position for the checkpoint & the ship,
     *                    * A Beacon fot the beacons.
     */
    public DijkstraNode(IPositionable nodeObject){
        this.nodeObject = nodeObject;
        this.previousNode = null;
        this.nodeValue = Double.POSITIVE_INFINITY;
    }

    /**
     * Create a DijkstraNode with a given IPositionable nodeObject and a node value.
     * Previous node is null.
     * @param nodeObject - The IPositionable object situated on the node, can be :
     *                    * A Position for the checkpoint & the ship,
     *                    * A Beacon fot the beacons.
     * @param nodeValue - The value to associate to the DijkstraNode.
     */
    private DijkstraNode(IPositionable nodeObject, double nodeValue){
        this.nodeObject = nodeObject;
        this.previousNode = null;
        this.nodeValue = nodeValue;
    }

    /**
     * Generate a StartingNode.
     * A starting node is characterized by having its node value set to 0.
     * @param nodeObject - The IPositionable object situated on the node, can be :
     *                    * A Position for the checkpoint & the ship,
     *                    * A Beacon fot the beacons.
     * @return The StartingNode containing an IPositionable object and a node value of 0.
     */
    public static DijkstraNode generateStartingNode(IPositionable nodeObject){
        return new DijkstraNode(nodeObject, 0);
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
     * For a given DijkstraNode, returns all the Beacons that must be traversed to reach it.
     * @return The Beacons that must be traversed to reach the given DijkstraNode.
     */
    public Stack<Beacon> getPath(){
        return getPath(new Stack<>(), this);
    }

    /**
     * RECURSIVE
     * For a given DijkstraNode, returns all the Beacons that must be traversed to reach it.
     * @param currentPath The current path already calculates.
     * @param currentNode - The DijkstraNode to retrieve the path.
     * @return The Beacons that must be traversed to reach the given DijkstraNode.
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
