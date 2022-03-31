package fr.unice.polytech.si3.qgl.royal_fortune.calculus.dijkstra;

import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DijkstraNode {
    private final Beacon node;
    private DijkstraNode previousNode;
    private double nodeValue;

    public DijkstraNode(Beacon currentNode, DijkstraNode previousNode, double nodeValue){
        this.node = currentNode;
        this.previousNode = previousNode;
        this.nodeValue = nodeValue;
    }

    public void setPreviousNode(DijkstraNode previousNode){
        this.previousNode = previousNode;
    }

    public void setNodeValue(double nodeValue){
        this.nodeValue = nodeValue;
    }

    /**
     * @return The shortest path to access this Node.
     */
    public List<Beacon> getPath(){
        List<Beacon> nodePath = getPath(new ArrayList<>(), this);
        Collections.reverse(nodePath);
        return nodePath;
    }

    /**
     * RECURSIVE
     * For a given Path return
     * @param currentPath The current path already calculates.
     * @param currentNode - The node to retrieve the path
     * @return The shortest path to access the given node.
     */
    private List<Beacon> getPath(List<Beacon> currentPath, DijkstraNode currentNode){
        if (currentNode == null)
            return currentPath;

        currentPath.add(currentNode.node);
        return(getPath(currentPath, currentNode.previousNode));
    }
}
