package fr.unice.polytech.si3.qgl.royal_fortune.ship.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Associations;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bonnet Kilian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
@JsonSubTypes(value = {
		@JsonSubTypes.Type(value = Oar.class, name = "oar"),
		@JsonSubTypes.Type(value = Rudder.class, name = "rudder"),
		@JsonSubTypes.Type(value = Sail.class, name = "sail"),
		@JsonSubTypes.Type(value = Watch.class, name = "watch")
})
@JsonIgnoreProperties(value = {
	"sailor"
})


public class Entities {
	private String type;
	private int x;
	protected int y;
	final Logger logger = Logger.getLogger(Entities.class.getName());
	
	public Entities() {}

	public Entities(String type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			logger.log(Level.INFO, "Exception");
		}
		return "";
	}

    public boolean isOar() {
		return(this instanceof Oar);
    }

	public boolean isWatch() {
		return(this instanceof Watch);
	}

	public boolean isRudder(){
		return(this instanceof Rudder);
	}

	public boolean isSail() {return(this instanceof Sail);}

	/**
	 * For a given range (included), will return the list of unassigned sailors.
	 * @param sailors The list of all the sailors.
	 * @param range The maximum range.
	 * @return The list of unassigned sailors in the oar range.
	 */
	public List<Sailor> getSailorsInRange(List<Sailor> sailors, int range, Associations associations){
		return sailors.stream()
				.filter(associations::isFree)
				.filter(sailor -> sailor.getDistanceToEntity(this) <= range)
				.toList();
	}

	public Optional<Sailor> getNearestSailor(List<Sailor> sailors, int range, Associations associations){
		return sailors.stream()
				.filter(associations::isFree)
				.filter(sailor -> sailor.getDistanceToEntity(this) <= range)
				.min(Comparator.comparingInt(sailor -> sailor.getDistanceToEntity(this)));
	}


}
