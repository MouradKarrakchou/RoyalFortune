package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class MovingAction extends Action {
    private int xdistance;
    private int ydistance;
    private static final String MOVING = "MOVING";
    final Logger logger = Logger.getLogger(MovingAction.class.getName());

    
    public MovingAction(int sailorId, int xdistance, int ydistance) {
        super(sailorId, MOVING);
        this.xdistance = xdistance;
        this.ydistance = ydistance;
        this.type = MOVING;
    }
    public MovingAction(){}

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode oarActionJSON = mapper.createObjectNode();
        oarActionJSON.put("sailorId", sailorId);
        oarActionJSON.put("type", MOVING);
        oarActionJSON.put("xdistance", xdistance);
        oarActionJSON.put("ydistance", ydistance);

        try {
            return mapper.writeValueAsString(oarActionJSON);
        } catch (JsonProcessingException e) {
            logger.log(Level.INFO, "Exception");
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
