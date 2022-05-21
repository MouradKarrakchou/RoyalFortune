package fr.unice.polytech.si3.qgl.royal_fortune.dijkstra;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.dijkstra.Dijkstra;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DijkstraTest {
    List<Reef> reefList;
    List<Beacon> beaconList;


    @BeforeEach
    void init(){
        Dijkstra.clearMap();
        reefList = new ArrayList<>();
        beaconList = new ArrayList<>();
    }

    @Test
    void proceedDijkstra0Node0ReefTest(){
        Cartologue cartologue = new Cartologue(new ArrayList<>(), new ArrayList<>());
        Position departurePosition = new Position(0, 0);
        Position arrivalPosition = new Position(500, 500);
        List<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(0, beaconPath.size());
    }

    @Test
    void proceedDijkstra3Nodes1ReefTest(){
        Reef reef00 = new Reef(new Position(0, 500), new Rectangle(100, 100, 0));
        reefList.add(reef00);

        Beacon beacon00 = new Beacon(new Position(700, 0));
        beaconList.add(beacon00);

        Cartologue cartologue = new Cartologue(new ArrayList<>(), reefList);

        Position departurePosition = new Position(0, 0);
        Position arrivalPosition = new Position(0, 1000);
        List<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(1, beaconPath.size());
        assertEquals(beacon00, beaconPath.remove(0));
    }

    @Test
    void proceedDijkstra6Nodes1ReefTest(){
        Reef reef00 = new Reef(new Position(750, 100), new Rectangle(900, 100, 0));
        reefList.add(reef00);

        Beacon beacon00 = new Beacon(new Position(500, 0));
        beaconList.add(beacon00);

        Beacon beacon01 = new Beacon(new Position(1000, 0));
        beaconList.add(beacon01);

        Beacon beacon02 = new Beacon(new Position(500, -500));
        beaconList.add(beacon02);

        Beacon beacon03 = new Beacon(new Position(1000, -500));
        beaconList.add(beacon03);

        Cartologue cartologue = new Cartologue(new ArrayList<>(), reefList);

        Position departurePosition = new Position(0, 0);
        Position arrivalPosition = new Position(1000, 500);
        List<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(2, beaconPath.size());
        assertEquals(beacon02, beaconPath.remove(0));
        assertEquals(beacon03, beaconPath.remove(0));
    }

    @Test
    void proceedDijkstra6Nodes1ReefTest2(){
        Dijkstra.clearMap();
        Reef reef00 = new Reef(new Position(800, 250), new Rectangle(1300, 200, 0));
        reefList.add(reef00);

        Beacon beacon00 = new Beacon(new Position(500, 0));
        beaconList.add(beacon00);

        Beacon beacon01 = new Beacon(new Position(500, 1000));
        beaconList.add(beacon01);

        Beacon beacon02 = new Beacon(new Position(500, -500));
        beaconList.add(beacon02);

        Beacon beacon03 = new Beacon(new Position(1000, -500));
        beaconList.add(beacon03);

        Cartologue cartologue = new Cartologue(new ArrayList<>(), reefList);

        Position departurePosition = new Position(0, 0);
        Position arrivalPosition = new Position(1000, 1000);
        List<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(1, beaconPath.size());
    }

    @Test
    void proceedDijkstra4Nodes1ReefTest(){
        Reef reef00 = new Reef(new Position(550, -100), new Rectangle(200, 700, 0));
        reefList.add(reef00);

        Beacon beacon00 = new Beacon(new Position(1000, 1000));
        beaconList.add(beacon00);

        Beacon beacon01 = new Beacon(new Position(0, -300));
        beaconList.add(beacon01);

        Beacon beacon02 = new Beacon(new Position(1000, -300));
        beaconList.add(beacon02);

        Cartologue cartologue = new Cartologue(new ArrayList<>(), reefList);

        Position departurePosition = new Position(0, 0);
        Position arrivalPosition = new Position(1000, -100);
        List<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);

        assertEquals(2, beaconPath.size());
        assertEquals(beacon01, beaconPath.remove(0));
        assertEquals(beacon02, beaconPath.remove(0));
    }

    @Test
    void proceedDijkstra1CircularReefTest(){
        Reef circularReef = new Reef(new Position(800, 600), new Circle(200));
        reefList.add(circularReef);

        Beacon beacon00 = new Beacon(new Position(-1000, 1000));
        beaconList.add(beacon00);

        Cartologue cartologue = new Cartologue(new ArrayList<>(), reefList);

        Position departurePosition = new Position(200, 200);
        Position arrivalPosition = new Position(1000, 1000);
        List<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(1, beaconPath.size());
        assertEquals(beacon00, beaconPath.remove(0));
    }
}
