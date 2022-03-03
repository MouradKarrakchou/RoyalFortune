package fr.unice.polytech.si3.qgl.royal_fortune.tooling;

import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.Goal;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.OarAction;
import fr.unice.polytech.si3.qgl.royal_fortune.action.RudderAction;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation.Referee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RefereeTest{
    Cockpit cockpit;
    Referee referee;
    Ship mockShip;

    @BeforeEach
    void init(){
        mockShip = mock(Ship.class);
        cockpit = new Cockpit(mockShip, new ArrayList<Sailor>(), new Goal(), new Captain());
        referee = new Referee(cockpit);
    }

    @Test
    void angleTest1(){
        int nbrLeft = 0 ;
        int nbrRight = 0 ;
        double rudderAngle = 0.0;
        when(mockShip.getNbrOar()).thenReturn(nbrLeft+nbrRight);


        referee.setLeftPush(nbrLeft);
        referee.setLeftPush(nbrRight);

        referee.computeAngleRotate();
        double expected = ((nbrRight-nbrLeft)* Math.PI/(nbrLeft+nbrRight)) + rudderAngle;
        assertEquals(expected, referee.computeAngleRotate());
    }

    @Test
    void angleTest2(){
    //return (orientationCalculus() * Math.PI / cockpit.getShip().getNbrOar()) +rudderRotation;

        int nbrLeft = 5 ;
        int nbrRight = 7 ;
        double rudderAngle = 5.0;
        when(mockShip.getNbrOar()).thenReturn(nbrLeft+nbrRight);


        referee.setLeftPush(nbrLeft);
        referee.setRightPush(nbrRight);
        referee.setRudderRotation(rudderAngle);
        referee.computeAngleRotate();

        double expected = ((nbrRight-nbrLeft)* Math.PI/(nbrLeft+nbrRight)) + rudderAngle;
        assertEquals(expected, referee.computeAngleRotate());
    }

    @Test
    void doActionTestRudder(){
        assertEquals(0, referee.getRudderRotation());
        referee.doAction(new RudderAction(0, 1.2));
        assertEquals(1.2, referee.getRudderRotation());
    }

    @Test
    void doActionTestOar(){
        List<Sailor> sailors = referee.getCockpit().getSailors();
        sailors.add(new Sailor(0,0,-1,"testSailor"));

        assertEquals(0, referee.getLeftPush());
        referee.doAction(new OarAction(0));
        assertEquals(1, referee.getLeftPush());

        sailors.clear();
        sailors.add(new Sailor(0,0,1,"testSailor"));

        assertEquals(0, referee.getRightPush());referee.doAction(new OarAction(0));
        assertEquals(1, referee.getRightPush());
    }

    @Test
    void makeMooveShipByOaringFrontTest(){
        referee.setLeftPush(1);
        referee.setRightPush(1);

        when(mockShip.getNbrOar()).thenReturn(2);
        when(mockShip.getPosition()).thenReturn(new Position(0,0,0));

        Position testedPosition = referee.getCockpit().getShip().getPosition();
        referee.makeMooveShipByOaring(referee.getCockpit().getShip());

        assertEquals(165, (int) testedPosition.getX());
        assertEquals(0, (int) testedPosition.getY());
        assertEquals(0, (int) testedPosition.getOrientation());
    }

    @Test
    void makeMooveShipByOaringOneLeftTest(){
        referee.setLeftPush(1);

        when(mockShip.getNbrOar()).thenReturn(1);
        when(mockShip.getPosition()).thenReturn(new Position(0,0,0));

        Position testedPosition = referee.getCockpit().getShip().getPosition();
        referee.makeMooveShipByOaring(referee.getCockpit().getShip());

        assertEquals(0, (int) testedPosition.getX());
        assertEquals(-105, (int) testedPosition.getY());
        assertEquals(-3, (int) testedPosition.getOrientation());
    }

    @Test
    void makeMooveShipByOaringOneRightTest(){
        referee.setRightPush(1);

        when(mockShip.getNbrOar()).thenReturn(1);
        when(mockShip.getPosition()).thenReturn(new Position(0,0,0));

        Position testedPosition = referee.getCockpit().getShip().getPosition();
        referee.makeMooveShipByOaring(referee.getCockpit().getShip());

        assertEquals(0, (int) testedPosition.getX());
        assertEquals(105, (int) testedPosition.getY());
        assertEquals(3, (int) testedPosition.getOrientation());
    }
}
