package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
public class Reef extends SeaEntities {

    public Reef(){}
    public Reef(Position position, Shape shape){
        super(position,shape);
    }
}
