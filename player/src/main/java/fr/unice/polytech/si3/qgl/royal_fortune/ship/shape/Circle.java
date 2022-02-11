package fr.unice.polytech.si3.qgl.royal_fortune.ship.shape;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Circle extends Shape{

    private double radius;

    public Circle() {}
    public Circle(String type, double radius) {
        super(type);
        this.radius = radius;
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
            e.printStackTrace();
        }
        return "";
    }

}
