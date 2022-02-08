package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;

public class MovingAction extends Action {
    private int xdistance;
    private int ydistance;

    
    public MovingAction(int sailorId, int xdistance, int ydistance) {
        super(sailorId, "MOVING");
        this.xdistance = xdistance;
        this.ydistance = ydistance;
    }
    public MovingAction(){}

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode oarActionJSON = mapper.createObjectNode();
        oarActionJSON.put("sailorId", sailorId);
        oarActionJSON.put("type", "MOVING");
        oarActionJSON.put("xdistance", xdistance);
        oarActionJSON.put("ydistance", ydistance);

        try {
            return mapper.writeValueAsString(oarActionJSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

	public int getXdistance() {
		return xdistance;
	}

	public int getYdistance() {
		return ydistance;
	}
    
    
}
