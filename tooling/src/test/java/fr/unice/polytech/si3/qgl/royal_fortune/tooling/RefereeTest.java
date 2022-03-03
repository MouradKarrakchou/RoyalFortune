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
    public void init(){
        mockShip = mock(Ship.class);
        cockpit = new Cockpit(mockShip, new ArrayList<Sailor>(), new Goal(), new Captain());
        referee = new Referee(cockpit);
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
    public void makeMooveShipByOaringFrontTest(){
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
}
