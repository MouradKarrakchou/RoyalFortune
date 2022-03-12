package fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates;

public class SailorPlacement {
    private int oarWeight;
    private boolean rudder;
    private boolean sail;
    private int nbLeftSailors;
    private int nbRightSailors;

    public SailorPlacement(){
        this.oarWeight = 0;
        this.rudder = false;
        this.sail = false;
        this.nbLeftSailors = 0;
        this.nbRightSailors = 0;
    }

    public SailorPlacement(int oarWeight, boolean rudder, boolean sail) {
        this.oarWeight = oarWeight;
        this.rudder = rudder;
        this.sail = sail;
    }

    public SailorPlacement(int nbLeftSailors, int nbRightSailors, boolean rudder, boolean sail){
        this.rudder = rudder;
        this.sail = sail;
        this.nbRightSailors = nbRightSailors;
        this.nbLeftSailors = nbLeftSailors;
    }

    public SailorPlacement(int oarWeight, int nbLeftSailors, int nbRightSailors, boolean rudder, boolean sail){
        this.oarWeight = oarWeight;
        this.rudder = rudder;
        this.sail = sail;
        this.nbRightSailors = nbRightSailors;
        this.nbLeftSailors = nbLeftSailors;
    }

    public boolean hasRudder() {
        return rudder;
    }

    public void setOarWeight(int oarWeight) {
        this.oarWeight = oarWeight;
    }

    public void setSail(boolean sail) {
        this.sail = sail;
    }

    public void setNbLeftSailors(int nbLeftSailors) {
        this.nbLeftSailors = nbLeftSailors;
    }

    public void setNbRightSailors(int nbRightSailors) {
        this.nbRightSailors = nbRightSailors;
    }

    public boolean hasSail() {
        return sail;
    }

    public int getNbLeftSailors() {
        return nbLeftSailors;
    }

    public int getNbRightSailors() {
        return nbRightSailors;
    }

    public int getOarWeight() {
        return oarWeight;
    }

    public void setRudder(boolean rudder) {
        this.rudder = rudder;
    }

    public void incrementNbLeftSailor(int incrementation){
        this.nbLeftSailors += incrementation;
    }

    public void incrementNbRightSailor(int incrementation){
        this.nbRightSailors += incrementation;
    }

    public void incrementOarWeight(int incrementation){
        this.oarWeight += incrementation;
    }

    @Override
    public String toString() {
        return "SailorPlacement{" +
                "rudder=" + rudder +
                ", sail=" + sail +
                ", nbLeftSailors=" + nbLeftSailors +
                ", nbRightSailors=" + nbRightSailors +
                '}';
    }
}
