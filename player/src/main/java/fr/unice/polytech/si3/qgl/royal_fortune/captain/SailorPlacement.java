package fr.unice.polytech.si3.qgl.royal_fortune.captain;

public class SailorPlacement {
    private int oarWeight;
    private boolean rudder;
    private boolean sail;
    private int nbLeftSailors;
    private int nbRightSailors;

    public SailorPlacement(int oarWeight, boolean rudder, boolean sail) {
        this.oarWeight = oarWeight;
        this.rudder = rudder;
        this.sail = sail;
    }

    public SailorPlacement( int nbLeftSailors, int nbRightSailors, boolean rudder, boolean sail){
        this.rudder = rudder;
        this.sail = sail;
        this.nbRightSailors = nbRightSailors;
        this.nbLeftSailors = nbLeftSailors;
    }

    public boolean hasRudder() {
        return rudder;
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
}
