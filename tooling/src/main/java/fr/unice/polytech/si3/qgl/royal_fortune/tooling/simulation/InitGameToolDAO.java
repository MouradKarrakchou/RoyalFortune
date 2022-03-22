package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InitGameToolDAO extends InitGameDAO {

    private List<SeaEntities> seaEntities;
    private int minumumCrewSize;
    private int maximumCrewSize;
    private Ship ship;
    private Position startingPositions;

    public InitGameToolDAO() {}
    public InitGameToolDAO(Goal goal, Ship ship, int shipCount, Wind wind, List<SeaEntities> seaEntities, int maxRound,int minumumCrewSize, int maximumCrewSize, Position startingPositions) {
        super(goal, ship, new ArrayList<>(), shipCount, wind);
        this.ship = ship;
        this.seaEntities = seaEntities;
        this.maximumCrewSize = maximumCrewSize;
        this.minumumCrewSize = minumumCrewSize;
        this.startingPositions = startingPositions;
    }
     public void configDao(){
         int crewSize = countCrew(minumumCrewSize, maximumCrewSize);
         try {
             setSailors(genSailors(crewSize, ship));
         } catch (Exception e) {
             e.printStackTrace();
         }
         super.getShip().setPosition(startingPositions);
     }

    public int countCrew(int minumumCrewSize, int maximumCrewSize){
        int crewSize = 0;
        if(minumumCrewSize == maximumCrewSize) crewSize = minumumCrewSize;
        else{
            Random r = new Random();
            crewSize = r.nextInt(maximumCrewSize-minumumCrewSize) + minumumCrewSize;
        }
        return crewSize;
    }

    public List<Sailor> genSailors(int crewSize, Ship ship) throws Exception {
        List<Sailor> listSailor = new ArrayList<>();
        int shipLength = ship.getDeck().getLength();
        int shipWidth = ship.getDeck().getWidth();

        int x = 0;
        int y = 0;
        for(int i = 0 ; i < crewSize ; i++){
            //int id, int x, int y, String name
            Sailor sailor = new Sailor(i, x, y, "sailor"+i);
            listSailor.add(sailor);
            x+=1;
            if(x > shipWidth-1){
                x=0;
                y+=1;
                if(y > shipLength)
                    throw new Exception();
            }
        }
        return listSailor;
    }

    public List<SeaEntities> getSeaEntities() {
        return seaEntities;
    }


}
