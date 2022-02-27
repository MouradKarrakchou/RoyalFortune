package fr.unice.polytech.si3.qgl.royal_fortune;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.si3.qgl.royal_fortune.action.MovingAction;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

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

		sailor.setTargetEntity(targetEntitiesInRange);
		sailor.moveToTarget();

		assertEquals(targetEntitiesInRange.getX(), sailor.getX());
		assertEquals(targetEntitiesInRange.getY(), sailor.getY());
		
		sailor.setTargetEntity(targetEntitiesIn00);
		sailor.moveToTarget();
		
		assertEquals(targetEntitiesIn00.getX(), sailor.getX());
		assertEquals(targetEntitiesIn00.getY(), sailor.getY());
		
	}

	@Test
	void moveToTargetExistingOutOfRangeTest() {
		assertEquals(0, sailor.getX());
		assertEquals(0, sailor.getY());

		sailor.setTargetEntity(targetEntitiesOutOfRange);
		MovingAction movingActionResult = sailor.moveToTarget();

		assertEquals(targetEntitiesOutOfRange.getX(), movingActionResult.getXdistance());
		assertEquals(2, movingActionResult.getYdistance());
	}

}