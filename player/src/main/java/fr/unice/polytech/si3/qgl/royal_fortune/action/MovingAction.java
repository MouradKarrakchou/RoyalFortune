package fr.unice.polytech.si3.qgl.royal_fortune.action;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;

public class MovingAction extends Action {
    private final int xDistance;
    private final int yDistance;

    public MovingAction(Sailor sailor, int xDistance, int yDistance) {
        super(sailor, "MOVING");
        this.xDistance = xDistance;
        this.yDistance = yDistance;
    }
}
