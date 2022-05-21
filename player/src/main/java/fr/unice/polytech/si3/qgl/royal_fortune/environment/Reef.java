package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.util.Objects;

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
        return Objects.hash(super.hashCode());
    }
}
