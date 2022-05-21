package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.util.Objects;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), strength);
    }
}
