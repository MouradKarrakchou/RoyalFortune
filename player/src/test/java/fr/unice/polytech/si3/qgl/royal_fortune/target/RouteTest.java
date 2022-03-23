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

public class RouteTest {
    @BeforeEach
    void init(){
        List<Stream> listStream = new ArrayList<>();
        List<Reef> listReef = new ArrayList<>();
        Wind wind = new Wind(0., 5.);

        Segment segment = new Segment(new Position(0,0,0), new Position(10,0,0));
        Cartologue cartologue = new Cartologue(listStream, listReef, wind);
        Route r = new Route(segment, cartologue);
    }
    @Test
    void getListSegmentTest(){}
}
