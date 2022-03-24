package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;
import org.apache.maven.surefire.shared.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntitiesTest {
    @Test
    void rudderAndSailTest(){
        Sail sail=new Sail();
        Rudder rudder= new Rudder();
        assertTrue(sail.isSail());
        assertTrue(rudder.isRudder());
        assertFalse(sail.isRudder());
        assertFalse(rudder.isSail());
    }

    @Test
    void typeTest() {
        Entities entities = new Entities("Type", 1, 2);
        assertEquals("Type", entities.getType());
        assertEquals("{\"type\":\"Entities\",\"type\":\"Type\",\"x\":1,\"y\":2,\"rudder\":false,\"sail\":false,\"oar\":false}", entities.toString());
    }
}
