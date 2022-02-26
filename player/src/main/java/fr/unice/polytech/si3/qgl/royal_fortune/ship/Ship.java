package fr.unice.polytech.si3.qgl.royal_fortune.ship;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
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
	private ArrayList<Entities> entities;
	private Shape shape;
	
	public Ship() {}
	
	public Ship(String type, int life, Position position, String name, Deck deck, ArrayList<Entities> entities, Shape shape) {
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
	public ArrayList<Entities> getEntities() {
		return entities;
	}
	public Shape getShape() {
		return shape;
	}


	public ArrayList<Oar> getOarList(String orientation) {
		 return (ArrayList<Oar>) entities.stream()
				 .filter(entity -> entity instanceof Oar)
				 .map(Oar.class::cast)
				 .filter(oar -> oar.getSailor()==null)
				 .filter(oar -> oar.isLeft() == orientation.equals("left"))
				 .collect(Collectors.toList());
	}

	public void setPosition(Position position){
		this.position = position;
	}

	public void setEntities(ArrayList<Entities> entities) {
		this.entities = entities;
	}

	@Override
	public String toString() {
		try {
			 return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			final Logger LOGGER = Logger.getLogger(JsonManager.class.getName());
			LOGGER.log(Level.INFO, "Exception");
		}
		return "";
	}

}
