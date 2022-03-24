package fr.unice.polytech.si3.qgl.royal_fortune.target;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteTest {
    List<Stream> listStream;
    List<Reef> listReef;
    Wind wind;
    Segment segment;
    Cartologue cartologue;
    Route route;

    @BeforeEach
    void init() {
        listStream = new ArrayList<>();
        listReef = new ArrayList<>();
        wind = new Wind(0., 5.);
        cartologue = new Cartologue(listStream, listReef);

        segment = new Segment(new Position(0, 0, 0), new Position(10, 0, 0));
        route = new Route(segment, cartologue);
    }

    @Test
    void createLeaveRouteNotStreamNotReef() {
        assertTrue(route.getFirstRoute() == null);
        assertTrue(route.getSecondRoute() == null);
    }

    @Test
    void createRouteThatContainTwoLeaveRoute() {
        Segment segment1 = new Segment(new Position(0, 0, 0), new Position(10, 0, 0));
        Segment segment2 = new Segment(new Position(10, 0, 0), new Position(10, 10, 0));
        Segment segment3 = new Segment(new Position(10, 10, 0), new Position(30, 10, 0));

        List<Segment> listSegment = new ArrayList<>();
        listSegment.add(segment1);
        listSegment.add(segment2);
        listSegment.add(segment3);

        Route route = new Route(listSegment, cartologue);
        assertTrue(route.getFirstRoute().getListSegment().size() == 1);
        assertNull(route.getFirstRoute().getFirstRoute());
        assertNull(route.getFirstRoute().getSecondRoute());

        assertTrue(route.getSecondRoute().getListSegment().size() == 2);
        assertEquals(segment2, route.getSecondRoute().getFirstRoute().getListSegment().get(0));
        assertEquals(segment3, route.getSecondRoute().getSecondRoute().getListSegment().get(0));
    }

    @Test
    void createRouteThatCutAStream() {
        Segment segment1 = new Segment(new Position(0, 0, 0), new Position(10, 0, 0));
        Stream stream = new Stream(new Position(5,0,0), new Rectangle(2,2,0), 5);
        listStream.add(stream);
        cartologue=new Cartologue(listStream,listReef);
        Route route = new Route(segment1, cartologue);
        assertTrue(route.getListSegment().size() == 3);
        assertTrue(route.getFirstRoute().getListSegment().size() == 1);
        assertNull(route.getFirstRoute().getFirstRoute());
        assertNull(route.getFirstRoute().getSecondRoute());
        //-----------> A DECOMMENTER
        assertEquals(route.getFirstRoute().getValue()+route.getSecondRoute().getValue(),route.getValue());

        assertTrue(route.getSecondRoute().getListSegment().size() == 2);
        assertEquals(route.getListSegment().get(1), route.getSecondRoute().getFirstRoute().getListSegment().get(0));
        assertEquals(route.getListSegment().get(2), route.getSecondRoute().getSecondRoute().getListSegment().get(0));
    }

    @Test
    void createRouteThatCutAStream2() {
        Segment segment1 = new Segment(new Position(0, 0, 0), new Position(10, 1, 0));
        Stream stream = new Stream(new Position(5,0,0), new Rectangle(2,2,0), 5);
        listStream.add(stream);
        cartologue=new Cartologue(listStream,listReef);
        Route route = new Route(segment1, cartologue);
        assertTrue(route.getListSegment().size() == 3);
        assertTrue(route.getFirstRoute().getListSegment().size() == 1);
        assertNull(route.getFirstRoute().getFirstRoute());
        assertNull(route.getFirstRoute().getSecondRoute());
        assertEquals(route.getFirstRoute().getValue()+route.getSecondRoute().getValue(),route.getValue());

        assertTrue(route.getSecondRoute().getListSegment().size() == 2);
        assertEquals(route.getListSegment().get(1), route.getSecondRoute().getFirstRoute().getListSegment().get(0));
        assertEquals(route.getListSegment().get(2), route.getSecondRoute().getSecondRoute().getListSegment().get(0));
    }


    @Test
    void createRouteThatContain5Route() {
        Segment segment1 = new Segment(new Position(0, 0, 0), new Position(10, 0, 0));
        Segment segment2 = new Segment(new Position(10, 0, 0), new Position(10, 10, 0));
        Segment segment3 = new Segment(new Position(10, 10, 0), new Position(30, 10, 0));
        Segment segment4 = new Segment(new Position(30, 0, 0), new Position(30, 50, 0));
        Segment segment5 = new Segment(new Position(30, 50, 0), new Position(30, 100, 0));


        List<Segment> listSegment = new ArrayList<>();
        listSegment.add(segment1);
        listSegment.add(segment2);
        listSegment.add(segment3);
        listSegment.add(segment4);
        listSegment.add(segment5);


        Route route = new Route(listSegment, cartologue);
        assertTrue(route.getFirstRoute().getListSegment().size() == 2);
        assertTrue(route.getSecondRoute().getListSegment().size() == 3);
        System.out.println(route);
    }

    @Test
    void sliceSegmentTestWithAStream(){
        Stream stream = new Stream(new Position(5,0,0), new Rectangle(2,2,0), 5);
        listStream.add(stream);
        cartologue=new Cartologue(listStream,listReef);
        route = new Route(segment, cartologue);
        List<Segment> res = route.sliceSegment(segment);
        assertEquals(3, res.size());
    }

    @Test
    void getValueTest(){
        assertTrue(Math.abs(route.getValue()-0.06) < 0.2);
    }

    @Test
    void getCartolgueTest(){
        assertTrue(route.getCartologue() != null);
    }

    @Test
    void toStringTest(){
        assertTrue(route.toString() != null);
        assertTrue(route.toString(0,0) != null);
    }

    @Test
    void indentTest(){
        assertTrue(route.indent(2).equals("\t\t"));
    }

    @Test
    void compareToTest(){
        Segment segment1 = new Segment(new Position(0, 0, 0), new Position(10, 0, 0));
        Segment segment2 = new Segment(new Position(0, 0, 0), new Position(20, 10, 0));

        Route r1 = new Route(segment1, cartologue);
        Route r2 = new Route(segment2, cartologue);
        assertEquals(-1, r1.compareTo(r2));
        assertEquals(1, r2.compareTo(r1));
        assertEquals(0, r1.compareTo(r1));
    }



}
