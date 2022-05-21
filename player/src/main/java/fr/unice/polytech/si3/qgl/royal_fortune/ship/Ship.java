package fr.unice.polytech.si3.qgl.royal_fortune.ship;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaEntities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.*;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Ship {
	private String type;
	private int life;
	private Position position;
	private String name;
	private Deck deck;
	private List<Entities> entities;


	private Shape shape;
	final Logger logger = Logger.getLogger(Ship.class.getName());
	
	public Ship() {}

	public Ship(Ship ship) {
		this.type =ship.type;
		this.life = ship.life;
		this.position = ship.position;
		this.name = ship.name;
		this.deck = ship.deck;
		this.entities = ship.entities;
		this.shape = ship.shape;
	}
	public Ship(String type, int life, Position position, String name, Deck deck, List<Entities> entities, Shape shape) {
		this.type =type;
		this.life = life;
		this.position = position;
		this.name = name;
		this.deck = deck;
		this.entities = entities;
		this.shape = shape;
	}

	public String getType() {
		return type;
	}
	public int getLife() {
		return life;
	}
	public Position getPosition() {
		return position;
	}
	public String getName() {
		return name;
	}
	public Deck getDeck() {
		return deck;
	}
	public List<Entities> getEntities() {
		return entities;
	}
	public Shape getShape() {
		return shape;
	}


	public List<Oar> getOarList(int orientation, Associations associations) {
		 return entities.stream()
				 .filter(Oar.class::isInstance)
				 .map(Oar.class::cast)
				 .filter(associations::isFree)
				 .filter(oar -> oar.isLeft() == (orientation == DirectionsManager.LEFT))
				 .toList();
	}

	/**
	 * Get the rudder, return null if there is no rudder on the ship (Week 1-3)
	 * @return The rudder entity.
	 */
	public Rudder getRudder(){
		for(Entities entity : entities)
			if (entity instanceof Rudder rudder)
				return  rudder;
		return null;
	}
	public ArrayList<Sail> getSail(){
		ArrayList<Sail> sailArrayList=new ArrayList<>();
		for(Entities entity : entities)
			if (entity instanceof Sail sail)
				sailArrayList.add(sail);
		return sailArrayList;
	}

	public void setPosition(Position position){
		this.position=position;
	}

	public void setEntities(List<Entities> entities) {
		this.entities = entities;
	}

	@Override
	public String toString() {
		try {
			 return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			logger.log(Level.INFO, "Exception");
		}
		return "";
	}

	public int getNbrOar() {
		return getAllOar().size();
	}

	public List<Oar> getAllOar() {
		List<Oar> listOar = new ArrayList<>();
		for(Entities currentEntity : entities){
			if(currentEntity.isOar()){
				listOar.add((Oar)currentEntity);
			}
		}
		return listOar;
	}
	public void updatePos(Position position){
		this.position.update(position);
	}

    public Watch getWatch() {
		for(Entities entity : entities)
			if (entity instanceof Watch watch)
				return watch;
		return null;
    }
}
