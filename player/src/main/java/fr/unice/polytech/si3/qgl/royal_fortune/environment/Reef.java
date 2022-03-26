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
        if (!(object instanceof Reef))
            return false;
        Reef reef=(Reef) object;
        if (this.position.equals(reef.getPosition())
                &&this.shape.equals(reef.shape))
            return true;
        return false;
    }
}
