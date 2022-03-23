package fr.unice.polytech.si3.qgl.royal_fortune.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LiftSailAction extends SailAction{
    public static String LIFT = "LIFT_SAIL";

    final Logger logger = Logger.getLogger(RudderAction.class.getName());
    public LiftSailAction(int sailorId) {
        super(sailorId, LIFT);
        this.type = LIFT;
    }

    public LiftSailAction(){}

}
