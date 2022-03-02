package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OarAction extends Action {
    final Logger logger = Logger.getLogger(OarAction.class.getName());
	
    public OarAction(int sailorId) {
        super(sailorId, "OAR");
        this.type="OAR";
    }

    public OarAction(){}
    
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode oarActionJSON = mapper.createObjectNode();
        oarActionJSON.put("sailorId", sailorId);
        oarActionJSON.put("type", "OAR");

        try {
            return mapper.writeValueAsString(oarActionJSON);
        } catch (JsonProcessingException e) {
            logger.log(Level.INFO, "Exception");
        }
        return "";
    }
}
