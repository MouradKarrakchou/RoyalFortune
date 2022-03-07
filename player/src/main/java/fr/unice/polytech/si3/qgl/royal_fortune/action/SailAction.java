package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SailAction extends Action{
    final Logger logger = Logger.getLogger(RudderAction.class.getName());
    public String action;
    public SailAction(int sailorId, boolean takeWind) {
        super(sailorId, takeWind?"LOWER_SAIL":"LIFT_SAIL");
        this.type = takeWind?"LOWER_SAIL":"LIFT_SAIL";
        action = takeWind?"LOWER_SAIL":"LIFT_SAIL";
    }

    public SailAction(){}

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rudderActionJSON = mapper.createObjectNode();
        rudderActionJSON.put("sailorId", sailorId);
        rudderActionJSON.put("type", action);

        try {
            return mapper.writeValueAsString(rudderActionJSON);
        } catch (JsonProcessingException e) {
            logger.log(Level.INFO, "Exception");
        }
        return "";
    }
}
