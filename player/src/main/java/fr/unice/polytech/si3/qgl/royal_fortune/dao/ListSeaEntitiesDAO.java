package fr.unice.polytech.si3.qgl.royal_fortune.dao;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;

import java.util.List;

public class ListSeaEntitiesDAO {
    private List<SeaEntities> visibleEntities;

    public ListSeaEntitiesDAO(List<SeaEntities> visibleEntities) {
        this.visibleEntities = visibleEntities;
    }

    public List<SeaEntities> getVisibleEntities() {
        return visibleEntities;
    }
}
