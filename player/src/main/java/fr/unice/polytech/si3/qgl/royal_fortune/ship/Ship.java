package fr.unice.polytech.si3.qgl.royal_fortune.ship;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.DirectionsManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Shape;

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
				 .collect(Collectors.toList());
	}

	/**
	 * Get the rudder, return null if there is no rudder on the ship (Week 1-3)
	 * @return The rudder entity.
	 */
	public Rudder getRudder(){
		for(Entities entity : entities)
			if (entity instanceof Rudder)
				return (Rudder) entity;
		return null;
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
}
