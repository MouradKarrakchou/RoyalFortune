package fr.unice.polytech.si3.qgl.royal_fortune.action;

import java.util.logging.Logger;

public class LowerSailAction extends SailAction{
    public static final String lower_sail = "LOWER_SAIL";

    final Logger logger = Logger.getLogger(LowerSailAction.class.getName());
    public LowerSailAction(int sailorId) {
        super(sailorId, lower_sail);
        this.type = lower_sail;
    }

    public LowerSailAction(){}

}
