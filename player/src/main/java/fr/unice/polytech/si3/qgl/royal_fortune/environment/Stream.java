package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

public class Stream extends SeaEntities{
    int strength;
    public Stream(){}
    public Stream(Position position, Shape shape,int strength){
        super(position,"stream",shape);
        this.strength=strength;
    }

    public int getStrength() {
        return strength;
    }
}
