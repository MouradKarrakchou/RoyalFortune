package fr.unice.polytech.si3.qgl.royal_fortune.environment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;

import java.util.Optional;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Stream.class, name = "stream"),
        @JsonSubTypes.Type(value = Reef.class, name = "reef")
})
public class SeaEntities {
    Position position;
    Shape shape;
    public SeaEntities(Position position, Shape shape){
        this.position=position;
        this.shape=shape;
        shape.setPosition(position);
    }

    public SeaEntities() {
    }

    public Position getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }


    public Optional<Stream> isStream() {
        if(this instanceof Stream){
            Stream current = (Stream) this;
            return Optional.of(current);
        }
        return Optional.empty();
    }
    public Optional<Reef> isReef() {
        if(this instanceof Reef){
            Reef current = (Reef) this;
            return Optional.of(current);
        }
        return Optional.empty();
    }

}
