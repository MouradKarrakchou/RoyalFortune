package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
    public void updateForReef() {
        if (!super.updated)
            radius+=15;
        super.updated=true;
    }
    @Override
    public boolean equals(Object object){
        if (!(object instanceof Circle circle))
            return false;
        return this.radius == circle.getRadius();
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
