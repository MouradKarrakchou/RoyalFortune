package fr.unice.polytech.si3.qgl.royal_fortune.dao_test;

import fr.unice.polytech.si3.qgl.royal_fortune.dao.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.NextRoundDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NextRoundDaoTest {

    private NextRoundDAO nextDao;
    @BeforeEach
    void init(){
        String json = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1000,\"y\":100,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":100.0}},{\"position\":{\"x\":-200.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":80.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"royal_fortune\",\"deck\":{\"width\":2,\"length\":4},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Luffy Pouce\"}],\"shipCount\":1}";
        nextDao = JsonManager.readNextRoundDAOJson(json);
    }

    @Test
    void getSeaEntitiesTest(){
        List<SeaEntities> l = new ArrayList<>();
        l.add(new SeaEntities());
        nextDao = new NextRoundDAO(null, l, null);
        assertEquals(1, nextDao.getVisibleEntities().size());
    }

    @Test
    void toStringTest(){
        assertTrue(nextDao.toString() != "");
    }


    @Test
    void toStringNullCatchTest(){
        boolean catchError = false;
        nextDao = null;
        try{
            nextDao.toString();
        }catch(NullPointerException e){
            catchError = true;
        }
        assertTrue(catchError);
    }
}

