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
    public static StringBuilder parameterOfGame = new StringBuilder();
    public static StringBuilder shipPosition = new StringBuilder();


    public OutputMaker(){
        this.parameterOfGame = new StringBuilder();
    }

    public static void insertCheckpoints(List<Checkpoint> allCheckpoints){
        parameterOfGame.append(OutputMaker.getAllCheckpointsForOutput(allCheckpoints) + "---|\n");
    }

    public static void insertAllSeaEntities(List<SeaEntities> allSeaEntities){
        try {
            parameterOfGame.append(getAllSeaEntitiesForOutput(allSeaEntities)+ "---|\n");
        } catch (Exception e) {
            LOGGER.info("Exception");
        };
    }

    public static void insertBeacons(List<Beacon> allBeacon){
        OutputMaker.getAllBeaconForOutput(allBeacon);
    }


    public static void appendShipPosition(String text){
        shipPosition.append(text);
    }

    public static StringBuilder getAllCheckpointsForOutput( List<Checkpoint> checkPoints) {
        StringBuilder out = new StringBuilder();
        for(Checkpoint checkpoint : checkPoints) {
            Position pos = checkpoint.getPosition();
            double x = pos.getX();
            double y = pos.getY();
            double radius = ((Circle)checkpoint.getShape()).getRadius();
            out.append(Math.round(x)).append(";").append(Math.round(y)).append(";").append(Math.round(radius)).append("|\n");
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
            out.append("|\n");
        }
        return out;
    }

    public static StringBuilder createOutForStream(Stream stream, StringBuilder out) throws Exception {
        Position streamPos = stream.getPosition();
        out.append("stream").append(";");
        Optional<Rectangle> isRectangle = stream.getShape().isRectangle();
        if(isRectangle.isPresent()){
            Rectangle rectangle = isRectangle.get();
            out.append(Math.round(rectangle.getHeight())).append(";").append(Math.round(rectangle.getWidth())).append(";");
            out.append(Math.round(stream.getStrength())).append(";");
            out.append(Math.round(streamPos.getX())).append(";").append(Math.round(streamPos.getY())).append(";").append(Math.round(streamPos.getOrientation()));
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
            out.append("rect").append(";").append(Math.round(rect.getHeight())).append(";").append(Math.round(rect.getWidth())).append(";");
            out.append(Math.round(streamPos.getX())).append(";").append(Math.round(streamPos.getY())).append(";").append(streamPos.getOrientation());
        }
        else if(isCircle.isPresent()){
            Circle circle = isCircle.get();
            out.append("circle").append(";").append(Math.round(circle.getRadius())).append(";");
            out.append(Math.round(streamPos.getX())).append(";").append(Math.round(streamPos.getY())).append(";").append(streamPos.getOrientation());
        }
        else
            throw new Exception("Stream with other shape than rectangle");
        return out;
    }

    private static void getAllBeaconForOutput(List<Beacon> allBeacon) {
        for (Beacon beacon : allBeacon) {
            Position beaconPos = beacon.getPosition();
            parameterOfGame.append(Math.round(beaconPos.getX())).append(";").append(Math.round(beaconPos.getY())).append("|\n");
        }
        parameterOfGame.append("---|\n");
    }

    public static void writeOutputInFile(){
        parameterOfGame.append(shipPosition);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            try {
                writer.write(String.valueOf(parameterOfGame));
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Exception");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
