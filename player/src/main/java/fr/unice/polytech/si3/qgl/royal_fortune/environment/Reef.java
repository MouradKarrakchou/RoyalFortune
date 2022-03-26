package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

public class Reef extends SeaEntities {

    public Reef(){}
    public Reef(Position position, Shape shape){
        super(position,shape);
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Reef reef))
            return false;
        return this.position.equals(reef.getPosition())
                && this.shape.equals(reef.shape);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + String.valueOf(position.getX()).hashCode();
        result = 31 * result + Integer.parseInt(String.valueOf(position.getY()));
        result = 31 * result + String.valueOf(shape.getType()).hashCode();
        return result;
    }
}
