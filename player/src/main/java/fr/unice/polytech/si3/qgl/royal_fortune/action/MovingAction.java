package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;

public class MovingAction extends Action {
    private final int xDistance;
    private final int yDistance;

    public MovingAction(Sailor sailor, int xDistance, int yDistance) {
        super(sailor, "MOVING");
        this.xDistance = xDistance;
        this.yDistance = yDistance;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode oarActionJSON = mapper.createObjectNode();
        oarActionJSON.put("id", sailorId);
        oarActionJSON.put("type", "MOVING");
        oarActionJSON.put("xdistance", xDistance);
        oarActionJSON.put("ydistance", yDistance);

        try {
            return mapper.writeValueAsString(oarActionJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

	public int getxDistance() {
		return xDistance;
	}

	public int getyDistance() {
		return yDistance;
	}
    
    
}
