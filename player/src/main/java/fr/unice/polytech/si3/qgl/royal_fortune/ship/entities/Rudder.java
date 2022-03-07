package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;

@JsonIgnoreProperties(value = {"type"})
public class Rudder extends Entities{
    private Sailor sailor;

    public Rudder(){}

    public Rudder(int x, int y) {
        super("rudder", x, y);
    }

    public Sailor getSailor() {
        return sailor;
    }

    public void setSailor(Sailor sailor) {
        this.sailor = sailor;
    }
}
