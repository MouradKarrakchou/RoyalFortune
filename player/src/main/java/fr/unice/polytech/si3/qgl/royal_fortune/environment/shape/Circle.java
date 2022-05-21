package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.Cockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryCircle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
@JsonIgnoreProperties(value = {
        "type"
})
public class Circle extends Shape{

    private double radius;

    public Circle() {}
    public Circle(double radius) {
        super("circle");
        this.radius = radius;
    }

    public double getRadius() { return radius; }

    @Override
    public Boolean positionIsInTheShape(Position pointA, Position seaEntitiesPos) {
        return(GeometryCircle.positionIsInTheCircle(pointA,seaEntitiesPos,this));
    }

    @Override
    public List<Position> computeIntersectionWith(Segment segment, Position seaEntitiesPos) {
        return GeometryCircle.computeIntersectionWith(segment, seaEntitiesPos, this);
    }

    @Override
    public void updateForReef() {
        if (!super.updated)
            radius+= Cockpit.SECURITY_UPSCALE;
        super.updated=true;
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Circle circle))
            return false;
        return this.radius == circle.getRadius();
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode oarActionJSON = mapper.createObjectNode();
        oarActionJSON.put("type", "circle");
        oarActionJSON.put("radius", radius);

        try {
            return mapper.writeValueAsString(oarActionJSON);
        } catch (JsonProcessingException e) {
            logger.log(Level.INFO, "Exception");
        }
        return "";
    }

}
