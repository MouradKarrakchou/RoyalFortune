package fr.unice.polytech.si3.qgl.royal_fortune;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Wind {
    final Logger logger = Logger.getLogger(Ship.class.getName());
    double orientation;
    double strength;

    public Wind(){}

    public Wind(double orientation, double strength){
        this.orientation = orientation;
        this.strength = strength;
    }

    public double getOrientation() { return orientation; }

    public double getStrength() { return strength; }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.log(Level.INFO, "Exception");
        }
        return "";
    }
}
