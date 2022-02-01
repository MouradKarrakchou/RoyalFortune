package fr.unice.polytech.si3.qgl.royal_fortune;

import com.fasterxml.jackson.core.JsonProcessingException;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonManagerTest {

    @BeforeEach
    void init(){

    }

    @Test
    void readJsonTest() throws JsonProcessingException {
        String json ="""
            {
                "type": "ship",
                "life": 100,
                "position": {
                "x": 10,
                "y": 20,
                "orientation": 0
                },
                "name": "Boat test"
            }""";
        Ship ship = JsonManager.readJson(json);

        assertEquals("ship", ship.getType());
        assertEquals(100, ship.getLife());
        assertEquals(new Position(10, 20, 0), ship.getPosition());
        assertEquals("Boat test", ship.getName());
    }

    @Test
    void writeJsonActionTest() throws JsonProcessingException {
        JsonManager jsonManager = new JsonManager();
        String actionDone = "[{\"sailorId\":0,\"type\":\"OAR\"},{\"sailorId\":1,\"type\":\"OAR\"}]";

        //assertEquals(actionDone,jsonManager.writeJsonAction());
    }


}
