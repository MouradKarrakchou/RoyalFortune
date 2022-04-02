package fr.unice.polytech.si3.qgl.royal_fortune.action;

public class LowerSailAction extends SailAction{
    public static final String LOWER = "LOWER_SAIL";

    public LowerSailAction(int sailorId) {
        super(sailorId, LOWER);
        this.type = LOWER;
    }

    public LowerSailAction(){}

}
