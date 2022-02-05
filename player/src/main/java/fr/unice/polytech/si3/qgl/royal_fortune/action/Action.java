package fr.unice.polytech.si3.qgl.royal_fortune.action;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;

public class Action  {
	protected final int sailorId;
	private final String type;
	
	public Action(Sailor sailor, String type) {
		this.sailorId = sailor.getId();
		this.type = type;
	}

	@Override
	public String toString() {
		return "Action [sailorId=" + sailorId + ", type=" + type + "]";
	}
}
