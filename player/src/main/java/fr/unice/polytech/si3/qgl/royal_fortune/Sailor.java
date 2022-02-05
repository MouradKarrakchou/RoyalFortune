package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.action.MovingAction;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Sailor{
	private int id;
	private int x;
	private int y;
	private String name;
	private Entities targetEntity;
	
	public Sailor() {}

	public Sailor(int id, int x, int y, String name) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public void setTargetEntity(Entities targetEntity) {
		this.targetEntity = targetEntity;
	}

	public Entities getTargetEntity(){
		return targetEntity;
	}
	
	public String getName() {
		return name;
	}

	public Integer getId() {
		return (id);
	}

	public boolean isOnTheTargetEntity(){
		return (x == targetEntity.getX() && y == targetEntity.getY());
	}

	/**
	 * Called by the Captain, call a sailor to move to its targetEntity.
	 * @return A MovingAction that will be added to the action list.
	 */
	public MovingAction moveToTarget(){
		// If sailor don't have a target entity, sailor can't go to this target.
		if (targetEntity == null)
			return null;

		// If there is a target entity and the sailor can go to in one turn (>= 5 cases).
		MovingAction movingAction = null;
		if(Math.abs(targetEntity.getX() - x) + Math.abs(targetEntity.getY() - y) <= 5){
			movingAction = new MovingAction(this, targetEntity.getX() - x, targetEntity.getY() - y);
		}

		// If there is a target entity and the sailor can not go to in one turn (> 5 cases).
		// CODE HERE ...
		else{
			int i = 0 ;
			int posX = 0;
			int posY = 0;
			int vect = (targetEntity.getX()-this.x)<0 ? -1 : 1;
			for(; i<5 || this.x+posX == targetEntity.getX(); i++)
				posX+=i*vect;
			if(i != 4)
				for(; i<5 || this.x+posY == targetEntity.getY(); i++)
					posY+=i*vect;
			movingAction = new MovingAction(this, posX - x, posY - y);
		}

		return movingAction;
	}
}
