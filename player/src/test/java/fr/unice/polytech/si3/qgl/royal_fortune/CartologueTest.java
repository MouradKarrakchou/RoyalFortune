package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CartologueTest {
    Cartologue cartologue;
    List<Stream> listStream;
    List<Reef> listReef;
    Wind wind;
    Map<Segment, SeaEntities> map;
    @BeforeEach
    void init() {
        listStream=new ArrayList<>();
        listReef=new ArrayList<>();
        wind=new Wind();
        cartologue=new Cartologue(listStream,listReef);
        map = cartologue.getMap();
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
        map.put(segment,stream);
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)-4.65)<0.01);

        stream = new Stream(new Position(0,0, Math.PI),new Rectangle(1000,1000, Math.PI),50);
        segment = new Segment(new Position(0,0),new Position(1000,0));
        map.put(segment,stream);
        System.out.println(cartologue.computeNumberOfRoundsNeeded(segment));
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)-8.69)<0.01);

        stream= new Stream(new Position(0,0),new Rectangle(1000,1000,0),50);
        segment=new Segment(new Position(0,0),new Position(0,1000));
        map.put(segment,stream);
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)-6.06)<0.01);

        stream = new Stream(new Position(0,0),new Rectangle(1000,1000,0),50);
        segment = new Segment(new Position(0,0),new Position(0,1000));
        map.put(segment,stream);
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)-6.06)<0.01);
    }

    @Test
    void testComputeDistance3(){
        Stream stream= new Stream(new Position(0,0),new Rectangle(1000,1000,0),50);
        Segment segment=new Segment(new Position(0,0),new Position(500,500));
        map.put(segment,stream);
        double dist=segment.getLength()/(165+stream.getStrength()*Math.cos(Math.PI/4));
        assertTrue(Math.abs(cartologue.computeNumberOfRoundsNeeded(segment)- dist)<0.01);
    }

    @Test
    void testComputeDistanceInfinity1() {
        Stream stream = new Stream(new Position(0,0, Math.PI),new Rectangle(1000,1000, Math.PI),165);
        Segment segment = new Segment(new Position(0,0),new Position(1000,0));
        map.put(segment,stream);

        assertEquals(Double.POSITIVE_INFINITY, cartologue.computeNumberOfRoundsNeeded(segment));
    }

    @Test
    void testComputeDistanceInfinity2() {
        Stream stream = new Stream(new Position(0,0, Math.PI),new Rectangle(1000,1000, Math.PI),166);
        Segment segment = new Segment(new Position(0,0),new Position(1000,0));
        map.put(segment,stream);

        assertEquals(Double.POSITIVE_INFINITY, cartologue.computeNumberOfRoundsNeeded(segment));
    }

    @Test
    void testCutSegmentX(){
        Stream stream = new Stream(new Position(200,0, 0), new Rectangle(100,100,0),50);
        Segment segment = new Segment(new Position(0,0),new Position(1000,0));
        listStream.add(stream);
        cartologue = new Cartologue(listStream, listReef);
        map = cartologue.getMap();
        List<Segment> cartoCut = cartologue.cutSegment(segment, false);
        assertEquals(3,cartoCut.size());
        assertEquals(0,cartoCut.get(0).getPointA().getX());
        assertEquals(145,cartoCut.get(0).getPointB().getX());
        assertEquals(145,cartoCut.get(1).getPointA().getX());
        assertEquals(255,cartoCut.get(1).getPointB().getX());
        assertEquals(255,cartoCut.get(2).getPointA().getX());
        assertEquals(1000,cartoCut.get(2).getPointB().getX());
        assertEquals(stream, map.get(cartoCut.get(1)));
        assertFalse(map.containsKey(cartoCut.get(0)));
        assertFalse(map.containsKey(cartoCut.get(2)));
    }

    @Test
    void testCutSegmentY(){
        Stream stream= new Stream(new Position(0,200),new Rectangle(100,100,0),50);
        Segment segment=new Segment(new Position(0,0),new Position(0,1000));
        listStream.add(stream);
        cartologue=new Cartologue(listStream,listReef);
        map=cartologue.getMap();
        List<Segment> cartoCut = cartologue.cutSegment(segment, false);
        assertEquals(3,cartoCut.size());
        assertEquals(0,cartoCut.get(0).getPointA().getY());
        assertEquals(145,cartoCut.get(0).getPointB().getY());
        assertEquals(145,cartoCut.get(1).getPointA().getY());
        assertEquals(255,cartoCut.get(1).getPointB().getY());
        assertEquals(255,cartoCut.get(2).getPointA().getY());
        assertEquals(1000,cartoCut.get(2).getPointB().getY());
        assertEquals(stream, map.get(cartoCut.get(1)));
        assertFalse(map.containsKey(cartoCut.get(0)));
        assertFalse(map.containsKey(cartoCut.get(2)));
    }

    @Test
    void cutSegmentNotStream() {
        Reef reef = new Reef(new Position(0,200),new Rectangle(100,100,0));
        Segment segment = new Segment(new Position(0,0),new Position(0,1000));

        listReef.add(reef);
        cartologue = new Cartologue(listStream,listReef);
        map = cartologue.getMap();

        List<Segment> segmentList = new ArrayList<>();
        assertEquals(segmentList, cartologue.cutSegment(segment, false));
    }

    @Test
    void cutSegment3Intersections1() {
        Stream stream = new Stream(new Position(300,0), new Rectangle(70,70,0), 50);
        Segment segment = new Segment(new Position(0,0),new Position(300,0));

        listStream.add(stream);
        cartologue = new Cartologue(listStream,listReef);
        map = cartologue.getMap();

        List<Segment> cartoCut = cartologue.cutSegment(segment, false);

        assertEquals(2, cartoCut.size());

        assertEquals(0, cartoCut.get(0).getPointA().getX());
        assertEquals(0, cartoCut.get(0).getPointA().getY());
        assertEquals(260, cartoCut.get(0).getPointB().getX());
        assertEquals(0, cartoCut.get(0).getPointB().getY());

        assertEquals(260, cartoCut.get(1).getPointA().getX());
        assertEquals(0, cartoCut.get(1).getPointA().getY());
        assertEquals(300, cartoCut.get(1).getPointB().getX());
        assertEquals(0, cartoCut.get(1).getPointB().getY());

        assertFalse(map.containsKey(cartoCut.get(0)));
        assertTrue(map.containsKey(cartoCut.get(1)));
    }

    @Test
    void cutSegment3Intersections2() {
        Stream stream = new Stream(new Position(300,0), new Rectangle(70,70,0), 50);
        Segment segment = new Segment(new Position(0,0),new Position(300,0));

        listStream.add(stream);
        cartologue = new Cartologue(listStream,listReef);
        map = cartologue.getMap();

        List<Segment> cartoCut = cartologue.cutSegment(segment, true);

        assertEquals(2, cartoCut.size());

        assertEquals(0, cartoCut.get(0).getPointA().getX());
        assertEquals(0, cartoCut.get(0).getPointA().getY());
        assertEquals(260, cartoCut.get(0).getPointB().getX());
        assertEquals(0, cartoCut.get(0).getPointB().getY());

        assertEquals(260, cartoCut.get(1).getPointA().getX());
        assertEquals(0, cartoCut.get(1).getPointA().getY());
        assertEquals(300, cartoCut.get(1).getPointB().getX());
        assertEquals(0, cartoCut.get(1).getPointB().getY());

        assertTrue(map.containsKey(cartoCut.get(0)));
        assertFalse(map.containsKey(cartoCut.get(1)));
    }

    @Test
    void positionIsOnASeaEntities() {
        Stream stream = new Stream(new Position(300,0), new Rectangle(70,70,0), 50);
        Position position1 = new Position(300, 0);
        Position position2 = new Position(0, 0);

        listStream.add(stream);
        cartologue = new Cartologue(listStream,listReef);

        assertTrue(cartologue.positionIsOnASeaEntities(position1));
        assertFalse(cartologue.positionIsOnASeaEntities(position2));
    }

}
