package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
@JsonIgnoreProperties(value = {"type"})
public class Sail extends Entities{
    private boolean openned;

    public Sail(){}
    public Sail(int x, int y, boolean openned) {
        super("sail", x, y);
        this.openned = openned;
    }

    public boolean isOpenned() {
        return openned;
    }
}
