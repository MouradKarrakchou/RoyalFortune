package fr.unice.polytech.si3.qgl.royal_fortune;

import java.util.ArrayList;
import java.util.List;

public class Goal {
    String mode;
    List<Checkpoint> checkpoints;

    Goal(String mode, ArrayList<Checkpoint> checkpoints){
        this.mode = mode;
        this.checkpoints = checkpoints;
    }

    Goal(){}
    public void setMode(String mode){
        this.mode = mode;
    }

    public Checkpoint getCurrentCheckPoint() {
        return checkpoints.get(0);
    }

    public void nextCheckPoint(){checkpoints.remove(0);}

    public void setCheckpoints(List<Checkpoint> checkpoints){
        this.checkpoints = checkpoints;
    }

    public List<Checkpoint> getCheckPoints() { return checkpoints; }
}
