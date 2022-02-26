package fr.unice.polytech.si3.qgl.royal_fortune.ship.shape;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
@JsonIgnoreProperties(value = {
        "type"
})
public class Circle extends Shape{

    private double radius;
    final Logger LOGGER = Logger.getLogger(JsonManager.class.getName());

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
            LOGGER.log(Level.INFO, "Exception");
        }
        return "";
    }

}
