package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;

import java.util.List;

public class InitGameToolDAO extends InitGameDAO {

    private List<SeaEntities> seaEntities;

    public InitGameToolDAO() {}

    public InitGameToolDAO(Goal goal, Ship ship, List<Sailor> sailors, int shipCount, Wind wind, List<SeaEntities> allEntities) {
        super(goal, ship, sailors, shipCount, wind);
        this.seaEntities = allEntities;
    }

    public List<SeaEntities> getSeaEntities() {
        return seaEntities;
    }
}
