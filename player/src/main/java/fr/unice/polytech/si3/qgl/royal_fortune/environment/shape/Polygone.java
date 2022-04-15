package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.NextRoundDAO;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@JsonIgnoreProperties(value = {
        "type"
})
public class Polygone extends Shape{
    private static final Logger LOGGER = Logger.getLogger(Polygone.class.getName());
    private double orientation;
    private Point vertices[];

    public Polygone() {}

    public Polygone(Point vertices[], double orientation) {
        super("polygon");
        this.vertices = vertices;
        this.orientation = orientation;
    }

    public double getOrientation() {
        return orientation;
    }

    public Point[] getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.info("Json Exception");
        }
        return "";
    }
}
