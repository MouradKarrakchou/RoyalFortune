package fr.unice.polytech.si3.qgl.royal_fortune.dijkstra;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.dijkstra.Dijkstra;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstraTest {
    List<Reef> reefList;
    List<Beacon> beaconList;


    @BeforeEach
    void init(){
        reefList = new ArrayList<>();
        beaconList = new ArrayList<>();
    }

    @Test
    void proceedDijkstraTest(){
        Reef reef00 = new Reef(new Position(0, 500), new Rectangle(100, 100, 0));
        reefList.add(reef00);

        Beacon beacon00 = new Beacon(new Position(700, 0));
        beaconList.add(beacon00);

        Cartologue cartologue = new Cartologue(new ArrayList<>(), reefList);

        Position departurePosition = new Position(0, 0);
        Position arrivalPosition = new Position(0, 1000);
        Stack<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(1, beaconPath.size());
        assertEquals(beacon00, beaconPath.pop());
    }
}
