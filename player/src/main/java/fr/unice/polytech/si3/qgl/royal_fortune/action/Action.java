package fr.unice.polytech.si3.qgl.royal_fortune.action;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;

public class Action  {
	protected final int sailorId;
	private final String type;
	Integer xdistance;
	Integer ydistance;
	
	public Action(Sailor sailor, String type) {
		this.sailorId = sailor.getId();
		this.type = type;
	}

	public Action(Sailor sailor, String type,Integer xdistance, Integer ydistance) {
		this.sailorId = sailor.getId();
		this.type = type;
		this.xdistance=xdistance;
		this.ydistance=ydistance;
	}

	public int getSailorId() {
		return sailorId;
	}

	public String getType() {
		return type;
	}

	public Integer getXdistance() {
		return xdistance;
	}

	public Integer getYdistance() {
		return ydistance;
	}

	@Override
	public String toString() {
		return "Action [sailorId=" + sailorId + ", type=" + type + "]";
	}
}
