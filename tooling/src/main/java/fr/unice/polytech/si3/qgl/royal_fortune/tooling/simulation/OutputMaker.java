package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputMaker {
    static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static StringBuilder textForOutput = new StringBuilder();

    public OutputMaker(){
        this.textForOutput = new StringBuilder();
    }

    public static void insertCheckpoints(List<Checkpoint> allCheckpoints){
        textForOutput.append(OutputMaker.getAllCheckpointsForOutput(allCheckpoints) + "---\n");
    }

    public static void insertAllSeaEntities(List<SeaEntities> allSeaEntities){
        try {
            textForOutput.append(getAllSeaEntitiesForOutput(allSeaEntities)+ "---\n");
        } catch (Exception e) {
            LOGGER.info("Exception");
        };
    }

    public static void insertAllBeacons(List<Beacon> allBeacon){
        OutputMaker.getAllBeaconForOutput(allBeacon);
    }



    public static void appendForOutput(String text){
        textForOutput.append(text);
    }

    public static StringBuilder getAllCheckpointsForOutput( List<Checkpoint> checkPoints) {
        StringBuilder out = new StringBuilder();
        for(Checkpoint checkpoint : checkPoints) {
            Position pos = checkpoint.getPosition();
            double x = pos.getX();
            double y = pos.getY();
            double radius = ((Circle)checkpoint.getShape()).getRadius();
            out.append(x).append(";").append(y).append(";").append(radius).append("\n");
        }
        return out;
    }

    public static StringBuilder getAllSeaEntitiesForOutput(List<SeaEntities> allSeaEntities) throws Exception {
        StringBuilder out = new StringBuilder();
        for(SeaEntities seaEntities : allSeaEntities) {
            Boolean isStream = seaEntities.isStream();
            Boolean isReef = seaEntities.isReef();
            if(isStream){
                out = createOutForStream((Stream) seaEntities, out);
            }
            else if(isReef){
                out = createOutForReef((Reef) seaEntities, out);
            }
            out.append("\n");
        }
        return out;
    }

    public static StringBuilder createOutForStream(Stream stream, StringBuilder out) throws Exception {
        Position streamPos = stream.getPosition();
        out.append("stream").append(";");
        Optional<Rectangle> isRectangle = stream.getShape().isRectangle();
        if(isRectangle.isPresent()){
            Rectangle rectangle = isRectangle.get();
            out.append(rectangle.getHeight()).append(";").append(rectangle.getWidth()).append(";");
            out.append(stream.getStrength()).append(";");
            out.append(streamPos.getX()).append(";").append(streamPos.getY()).append(";").append(streamPos.getOrientation());
        }
        else
            throw new Exception("Stream with other shape than rectangle");
        return out;
    }

    public static StringBuilder createOutForReef(Reef reef, StringBuilder out) throws Exception {
        Position streamPos = reef.getPosition();
        out.append("reef").append(";");
        Optional<Rectangle> isRectangle = reef.getShape().isRectangle();
        Optional<Circle> isCircle = reef.getShape().isCircle();
        if(isRectangle.isPresent()){
            Rectangle rect = isRectangle.get();
            out.append("rect").append(";").append(rect.getHeight()).append(";").append(rect.getWidth()).append(";");
            out.append(streamPos.getX()).append(";").append(streamPos.getY()).append(";").append(streamPos.getOrientation());
        }
        else if(isCircle.isPresent()){
            Circle circle = isCircle.get();
            out.append("circle").append(";").append(circle.getRadius()).append(";");
            out.append(streamPos.getX()).append(";").append(streamPos.getY()).append(";").append(streamPos.getOrientation());
        }
        else
            throw new Exception("Stream with other shape than rectangle");
        return out;
    }

    private static void getAllBeaconForOutput(List<Beacon> allBeacon) {
        for (Beacon beacon : allBeacon) {
            Position beaconPos = beacon.getPosition();
            textForOutput.append(beaconPos.getX()).append(";").append(beaconPos.getY()).append("\n");
        }
        textForOutput.append("---\n");
    }

    public static void writeOutputInFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            try {
                writer.write(String.valueOf(textForOutput));
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Exception");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
