package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.action.RudderAction;
import fr.unice.polytech.si3.qgl.royal_fortune.action.SailAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionTest {

    @Test
    void elementsTest() {
        Action action = new Action(1, "Type");
        assertEquals(1, action.getSailorId());
        assertEquals("Type", action.getType());
        assertEquals("Action [sailorId=1, type=Type]", action.toString());
    }

    @Test
    void rudderTest() {
        RudderAction rudderAction = new RudderAction(1, Math.PI);
        assertEquals(Math.PI, rudderAction.getRotation());
    }

    @Test
    void sailTest() {
        SailAction sailAction = new SailAction(1, "Type");
        assertEquals("Type", sailAction.getType());
    }
}
