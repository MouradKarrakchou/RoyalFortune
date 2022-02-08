package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;

public class MovingAction extends Action {
    private int xdistance;
    private int yDistance;

    public MovingAction(Sailor sailor, int xDistance, int yDistance) {
        super(sailor, "MOVING");
        this.xdistance = xDistance;
        this.yDistance = yDistance;
    }
    public MovingAction(int sailorId,String type,int xdistance, int ydistance) {
        super.sailorId=sailorId;
        super.type=type;
        this.xdistance = xdistance;
        this.yDistance = ydistance;
    }
    public MovingAction(){}

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode oarActionJSON = mapper.createObjectNode();
        oarActionJSON.put("sailorId", sailorId);
        oarActionJSON.put("type", "MOVING");
        oarActionJSON.put("xdistance", xdistance);
        oarActionJSON.put("ydistance", yDistance);

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

	public int getyDistance() {
		return yDistance;
	}
    
    
}
