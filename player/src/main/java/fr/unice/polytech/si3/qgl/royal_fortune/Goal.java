package fr.unice.polytech.si3.qgl.royal_fortune;

import java.util.ArrayList;

public class Goal {
    String mode;
    ArrayList<Checkpoint> checkpoints;
    int currentCheckPoint=0;

    Goal(String mode, ArrayList<Checkpoint> checkpoints){
        this.mode = mode;
        this.checkpoints = checkpoints;
    }

    Goal(){}
    public void setMode(String mode){
        this.mode = mode;
    }

    public Checkpoint getCurrentCheckpoint() {
        return checkpoints.get(currentCheckPoint);
    }

    public void nextCheckPoint(){currentCheckPoint++;}

    public void setCheckpoints(ArrayList<Checkpoint> checkpoints){
        this.checkpoints = checkpoints;
    }

    public ArrayList<Checkpoint> getCheckPoints() { return checkpoints; }
}
