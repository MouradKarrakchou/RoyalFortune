package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
@JsonSubTypes(value = {
		@JsonSubTypes.Type(value = MovingAction.class, name = "MOVING"),
		@JsonSubTypes.Type(value = OarAction.class, name = "OAR")
})
public class Action  {
	protected int sailorId;
	protected String type;

	public Action(Sailor sailor, String type) {
		this.sailorId = sailor.getId();
		this.type = type;
	}
	public Action(int sailorId, String type) {
		this.sailorId = sailorId;
		this.type = type;
	}
	public Action(){}

	public int getSailorId() {
		return sailorId;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Action [sailorId=" + sailorId + ", type=" + type + "]";
	}
}
