package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.exception.ToFarAssociationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.si3.qgl.royal_fortune.action.MovingAction;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

import static org.junit.jupiter.api.Assertions.*;

class SailorTest {
	Sailor sailor;
	Entities targetEntitiesInRange;
	Entities targetEntitiesOutOfRange;
	Entities targetEntitiesIn00;

	@BeforeEach
	void init() {
		sailor = new Sailor(0, 0,0, "sailorTest");
		targetEntitiesInRange = new Entities("OAR", 2,2);
		targetEntitiesOutOfRange = new Entities("OAR", 3,5);
		targetEntitiesIn00 = new Entities("OAR", 1,1);
	}

	@Test
	void getDistanceToEntityTest(){
		assertEquals(4, sailor.getDistanceToEntity(targetEntitiesInRange));
		assertEquals(8, sailor.getDistanceToEntity(targetEntitiesOutOfRange));
	}

	@Test
	void moveToTargetExistingInRangeTest() {
		assertEquals(0, sailor.getX());
		assertEquals(0, sailor.getY());

        Associations associations = new Associations();
        associations.addAssociation(sailor, targetEntitiesInRange);
		sailor.moveToTarget(associations);

		assertEquals(targetEntitiesInRange.getX(), sailor.getX());
		assertEquals(targetEntitiesInRange.getY(), sailor.getY());

        associations.dissociateAll();
        associations.addAssociation(sailor, targetEntitiesIn00);
		sailor.moveToTarget(associations);
		
		assertEquals(targetEntitiesIn00.getX(), sailor.getX());
		assertEquals(targetEntitiesIn00.getY(), sailor.getY());
	}

	@Test
	void moveToTargetExistingOutOfRangeTest() {
		assertEquals(0, sailor.getX());
		assertEquals(0, sailor.getY());

        Associations associations = new Associations();
        assertThrows(ToFarAssociationException.class, () -> {
			associations.checkForTargetOutOfRange(sailor, targetEntitiesOutOfRange);
		});
	}

	@Test
	void isOnTargetEntityTest(){
		Associations associations = new Associations();
		Sailor correctSailor = new Sailor(0, 1,1, "sailor0");

		Entities entity = new Entities("Entity", 1, 1);
		associations.addAssociation(correctSailor, entity);
		assertTrue(correctSailor.isOnTheTargetEntity(associations));

		Sailor incorrectSailor01 = new Sailor(1, 0,0, "sailor1");
		Sailor incorrectSailor02 = new Sailor(2, 0,1,  "sailor2");
		Sailor incorrectSailor03 = new Sailor(3, 0,2,  "sailor3");

		Sailor incorrectSailor04 = new Sailor(4, 1,0,  "sailor4");
		Sailor incorrectSailor05 = new Sailor(5, 1,2,  "sailor5");

		Sailor incorrectSailor06 = new Sailor(6, 2,0, "sailor6");
		Sailor incorrectSailor07 = new Sailor(7, 2,1,  "sailor7");
		Sailor incorrectSailor08 = new Sailor(8, 2,2,  "sailor8");
	}

}