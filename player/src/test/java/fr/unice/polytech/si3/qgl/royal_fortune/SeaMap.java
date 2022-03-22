package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeaMap {
    private Ship basicShip;
    private Captain captain;
    private List<Checkpoint> checkpoints;
    private List<Sailor> sailors;
    private List<Entities> entities;
    private fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaMap seaMap;
    private Goal goal;
    private FictitiousCheckpoint fictitiousCheckpoint;

    @BeforeEach
    void init(){
        sailors = new ArrayList<>();
        entities = new ArrayList<>();
        checkpoints=new ArrayList<>();
        goal=new Goal("",(ArrayList<Checkpoint>) checkpoints);
        basicShip = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));
        fictitiousCheckpoint=new FictitiousCheckpoint(checkpoints);
        captain=new Captain(basicShip,sailors,goal,fictitiousCheckpoint, new Wind(0,0));

        seaMap=captain.getSeaMap();
    }
    @Test
    void updateCheckPointTest(){
        Checkpoint checkpoint=new Checkpoint(new Position(5000,5000,0),new Circle("",100));
        checkpoints.add(checkpoint);
        assertEquals(false,seaMap.isInCheckpoint(checkpoint));
        fictitiousCheckpoint=new FictitiousCheckpoint(checkpoints);
        seaMap.setFictitiousCheckpoints(fictitiousCheckpoint);
        seaMap.updateCheckPoint(null);
        assertEquals(1,checkpoints.size());
        assertEquals(1,fictitiousCheckpoint.getFictitiousCheckpoints().size());
    }
    @Test
    void updateCheckPointTest2(){
        Checkpoint checkpoint=new Checkpoint(new Position(0,0,0),new Circle("",100));
        checkpoints.add(checkpoint);
        assertEquals(true,seaMap.isInCheckpoint(checkpoint));
        checkpoints.add(new Checkpoint(new Position(500,500,0),new Circle("",100)));
        fictitiousCheckpoint=new FictitiousCheckpoint(checkpoints);
        seaMap.setFictitiousCheckpoints(fictitiousCheckpoint);
        seaMap.updateCheckPoint(null);
        assertEquals(1,checkpoints.size());
        assertEquals(1,fictitiousCheckpoint.getFictitiousCheckpoints().size());
    }
    @Test
    void isInCheckPointPosTest() {
        Checkpoint checkpoint = new Checkpoint(new Position(1, 0, 0), new Circle("", 100));
        assertEquals(true, seaMap.isInCheckpointShipPos(checkpoint,0,0));
        assertEquals(true, seaMap.isInCheckpointShipPos(checkpoint,101,0));
        assertEquals(false, seaMap.isInCheckpointShipPos(checkpoint,102,0));

    }
    @Test
    void isInCheckPointPosTest2() {
        Checkpoint checkpoint = new Checkpoint(new Position(0, 1, 0), new Circle("", 100));
        assertEquals(true, seaMap.isInCheckpointShipPos(checkpoint,0,0));
        assertEquals(true, seaMap.isInCheckpointShipPos(checkpoint,0,101));
        assertEquals(false, seaMap.isInCheckpointShipPos(checkpoint,0,102));

    }

}
