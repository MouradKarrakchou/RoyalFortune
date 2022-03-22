package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.exception.ToFarAssociationException;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

import java.util.HashMap;
import java.util.Map;

public class Associations {
    private final Map<Sailor, Entities> sailorAssociations;
    private final Map<Entities, Sailor> entityAssociations;

    public Associations(){
        this.sailorAssociations = new HashMap<>();
        this.entityAssociations = new HashMap<>();
    }

    public void dissociateAll(){
        entityAssociations.clear();
        sailorAssociations.clear();
    }

    public int size(){
        return sailorAssociations.size();
    }

    @Override
    public String toString() {
        return sailorAssociations.toString();
    }

    public void addAssociation(Sailor sailor, Entities entity){

        try {
            checkForTargetOutOfRange(sailor, entity);
        } catch (ToFarAssociationException e) {
            e.printStackTrace();
        }

        sailorAssociations.put(sailor, entity);
        entityAssociations.put(entity, sailor);
    }

    public boolean isFree(Sailor sailor){
        return !sailorAssociations.containsKey(sailor);
    }

    public boolean isFree(Entities entity){
        return !entityAssociations.containsKey(entity);
    }

    public Sailor getAssociatedSailor(Entities entity){
        return entityAssociations.get(entity);
    }

    public Entities getAssociatedEntity(Sailor sailor){
        return sailorAssociations.get(sailor);
    }

    public void checkForTargetOutOfRange(Sailor sailor, Entities entity) throws ToFarAssociationException {
        if (sailor.getDistanceToEntity(entity) > 5)
            throw new ToFarAssociationException("Sailor to far from target");
    }
}
