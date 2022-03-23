package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SailAction extends Action{
    public static String LIFT = "LIFT_SAIL";

    final Logger LOGGER = Logger.getLogger(SailAction.class.getName());
    public SailAction(int sailorId, String type) {
        super(sailorId, type);
        this.type = type;
    }

    public SailAction(){}

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rudderActionJSON = mapper.createObjectNode();
        rudderActionJSON.put("sailorId", sailorId);
        rudderActionJSON.put("type", type);

        try {
            return mapper.writeValueAsString(rudderActionJSON);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.INFO, "Exception");
        }
        return "";
    }

    public String getType() {
        return type;
    }
}
