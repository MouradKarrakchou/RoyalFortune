package fr.unice.polytech.si3.qgl.royal_fortune.environment.shape;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryPolygone;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.GeometryRectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Vector;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.NextRoundDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fr.unice.polytech.si3.qgl.royal_fortune.Cockpit.SECURITY_UPSCALE;

@JsonIgnoreProperties(value = {
        "type"
})
public class Polygone extends Shape{
    private static final Logger LOGGER = Logger.getLogger(Polygone.class.getName());
    private double orientation;
    private Point[] vertices;
    private List<Segment> segmentList = new ArrayList<>();

    public Polygone() {}

    public Polygone(Point[] vertices, double orientation) {
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

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    /**
     * For a given polygon will shift all local coordinates to their global coordinates and upscale their size
     * to prevent from oaring to close from a rift.
     * @param center The center of the shape.
     */
    public void updatePolygone(Position center){
        for(int i = 0; i < vertices.length; i++){
            Point currentPont = vertices[i];
            Vector centerPointUnitVector = new Vector(new Point(0,0), currentPont).unitVector();
            vertices[i] = new Point(
                    (int) Math.ceil(currentPont.getX() + centerPointUnitVector.x * SECURITY_UPSCALE),
                    (int) Math.ceil(currentPont.getY() + centerPointUnitVector.y * SECURITY_UPSCALE)
            );
        }
        Mathematician.changeBasePointList(vertices, center);
    }

    @Override
    public List<Position> computeIntersectionWith(Segment segment, Position seaEntitiesPos) {
        return GeometryPolygone.computeIntersectionWith(segment, seaEntitiesPos, this);
    }

    @Override
    public List<Beacon> generateBeacon(Position aPosition, boolean isAReef) {
        return GeometryPolygone.generateBeacon(this);
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
