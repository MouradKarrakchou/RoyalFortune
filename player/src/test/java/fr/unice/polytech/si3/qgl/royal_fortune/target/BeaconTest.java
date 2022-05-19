package fr.unice.polytech.si3.qgl.royal_fortune.target;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeaconTest {

    @Test
    void toStringTest(){
        Beacon b = new Beacon(new Position(0,0,0));
        assertEquals("{\"x\":0.0,\"y\":0.0,\"orientation\":0.0}", b.toString());
    }
}
