package fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates;

import fr.unice.polytech.si3.qgl.royal_fortune.action.*;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;

import java.util.Comparator;
import java.util.List;

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

		// If there is a target entity and the sailor can go to in one turn (>= 5 cases).
		MovingAction movingAction;
		if(getDistanceToEntity(targetEntity) <= 5){
			movingAction = new MovingAction(this.getId(), targetEntity.getX() - x, targetEntity.getY() - y);
		}

		// If there is a target entity and the sailor can not go to in one turn (> 5 cases).
		else{
			movingAction = targetEntityFarAway(associations);
		}

		this.x += movingAction.getXdistance();
		this.y += movingAction.getYdistance();

		return movingAction;
	}

	MovingAction targetEntityFarAway(Associations associations) {
		Entities targetEntity = associations.getAssociatedEntity(this);
		int posX = 0;
		int posY = 0;
		int vectX = (targetEntity.getX()-this.x)<0 ? -1 : 1;
		int vectY = (targetEntity.getY()-this.y)<0 ? -1 : 1;

		//tant que this.x+Deplacement < target.x || i < 5
		while(Math.abs(this.x+posX) < Math.abs(targetEntity.getX()) && posX < 5) {
			System.out.println(Math.abs(this.x+posX) < Math.abs(targetEntity.getX()));
			posX+=vectX;
		}
		if(posX<5)
			while(Math.abs(this.y+posX) < Math.abs(targetEntity.getY()) && posY+posX < 5) {
				posY+=vectY;
			}

		return new MovingAction(this.getId(), posX - x, posY - y);
	}

	public OarAction oar(){
		return new OarAction(this.getId());
	}

	public RudderAction turnWithRudder(double rotationRudder) {
		return new RudderAction(this.getId(), rotationRudder);
	}
	public SailAction useSail(boolean openned) {
		if(openned == true)
			return new LiftSailAction(this.getId());
		else
			return new LowerSailAction(this.getId());
	}

	public Oar getNearestOar(List<Oar> oars,Associations associations){
		return oars.stream()
				.filter(associations::isFree)
				.min(Comparator.comparingInt(this::getDistanceToEntity))
				.get();
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
