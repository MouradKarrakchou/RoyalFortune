package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import java.util.ArrayList;

public class Captain {
    private final Ship ship;
    private final ArrayList<Sailor> sailors;

    public Captain(Ship ship, ArrayList<Sailor> sailors){
        this.ship = ship;
        this.sailors = sailors;
    }
}
