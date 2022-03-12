package fr.unice.polytech.si3.qgl.royal_fortune.target;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Mathematician;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Observer {
    private final int MAX_RANGE = 1000;
    private List<SeaEntities> currentSeaEntities;
    private Position shipPosition;
    private Mathematician mathematician;
    private Cartologue cartologue;
    private int RANGE=40;

    public Observer(Position shipPosition, Wind wind){
        this.shipPosition=shipPosition;
        this.currentSeaEntities=new ArrayList<>();
        cartologue=new Cartologue(getStream(),null,wind);
        this.mathematician = new Mathematician(cartologue);
    }

    private List<Stream> getStream(){
        List<Stream> listOfStream=new ArrayList<>();
        for(SeaEntities seaEntities:currentSeaEntities){
            if (seaEntities instanceof Stream)
                listOfStream.add((Stream) seaEntities);
        }
        return listOfStream;
    }
    /**
     * Check in half-circle all the seaEntities in RANGE. If newSeaEntities is different from currentSeaEntities return true.
     * @param newSeaEntities
     * @return boolean
     */
    public Boolean checkIfNewSeaEntities(List<SeaEntities> newSeaEntities){
        return null;
    }

    /**
     *If there is new Entities in RANGE ask Mathematician to find the best path through a beacon.
     * @param newSeaEntities
     * @return If return empty we target the checkpoint else we target the Beacon
     */
    public Optional<Beacon> watchSea(List<SeaEntities> newSeaEntities){
        return null;
    }

}
