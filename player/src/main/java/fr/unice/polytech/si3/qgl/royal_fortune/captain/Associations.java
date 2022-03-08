package fr.unice.polytech.si3.qgl.royal_fortune.captain;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;

import java.util.HashMap;
import java.util.Map;

public class Associations {
    private final Map<Sailor, Entities> sailorAssociations;
    private final Map<Entities, Sailor> entitiyAssociations;

    public Associations(){
        this.sailorAssociations = new HashMap<>();
        this.entitiyAssociations = new HashMap<>();
    }

    public void dissociateAll(){
        entitiyAssociations.clear();
        sailorAssociations.clear();
    }

    public void addAssociation(Sailor sailor, Entities entity){
        sailorAssociations.put(sailor, entity);
        entitiyAssociations.put(entity, sailor);
    }

    public boolean isFree(Sailor sailor){
        return sailorAssociations.containsKey(sailor);
    }

    public boolean isFree(Entities entity){
        return entitiyAssociations.containsKey(entity);
    }

    public Sailor getAssociatedSailor(Entities entity){
        return entitiyAssociations.get(entity);
    }

    public Entities getAssociatedEntity(Sailor sailor){
        return sailorAssociations.get(sailor);
    }
}
