package fr.unice.polytech.si3.qgl.royal_fortune;

public class Wind {

    double orientation;
    double strength;

    Wind(){}

    Wind(double orientation, double strength){
        this.orientation = orientation;
        this.strength = strength;
    }

    public double getOrientation() { return orientation; }

    public double getStrength() { return strength; }
}
