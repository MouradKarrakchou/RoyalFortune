package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RudderAction extends Action {
    final Logger logger = Logger.getLogger(RudderAction.class.getName());

    public RudderAction(int sailorId) {
        super(sailorId, "TURN");
        this.type="TURN";
    }

    public RudderAction(){}

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rudderActionJSON = mapper.createObjectNode();
        rudderActionJSON.put("sailorId", sailorId);
        rudderActionJSON.put("type", "TURN");

        try {
            return mapper.writeValueAsString(rudderActionJSON);
        } catch (JsonProcessingException e) {
            logger.log(Level.INFO, "Exception");
        }
        return "";
    }
}
