package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"type"})

public class Watch extends Entities{
    public Watch(){}

    public Watch(int x, int y) {
            super("watch", x, y);
    }
}
