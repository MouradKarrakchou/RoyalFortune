package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;
//
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"type"})
public class Sail extends Entities{
    private boolean opened;

    public Sail(){}
    public Sail(int x, int y, boolean opened) {
        super("sail", x, y);
        this.opened = opened;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
