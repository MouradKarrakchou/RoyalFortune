package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LowerSailAction extends SailAction{
    public static final String lower_sail = "LOWER_SAIL";

    final Logger logger = Logger.getLogger(LowerSailAction.class.getName());
    public LowerSailAction(int sailorId) {
        super(sailorId, lower_sail);
        this.type = lower_sail;
    }

    public LowerSailAction(){}

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rudderActionJSON = mapper.createObjectNode();
        rudderActionJSON.put("sailorId", sailorId);
        rudderActionJSON.put("type", type);

        try {
            return mapper.writeValueAsString(rudderActionJSON);
        } catch (JsonProcessingException e) {
            logger.log(Level.INFO, "Exception");
        }
        return "";
    }

}
