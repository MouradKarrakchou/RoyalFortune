package fr.unice.polytech.si3.qgl.royal_fortune;

import java.util.ArrayList;

public class Goal {
    String mode;
    ArrayList<Checkpoint> checkpoints;

    Goal(String mode, ArrayList<Checkpoint> checkpoints){
        this.mode = mode;
        this.checkpoints = checkpoints;
    }

    Goal(){}
    public void setMode(String mode){
        this.mode = mode;
    }

    public Checkpoint getCheckpointone() {
        return checkpoints.get(0);
    }

    public void setCheckpoints(ArrayList<Checkpoint> checkpoints){
        this.checkpoints = checkpoints;
    }

    public ArrayList<Checkpoint> getCheckPoints() { return checkpoints; }
}
