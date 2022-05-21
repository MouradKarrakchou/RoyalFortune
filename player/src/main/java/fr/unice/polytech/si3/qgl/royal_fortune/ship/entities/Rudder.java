package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
@JsonIgnoreProperties(value = {"type"})
public class Rudder extends Entities{
    public Rudder(){}

    public Rudder(int x, int y) {
        super("rudder", x, y);
    }

}
