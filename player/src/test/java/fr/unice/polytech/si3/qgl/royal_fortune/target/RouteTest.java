package fr.unice.polytech.si3.qgl.royal_fortune.target;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteTest {
    List<Stream> listStream;
    List<Reef> listReef;
    Wind wind;
    Segment segment;
    Cartologue cartologue;
    Route route;

    @BeforeEach
    void init(){
        listStream = new ArrayList<>();
        listReef = new ArrayList<>();
        wind = new Wind(0., 5.);
        segment = new Segment(new Position(0,0,0), new Position(10,0,0));
        cartologue = new Cartologue(listStream, listReef, wind);
        route = new Route(segment, cartologue);
    }
    @Test
    void distributeSegmentsTest(){
        assertTrue(true);
    }
}
