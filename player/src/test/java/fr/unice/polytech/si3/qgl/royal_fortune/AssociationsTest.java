package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.exception.ToFarAssociationException;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssociationsTest {

    @Test
    void toStringTest(){
        Associations asso = new Associations();
        assertEquals(asso.toString(), "{}");
    }

    @Test
    void sizeTest(){
        Associations asso = new Associations();
        asso.addAssociation(new Sailor(), new Entities());
        assertEquals(asso.size(), 1);
    }

    @Test
    void checkForTargetOutOfRangeTest() {
        Associations asso = new Associations();
        boolean catchError = true;

        //int id, int x, int y, String name
        Sailor sailor = new Sailor(0, 0, 0, "sailor");
        //String type, int x, int y
        Entities entity = new Entities("entityTest", 0, 0);
        try {
            asso.checkForTargetOutOfRange(sailor, entity);
        } catch (ToFarAssociationException e) {
            catchError = false;
        }
        assertTrue(catchError);
    }

    @Test
    void addAssociationTest(){
        Associations asso = new Associations();
        asso.addAssociation(new Sailor(), new Entities());
        assertEquals(asso.size(), 1);
    }
}
