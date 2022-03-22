package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartologueTest {
    Cartologue cartologue;
    List<Stream> listStream;
    List<Reef> listReef;
    Wind wind;
    HashMap<Segment, SeaEntities> hashMap;
    @BeforeEach
    void init() {
        listStream=new ArrayList<>();
        listReef=new ArrayList<>();
        wind=new Wind();
        cartologue=new Cartologue(listStream,listReef,wind);
        hashMap=cartologue.getHashMap();
    }
    @Test
    void testComputeDistance1(){
        Segment segment=new Segment(new Position(0,0),new Position(1000,0));
        assertTrue(cartologue.computeNumberOfRoundsNeeded(segment)-6.06<0.01);
    }
    @Test
    void testComputeDistance2(){
        Segment segment=new Segment(new Position(0,0),new Position(1000,0));
        assertTrue(cartologue.computeNumberOfRoundsNeeded(segment)-6.06<0.01);
    }
}
