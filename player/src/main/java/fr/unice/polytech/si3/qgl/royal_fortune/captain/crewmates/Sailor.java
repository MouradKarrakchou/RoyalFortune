package fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates;

import fr.unice.polytech.si3.qgl.royal_fortune.action.*;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Sailor{
	private int id;
	private int x;
	private int y;
	private String name;

	public Sailor() {}

	public Sailor(int id, int x, int y, String name) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Integer getId() {
		return (id);
	}

	public boolean isOnTheTargetEntity(Associations associations){
		Entities targetEntity = associations.getAssociatedEntity(this);
		return (x == targetEntity.getX() && y == targetEntity.getY());
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


	/**
	 * Calculate the distance between a sailor and a given entity.
	 * @param entity The entity to calculate the distance with.
	 * @return The distance between the sailor and the given entity.
	 */
	public int getDistanceToEntity(Entities entity){
		return Math.abs(entity.getX() - x) + Math.abs(entity.getY() - y);
	}

	/**
	 * Called by the Captain, call a sailor to move to its targetEntity.
	 * @return A MovingAction that will be added to the action list.
	 */
	public MovingAction moveToTarget(Associations associations){
		Entities targetEntity = associations.getAssociatedEntity(this);

		// If sailor don't have a target entity, sailor can't go to this target.
		if (targetEntity == null)
			return null;

		MovingAction movingAction;

		movingAction = new MovingAction(this.getId(), targetEntity.getX() - x, targetEntity.getY() - y);

		this.x += movingAction.getXdistance();
		this.y += movingAction.getYdistance();

		return movingAction;
	}

	public OarAction oar(){
		return new OarAction(this.getId());
	}

	public WatchAction watch(){
		return new WatchAction(this.getId());
	}

	public RudderAction turnWithRudder(double rotationRudder) {
		return new RudderAction(this.getId(), rotationRudder);
	}
	public SailAction useSail(boolean opened) {
		if(opened)
			return new LiftSailAction(this.getId());
		else
			return new LowerSailAction(this.getId());
	}

	public Oar getNearestOar(List<Oar> oars,Associations associations){
		Optional<Oar> nearestOar = oars.stream()
				.filter(associations::isFree)
				.min(Comparator.comparingInt(this::getDistanceToEntity));

		return nearestOar.orElse(null);
	}

	@Override
	public String toString() {
		return name + " " + id;
	}
	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}


}
