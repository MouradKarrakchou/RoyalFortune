package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.action.MovingAction;
import fr.unice.polytech.si3.qgl.royal_fortune.action.OarAction;
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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
			movingAction = new MovingAction(this.getId(), targetEntity.getX() - x, targetEntity.getY() - y);
		}

		// If there is a target entity and the sailor can not go to in one turn (> 5 cases).
		// CODE HERE ...
		else{
			int i = 0 ;
			int posX = 0;
			int posY = 0;
			int vect = (targetEntity.getX()-this.x)<0 ? -1 : 1;
			
			//tant que this.x+Deplacement < target.x || i < 5
			while(Math.abs(this.x+posX) < Math.abs(targetEntity.getX()) && posX < 5) {
				System.out.println(Math.abs(this.x+posX) < Math.abs(targetEntity.getX()));
				posX+=vect;
			}
			if(posX<5)
				while(Math.abs(this.y+posX) < Math.abs(targetEntity.getY()) && posY+posX < 5) {
					posY+=vect;
				}
			
			
			movingAction = new MovingAction(this.getId(), posX - x, posY - y);
		}

		this.x += movingAction.getXdistance();
		this.y += movingAction.getYdistance();
		return movingAction;
	}

	public OarAction oar(){
		// If sailor don't have a target entity, sailor can't go to this target.
		if (targetEntity == null)
			return null;

		return new OarAction(this.getId());
	}
}
