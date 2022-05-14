package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Polygone;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.OtherShip;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Stream.class, name = "stream"),
        @JsonSubTypes.Type(value = Reef.class, name = "reef"),
        @JsonSubTypes.Type(value = OtherShip.class, name = "ship")
})
@JsonIgnoreProperties(value = {
        "stream",
        "reef"
})

public class SeaEntities {
    Position position;
    Shape shape;
    public SeaEntities(Position position, Shape shape){
        this.position=position;
        this.shape=shape;
        shape.computeSegmentsIfPossible(position);
    }

    public SeaEntities() {
    }

    public Position getPosition() {
        return position;
    }

    public Shape getShape() {
        if (shape instanceof Polygone polygone){
            polygone.updatePolygone(position);
        }
        shape.updateForReef();
        shape.computeSegmentsIfPossible(position);
        return shape;
    }
    public Shape getShapeForTool(){
        return shape;
    }


    public Boolean isStream() {
        return this instanceof Stream;
    }

    public Boolean isReef() {
        return this instanceof Reef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        SeaEntities that = (SeaEntities) o;

        if (!position.equals(that.position))
            return false;

        return shape.equals(that.shape);
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (shape != null ? shape.hashCode() : 0);
        return result;
    }
}
