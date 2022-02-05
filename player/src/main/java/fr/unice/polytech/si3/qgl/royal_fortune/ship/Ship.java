package fr.unice.polytech.si3.qgl.royal_fortune.ship;

import java.util.ArrayList;
import java.util.stream.Collectors;

import fr.unice.polytech.si3.qgl.royal_fortune.Sailor;
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
				 .filter(oar -> oar.isLeft() == orientation.equals("left"))
				 .collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "Ship [life=" + life + ", position=" + position + ", name=" + name + ", deck=" + deck + ", entities="
				+ entities + ", shape=" + shape + "]";
	}
}
