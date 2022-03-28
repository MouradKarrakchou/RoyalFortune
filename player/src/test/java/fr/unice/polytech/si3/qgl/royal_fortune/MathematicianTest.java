package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MathematicianTest {
    Mathematician mathematician;
    Cartologue cartologue;
    List<Stream> listStream;
    List<Reef> listReef;
    Wind wind;
    @BeforeEach
    void setUp() {
        listStream=new ArrayList<>();
        listReef=new ArrayList<>();
        wind=new Wind(0,50);
        cartologue=new Cartologue(listStream,listReef);
        mathematician=new Mathematician(cartologue);
    }

    @Test
    void changeBaseTest() {
        Position positionInSeaBase = Mathematician.changeBase(new Position(0,0, Math.PI/4), 2, 1);
        double scale = Math.pow(10, 4);
        double x = Math.round(positionInSeaBase.getX()*scale)/scale;
        double y = Math.round(positionInSeaBase.getY()*scale)/scale;
        double ori = Math.round(positionInSeaBase.getOrientation()*scale)/scale;

        Position positionAfterBase = new Position(0.7071, 2.1213, 0);

        assertEquals(positionAfterBase.getX(), x);
        assertEquals(positionAfterBase.getY(), y);
        assertEquals(positionAfterBase.getOrientation(), ori);
    }

    @Test
    void computeTrajectoryTest(){
        List<Beacon> beacons = new ArrayList<>();

        Beacon beacon00 = new Beacon(new Position(50, -50), new Circle());
        beacons.add(beacon00);

        Beacon beacon01 = new Beacon(new Position(50, 0), new Circle());
        beacons.add(beacon01);

        Optional<Beacon> bestBeacon = mathematician.computeTrajectory(beacons,
                new Position(0, 0), new Position(100, 0));

        assertTrue(bestBeacon.isPresent());
        assertEquals(beacon01, bestBeacon.get());
    }


    @Test
    void computeTrajectoryOnBeaconTest(){
        List<Beacon> beacons = new ArrayList<>();

        Beacon beacon01 = new Beacon(new Position(0, 0), new Circle());
        beacons.add(beacon01);

        Optional<Beacon> bestBeacon = mathematician.computeTrajectory(beacons,
                new Position(0, 0), new Position(100, 0));

        assertTrue(bestBeacon.isEmpty());
    }

}
