package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
public class Stream extends SeaEntities{
    int strength;
    public Stream(){}
    public Stream(Position position, Shape shape,int strength){
        super(position,shape);
        this.strength=strength;
    }

    public int getStrength() {
        return strength;
    }
    @Override
    public boolean equals(Object object){
        if (!(object instanceof Stream stream))
            return false;
        return this.position.equals(stream.getPosition())
                && this.strength == (stream.getStrength())
                && this.shape.equals(stream.shape);
    }

    /*
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + String.valueOf(position.getX()).hashCode();
        result = 31 * result + Integer.parseInt(String.valueOf(strength));
        result = 31 * result + String.valueOf(shape.getType()).hashCode();
        return result;
    }*/
}
