package fr.unice.polytech.si3.qgl.royal_fortune.dijkstra;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.dijkstra.DijkstraNode;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DijkstraNodeTest {
    private DijkstraNode departureNode;
    private DijkstraNode node;

    @BeforeEach
    void init() {
        departureNode = DijkstraNode.generateStartingNode(null);
        node = new DijkstraNode(null);
    }

    @Test
    void generateStartingNodeTest(){
        assertEquals(0, departureNode.getNodeValue());
        assertTrue(Double.isInfinite(node.getNodeValue()));
        assertTrue(departureNode.compareTo(node) < 0);
    }

    @Test
    void setNodeValueTest(){
        departureNode.setNodeValue(1);
        node.setNodeValue(0);
        assertTrue(departureNode.compareTo(node) > 0);
    }

    @Test
    void pathTest(){
        Beacon beacon01 = new Beacon(new Position(0, 0));
        DijkstraNode node01 = new DijkstraNode(beacon01);
        node01.setPreviousNode(departureNode);

        Beacon beacon02 = new Beacon(new Position(0, 1));
        DijkstraNode node02 = new DijkstraNode(beacon02);
        node02.setPreviousNode(node01);

        Beacon beacon03 = new Beacon(new Position(0, 2));
        DijkstraNode node03 = new DijkstraNode(beacon03);
        node03.setPreviousNode(node02);

        Beacon beacon04 = new Beacon(new Position(0, 3));
        DijkstraNode node04 = new DijkstraNode(beacon04);
        node04.setPreviousNode(node03);

        List<Beacon> stack = node04.getPath();
        assertEquals(4, stack.size());
        assertEquals(beacon01, stack.remove(0));
        assertEquals(beacon02, stack.remove(0));
        assertEquals(beacon03, stack.remove(0));
        assertEquals(beacon04, stack.remove(0));
    }
}
