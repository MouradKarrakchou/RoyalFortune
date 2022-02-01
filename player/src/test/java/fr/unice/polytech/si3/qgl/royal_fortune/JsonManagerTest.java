package fr.unice.polytech.si3.qgl.royal_fortune;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonManagerTest {

    @BeforeEach
    void init(){

    }

    @Test
    void readJsonTest(){
        String json = "";
    }

    @Test
    void writeJsonActionTest() throws JsonProcessingException {
        JsonManager jsonManager = new JsonManager();
        String actionDone = "[{\"sailorId\":0,\"type\":\"OAR\"},{\"sailorId\":1,\"type\":\"OAR\"}]";

        assertEquals(actionDone,jsonManager.writeJsonAction());
    }

    
}
