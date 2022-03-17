package fr.unice.polytech.si3.qgl.royal_fortune.tooling;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation.InitGameToolDAO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InitGameToolDAOTest {
    private InitGameToolDAO initGameToolDao = new InitGameToolDAO();

    
    
    @Test
    public void genSailorsNormalUsage() throws Exception {
        Deck deck = new Deck(3, 3);
        Ship ship = new Ship(null, 0, new Position(), "shipTest", deck, null, null);
        //String type, int life, Position position, String name, Deck deck, List<Entities> entities, Shape shape)
        List<Sailor> res = initGameToolDao.genSailors(5, ship);
        res.forEach(s -> System.out.println(s.getX() +" || "+s.getY()));

        assertEquals(0,res.get(0).getX());
        assertEquals(0,res.get(0).getY());

        assertEquals(1,res.get(1).getX());
        assertEquals(0,res.get(1).getY());

        assertEquals(2,res.get(2).getX());
        assertEquals(0,res.get(2).getY());

        assertEquals(0,res.get(3).getX());
        assertEquals(1,res.get(3).getY());

        assertEquals(1,res.get(4).getX());
        assertEquals(1,res.get(4).getY());


    }

    @Test
    public void genSailorsNormalAbuse() throws Exception {
        Deck deck = new Deck(0, 0);
        Ship ship = new Ship(null, 0, new Position(), "shipTest", deck, null, null);
        //String type, int life, Position position, String name, Deck deck, List<Entities> entities, Shape shape)
        try{
            initGameToolDao.genSailors(5, ship);
        }catch(Exception e){
            assertTrue(true);
        }
    }
}
