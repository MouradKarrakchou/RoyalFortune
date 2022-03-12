package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import java.util.List;
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
    public Circle(String type, double radius) {
        super(type);
        this.radius = radius;
    }

    /**
     * check if the rectangle is in the circle
     * @return
     */
    public boolean rectangleIsInCircle(Rectangle rectangle){
        List<Position> positionList=rectangle.computeCorner();
        for(Position position:positionList){
            if(Mathematician.distanceFormula(super.position,position)<=radius)
                return true;
        }
        return false;
    }

    public double getRadius() { return radius; }

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
