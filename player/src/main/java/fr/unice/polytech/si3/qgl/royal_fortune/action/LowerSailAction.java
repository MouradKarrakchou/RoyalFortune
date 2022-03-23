package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LowerSailAction extends SailAction{
    public static String LOWER = "LOWER_SAIL";

    final Logger logger = Logger.getLogger(RudderAction.class.getName());
    public LowerSailAction(int sailorId) {
        super(sailorId, LOWER);
        this.type = LOWER;
    }

    public LowerSailAction(){}

}
