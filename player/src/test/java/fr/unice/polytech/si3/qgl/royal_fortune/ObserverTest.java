package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObserverTest {
    List<SeaEntities> currentSeaEntities;
    Position shipPosition;
    Position nextCheckPointPosition;
    Observer observer;

    @BeforeEach
    void init(){
        currentSeaEntities=new ArrayList<>();
        shipPosition=new Position(0,0,0);
        nextCheckPointPosition=new Position(1000,0,0);
        observer=new Observer();
    }

    @Test
    void watchSeaTest(){
        Stream stream=new Stream(new Position(500,0,0), new Rectangle(100,100,0),1000000);
        currentSeaEntities.add(stream);
        observer.setShipPosition(shipPosition);
        observer.setNextCheckPointPosition(nextCheckPointPosition);
        Stack<Beacon> beacons = observer.watchSea(currentSeaEntities);
        assertEquals("AYOUB REPART CE TEST STP", beacons.size());
    }

    @Test
    void watchSea2Test() {
        Reef reef = new Reef(new Position(500,0,0), new Rectangle(100,100, 0));
        currentSeaEntities.add(reef);
        observer.setShipPosition(shipPosition);
        observer.setNextCheckPointPosition(new Position(1000,100,0));
        Stack<Beacon> beacons =observer.watchSea(currentSeaEntities);
        assertEquals(1, beacons.size());
    }

    @Test
    void getStreamTestNoStream(){
        List<SeaEntities> listWithoutStream = new ArrayList<SeaEntities>();
        listWithoutStream.add(new SeaEntities());
        assertTrue(observer.getStream(listWithoutStream).isEmpty());
    }

    @Test
    void getStreamTestOneStream(){
        List<SeaEntities> listWithoutStream = new ArrayList<SeaEntities>();
        listWithoutStream.add(new Stream());
        assertEquals(1, observer.getStream(listWithoutStream).size());
    }

    @Test
    void getReefTestNoReef(){
        List<SeaEntities> listWithoutReef = new ArrayList<SeaEntities>();
        listWithoutReef.add(new SeaEntities());
        assertTrue(observer.getReef(listWithoutReef).isEmpty());
    }

    @Test
    void getReefTestOneReef(){
        List<SeaEntities> listWithoutReef = new ArrayList<SeaEntities>();
        listWithoutReef.add(new Reef());
        assertEquals(1, observer.getReef(listWithoutReef).size());
    }


}
