package fr.unice.polytech.si3.qgl.royal_fortune;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.si3.qgl.royal_fortune.action.MovingAction;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

public class SailorTest {
	Sailor sailor;
	Entities targetEntitiesInRange;
	Entities targetEntitiesOutOfRange;

	
	@BeforeEach
	public void init() {
		sailor = new Sailor(0, 0,0, "sailorTest");
		targetEntitiesInRange = new Entities("OAR", 2,2);
		targetEntitiesOutOfRange = new Entities("OAR", 3,5);

	}
	
	@Test
	public void moveToTargetExistingInRangeTest() {
		assertEquals(0, sailor.getX());
		assertEquals(0, sailor.getY());

		sailor.setTargetEntity(targetEntitiesInRange);
		MovingAction movingActionResult = sailor.moveToTarget();
		
		assertEquals(targetEntitiesInRange.getX(), movingActionResult.getXdistance());
		assertEquals(targetEntitiesInRange.getY(), movingActionResult.getyDistance());
	}
	
	@Test
	public void moveToTargetExistingOutOfRangeTest() {
		assertEquals(0, sailor.getX());
		assertEquals(0, sailor.getY());

		sailor.setTargetEntity(targetEntitiesOutOfRange);
		MovingAction movingActionResult = sailor.moveToTarget();
		
		assertEquals(targetEntitiesOutOfRange.getX(), movingActionResult.getXdistance());
		assertEquals(2, movingActionResult.getyDistance());
	}

}
