package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
@JsonSubTypes(value = {
		@JsonSubTypes.Type(value = MovingAction.class, name = "MOVING"),
		@JsonSubTypes.Type(value = OarAction.class, name = "OAR"),
		@JsonSubTypes.Type(value = RudderAction.class, name = "TURN"),
		@JsonSubTypes.Type(value = LowerSailAction.class, name = "LOWER_SAIL"),
		@JsonSubTypes.Type(value = LiftSailAction.class, name = "LIFT_SAIL"),
		@JsonSubTypes.Type(value = WatchAction.class, name = "USE_WATCH")
})

public class Action  {
	protected int sailorId;
	protected String type;

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
