package fr.unice.polytech.si3.qgl.royal_fortune.action;

import java.util.logging.Logger;

public class LiftSailAction extends SailAction{
    public static final String liftSail = "LIFT_SAIL";

    final Logger logger = Logger.getLogger(LiftSailAction.class.getName());
    public LiftSailAction(int sailorId) {
        super(sailorId, liftSail);
        this.type = liftSail;
    }

    public LiftSailAction(){}

}
