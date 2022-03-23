package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObserverTest {
    Wind wind;
    Position shipPosition;
    Position nextCheckPointPosition;
    Observer observer;
    List<SeaEntities> newSeaEntities;
    @BeforeEach
    void init() {
        shipPosition=new Position(0,0,0);
        nextCheckPointPosition=new Position(1000,0);
        wind=new Wind(0,100);
        observer=new Observer(shipPosition,wind,nextCheckPointPosition);
        newSeaEntities=new ArrayList<>();
    }
    @Test
    void watchSeaTest(){
        newSeaEntities.add(new Stream(new Position(100,100,0),new Rectangle(100,100,0),50));
        //assertEquals(,observer.watchSea(newSeaEntities));
    }
}
