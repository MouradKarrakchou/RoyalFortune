package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.exception.EmptyDaoException;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CockpitTest {

    Cockpit cockpit;

    @BeforeEach
    void setUp() {
        this.cockpit = new Cockpit();
    }

    @Test
    void createInitGameDAOBadJsonTest(){
        String badJson = "badJson";
        assertThrows(EmptyDaoException.class, () -> {
            cockpit.createInitGameDAO(badJson);
        });

    }

    @Test
    void createInitGameDAOGoodJsonTest(){
        boolean result = true;
        String goodJson = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1000,\"y\":100,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":100}},{\"position\":{\"x\":-200,\"y\":1000,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":80}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0,\"y\":0,\"orientation\":0},\"name\":\"royal_fortune\",\"deck\":{\"width\":4,\"length\":3},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"},{\"x\":3,\"y\":2,\"type\":\"sail\"},{\"x\":3,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2,\"height\":4,\"orientation\":0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":3,\"id\":4,\"name\":\"Luffy olala\"},{\"x\":1,\"y\":2,\"id\":5,\"name\":\"Luffy siuuuu\"}],\"shipCount\":1}";
        try {
            cockpit.createInitGameDAO(goodJson);
        } catch (Exception ex) {
            fail("DAO is null but it should not be !!!");
        }
        assertTrue(result);
    }

    @Test
    void createNextRoundDAOBadJsonTest(){
        String badJson = "badJson";
        assertThrows(EmptyDaoException.class, () -> {
            cockpit.createNextRoundDAO(badJson);
        });

    }

    @Test
    void createNextRoundDAOGoodJsonTest(){
        boolean result = true;
        String goodJson = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1000,\"y\":100,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":100}},{\"position\":{\"x\":-200,\"y\":1000,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":80}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0,\"y\":0,\"orientation\":0},\"name\":\"royal_fortune\",\"deck\":{\"width\":4,\"length\":3},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"},{\"x\":3,\"y\":2,\"type\":\"sail\"},{\"x\":3,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2,\"height\":4,\"orientation\":0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":3,\"id\":4,\"name\":\"Luffy olala\"},{\"x\":1,\"y\":2,\"id\":5,\"name\":\"Luffy siuuuu\"}],\"shipCount\":1}";
        try {
            cockpit.createNextRoundDAO(goodJson);
        } catch (Exception ex) {
            fail("DAO is null but it should not be !!!");
        }
        assertTrue(result);
    }

    @Test
    void initGameIT(){
        String json= "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1000,\"y\":100,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":100.0}},{\"position\":{\"x\":-200.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":80.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"royal_fortune\",\"deck\":{\"width\":2,\"length\":4},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Luffy Pouce\"}],\"shipCount\":1}";
        try {
            cockpit.initGame(json);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception was thrown!");
        }
        assertTrue(true);
    }

    @Test
    void nextRoundIT(){
        cockpit.initGame("{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1000,\"y\":100,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":100.0}},{\"position\":{\"x\":-200.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":80.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"royal_fortune\",\"deck\":{\"width\":2,\"length\":4},\"entities\":[{\"x\":3,\"y\":1,\"type\":\"rudder\"},{\"x\":3,\"y\":2,\"type\":\"sail\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Luffy Pouce\"}],\"shipCount\":1}");
        String json = "{\"ship\":{\"type\":\"ship\",\"position\":{\"x\":131.39463560710956,\"y\":-34.83865636111389,\"orientation\":-0.5235987755982993},\"name\":\"royal_fortune\",\"deck\":{\"width\":2,\"length\":4},\"entities\":[{\"x\":3,\"y\":1,\"type\":\"rudder\"},{\"x\":3,\"y\":2,\"type\":\"sail\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"oar\"}],\"life\":400,\"shape\":{\"type\":\"rectangle\",\"width\":2.0,\"height\":4.0,\"orientation\":0.0}},\"visibleEntities\":[],\"wind\":{\"orientation\":0.0,\"strength\":0.0}}";
        String res ="";
        try {
            res = cockpit.nextRound(json);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception was thrown!");
        }
        assertTrue(true);
        assertTrue(res!= null);
    }

    @Test
    void elementTest() {
        Goal goal = new Goal("Mode", new ArrayList<>());
        List<Sailor> sailorsList = new ArrayList<>();
        sailorsList.add(new Sailor(1, 2, 3, "Queen"));
        Ship ship = new Ship("Type", 100, new Position(0, 0, 0), "name",null, null, null);
        Captain captain = new Captain(ship, sailorsList, goal, null, new Wind(0, 12));
        Cockpit cockpit = new Cockpit(ship, sailorsList, goal, captain);

        assertEquals("Type", cockpit.getShip().getType());
        assertEquals("Queen", cockpit.getSailors().get(0).getName());
        assertEquals(new ArrayList<>(), cockpit.getGoal().getCheckPoints());
        assertEquals(12, cockpit.getCaptain().getWind().getStrength());
        assertEquals(new ArrayList<>(), cockpit.getLogs());
    }

    @Test
    void getTest(){
        Cockpit cockpit = new Cockpit(new Ship(), new ArrayList<>(), new Goal(), new Captain());
        assertTrue(cockpit.getShip()!= null);
        assertTrue(cockpit.getSailors()!= null);
        assertTrue(cockpit.getCaptain()!= null);
        assertTrue(cockpit.getGoal()!= null);
    }
}
