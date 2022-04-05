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
    void proceedDijkstra0Node0ReefTest(){
        Cartologue cartologue = new Cartologue(new ArrayList<>(), new ArrayList<>());
        Position departurePosition = new Position(0, 0);
        Position arrivalPosition = new Position(500, 500);
        Stack<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(0, beaconPath.size());
    }

    @Test
    void proceedDijkstraCanOnlyCrashTest(){
        Reef reef00 = new Reef(new Position(0, 500), new Rectangle(100, 100, 0));
        reefList.add(reef00);

        Cartologue cartologue = new Cartologue(new ArrayList<>(), new ArrayList<>());
        Position departurePosition = new Position(0, 0);
        Position arrivalPosition = new Position(0, 1000);
        Stack<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
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
        Stack<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(1, beaconPath.size());
        assertEquals(beacon00, beaconPath.pop());
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
        Stack<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(2, beaconPath.size());
        assertEquals(beacon02, beaconPath.pop());
        assertEquals(beacon03, beaconPath.pop());
    }

    @Test
    void proceedDijkstra6Nodes1ReefTest2(){
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
        Stack<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);
        assertEquals(1, beaconPath.size());
        assertEquals(beacon01, beaconPath.pop());
    }

    @Test
    void proceedDijkstra13Nodes2ReefTest(){
        Reef reef00 = new Reef(new Position(400, 0), new Rectangle(400, 400, 0));
        reefList.add(reef00);

        Reef reef01 = new Reef(new Position(1100, 300), new Rectangle(400, 400, 0));
        reefList.add(reef01);

        Beacon beacon00 = new Beacon(new Position(100, 300));
        beaconList.add(beacon00);

        Beacon beacon01 = new Beacon(new Position(100, -300));
        beaconList.add(beacon01);

        Beacon beacon02 = new Beacon(new Position(1400, 0));
        beaconList.add(beacon02);

        Beacon beacon03 = new Beacon(new Position(700, 700));
        beaconList.add(beacon03);

        Beacon beacon04 = new Beacon(new Position(700, 300));
        beaconList.add(beacon04);

        Beacon beacon05 = new Beacon(new Position(700, -300));
        beaconList.add(beacon05);

        Beacon beacon06 = new Beacon(new Position(700, -600));
        beaconList.add(beacon06);

        Beacon beacon07 = new Beacon(new Position(100, 700));
        beaconList.add(beacon07);

        Beacon beacon08 = new Beacon(new Position(100, -600));
        beaconList.add(beacon08);

        Beacon beacon09 = new Beacon(new Position(1400, -600));
        beaconList.add(beacon09);

        Beacon beacon10 = new Beacon(new Position(1400, 700));
        beaconList.add(beacon10);

        Cartologue cartologue = new Cartologue(new ArrayList<>(), reefList);

        Position departurePosition = new Position(-200, 0);
        Position arrivalPosition = new Position(1400, 300);
        Stack<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);

        assertEquals(3, beaconPath.size());
        assertEquals(beacon01, beaconPath.pop());
        assertEquals(beacon05, beaconPath.pop());
        assertEquals(beacon02, beaconPath.pop());
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
        Stack<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beaconList);

        assertEquals(2, beaconPath.size());
        assertEquals(beacon01, beaconPath.pop());
        assertEquals(beacon02, beaconPath.pop());
    }
}
