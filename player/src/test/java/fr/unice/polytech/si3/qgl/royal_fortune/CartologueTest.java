package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)-6.06)<0.01);
    }
    @Test
    void testComputeDistance2(){
        Stream stream= new Stream(new Position(0,0),new Rectangle(1000,1000,0),50);
        Segment segment=new Segment(new Position(0,0),new Position(1000,0));
        hashMap.put(segment,stream);
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)-4.65)<0.01);
    }
    @Test
    void testComputeDistance3(){
        Stream stream= new Stream(new Position(0,0),new Rectangle(1000,1000,0),50);
        Segment segment=new Segment(new Position(0,0),new Position(1000,0));
        hashMap.put(segment,stream);
        System.out.println(cartologue.computeNumberOfRoundsNeeded(segment));
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)-8.69)<0.01);
    }
    @Test
    void testComputeDistance4(){
        Stream stream= new Stream(new Position(0,0),new Rectangle(1000,1000,0),50);
        Segment segment=new Segment(new Position(0,0),new Position(0,1000));
        hashMap.put(segment,stream);
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)-6.06)<0.01);
    }
    @Test
    void testComputeDistance5(){
        Stream stream= new Stream(new Position(0,0),new Rectangle(1000,1000,0),50);
        Segment segment=new Segment(new Position(0,0),new Position(0,1000));
        hashMap.put(segment,stream);
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)-6.06)<0.01);
    }
    @Test
    void testComputeDistance6(){
        Stream stream= new Stream(new Position(0,0),new Rectangle(1000,1000,0),50);
        Segment segment=new Segment(new Position(0,0),new Position(500,500));
        hashMap.put(segment,stream);
        double dist=segment.getLength()/(165+stream.getStrength()*Math.cos(Math.PI/4));
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)- dist)<0.01);
    }
    /**@Test
    void testCutSegment(){
        Stream stream= new Stream(new Position(200,0),new Rectangle("rectangle",100,100,0),50);
        Segment segment=new Segment(new Position(0,0),new Position(1000,0));
        listStream.add(stream);
        assertEquals(3,cartologue.cutSegment(segment,false).size());
        assertEquals(0,cartologue.cutSegment(segment,false).get(0).getPointA().getX());
        assertEquals(150,cartologue.cutSegment(segment,false).get(0).getPointB().getX());
        assertEquals(150,cartologue.cutSegment(segment,false).get(1).getPointA().getX());
        assertEquals(250,cartologue.cutSegment(segment,false).get(1).getPointB().getX());
        assertEquals(250,cartologue.cutSegment(segment,false).get(2).getPointA().getX());
        assertEquals(1000,cartologue.cutSegment(segment,false).get(3).getPointB().getX());



        assertEquals(new Segment(new Position(100,0),new Position(300,0)),cartologue.cutSegment(segment,false).get(1));

    }**/
}
