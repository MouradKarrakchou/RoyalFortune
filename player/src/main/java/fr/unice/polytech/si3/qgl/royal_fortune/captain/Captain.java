package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Captain {
    private final Ship ship;
    private final ArrayList<Sailor> sailors;
    private final ArrayList<Action> roundActions;

    public Captain(Ship ship, ArrayList<Sailor> sailors){
        this.ship = ship;
        this.sailors = sailors;
        roundActions = new ArrayList<>();
    }

    /**
     * Associate the maximum of sailors to a specific oar orientation.
     * @param orientation The orientation to target ("left" or "right")
     */
    public void associateSailorToOar(String orientation){
        ArrayList<Oar> oarList = ship.getOarList(orientation);
        int sailorsIndex = 0;
        int oarListIndex = 0;

        // We continue associating until we run out of sailors or oars
        while(oarListIndex < oarList.size() && sailorsIndex < sailors.size()){
            if (sailors.get(sailorsIndex).getTargetEntity() == null){
                Oar oar = oarList.get(oarListIndex);
                oarListIndex++;
                sailors.get(sailorsIndex).setTargetEntity(oar);
            }
            sailorsIndex++;
        }
    }

    public void askSailorToMove(){

    }
}
