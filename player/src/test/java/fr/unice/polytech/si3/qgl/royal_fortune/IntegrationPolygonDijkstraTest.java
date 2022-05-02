package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryCircle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.dijkstra.Dijkstra;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Polygone;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationPolygonDijkstraTest {

    @Test
    void integrationTest(){
        List<Reef> reefList = new ArrayList<>();

        // Maison
        Point[] vertices = {
                new Point(-1000, 1000),
                new Point(1000, 1000),
                new Point(2000, 0),
                new Point(-1000, -1000),
                new Point(1000, -1000)
        };
        Polygone maison = new Polygone(vertices, -0.7504915783575616);
        Position maisonPosition = new Position(4126.466753585396, 1888.0208333333337, -0.7504915783575616);
        reefList.add(new Reef(maisonPosition, maison));
        maison.updatePolygone(maisonPosition);

        // Left big rectangle
        Rectangle rectangle01 = new Rectangle(550, 1150, -1.0471975511965976);
        Position rectangle01Position = new Position(3637.548891786179, 3515.624999999998, -1.0471975511965976);
        reefList.add(new Reef(rectangle01Position, rectangle01));
        rectangle01.updateForReef();

        // Right big rectangle
        Rectangle rectangle02 = new Rectangle(500, 1000, -0.767944870877505);
        Position rectangle02Position = new Position(2529.335071707953, 2376.3020833333358, -0.767944870877505);
        reefList.add(new Reef(rectangle02Position, rectangle02));
        rectangle02.updateForReef();

        // Small rectangle between big left and big right
        Rectangle rectangle03 = new Rectangle(250, 350, 1.1344640137963142);
        Position rectangle03Position = new Position(2470.664928292048, 2903.645833333334, 1.1344640137963142);
        reefList.add(new Reef(rectangle03Position, rectangle03));
        rectangle03.updateForReef();



        // Maison
        Point[] vertices1 = {
                new Point(750, -125),
                new Point(350, 125),
                new Point(-100, 125),
                new Point(-250, -125)
        };
        Polygone p1 = new Polygone(vertices1, 2.356194490192345);
        Position p1Position = new Position(3911.3428943937456, 566.4062500000032, 2.356194490192345);
        reefList.add(new Reef(p1Position, maison));
        p1.updatePolygone(p1Position);


        // Small circle between big left and big right
        Circle circle01 = new Circle(100);
        Position circle01Position = new Position(2822.6857887874835, 3320.3125, 0);
        reefList.add(new Reef(circle01Position, circle01));
        circle01.updateForReef();

        // Small circle between big left and big right
        Circle circle03 = new Circle(100);
        Position circle03Position = new Position(2868.3181225554163, 3391.9270833333358, 0);
        reefList.add(new Reef(circle03Position, circle03));
        circle01.updateForReef();

        // Cercle perdu en bas à droite
        Circle circle02 = new Circle(250);
        Position circle02Position = new Position(3617.992177314213, 4720.052083333333, 0);
        reefList.add(new Reef(circle02Position, circle02));
        circle01.updateForReef();



        List<Beacon> beacons = new ArrayList<>();
        beacons.addAll(maison.generateBeacon(maisonPosition, true));
        beacons.addAll(p1.generateBeacon(p1Position, true));
        beacons.addAll(rectangle01.generateBeacon(rectangle01Position, true));
        beacons.addAll(rectangle02.generateBeacon(rectangle02Position, true));
        beacons.addAll(rectangle03.generateBeacon(rectangle03Position, true));

        Cartologue cartologue = new Cartologue(new ArrayList<>(), reefList);
        Position departurePosition = new Position(3037.8096479791434, 2441.4062500000095);
        Position arrivalPosition = new Position(5769.230769230773, 260.41666666666896);

        Stack<Beacon> beaconPath = Dijkstra.proceedDijkstra(departurePosition, arrivalPosition, cartologue, beacons);

        assertEquals(4, beaconPath.size());
    }

    @Test
    void integrationFocusTest(){
        Point[] vertices = {
                new Point(-1000, 1000),
                new Point(1000, -1000),
                new Point(2000, 0),
                new Point(1000, 1000),
                new Point(-1000, -1000)
        };
        List<Reef> reefList = new ArrayList<>();

        Polygone p = new Polygone(vertices, 0);
        p.setUpdated(true);
        reefList.add(new Reef(new Position(0, 0), p));





        Cartologue cartologue = new Cartologue(new ArrayList<>(), reefList);
        List<Segment> segments = cartologue.sliceSegmentByInteraction(new Segment(
                new Position(1021, -1021),
                new Position(2030, 0)
        ));



        assertEquals(0, cartologue.getMap().size());
    }
    
}
