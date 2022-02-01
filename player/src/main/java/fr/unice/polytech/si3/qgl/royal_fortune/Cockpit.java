package fr.unice.polytech.si3.qgl.royal_fortune;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sailor;

/**
 * @author Bonnet Killian Imami Ayoub Karrakchou Mourad Le Bihan Leo
 *
 */
public class Cockpit implements ICockpit {
	private Ship ship;
	public void initGame(String game) {
		System.out.println("Init game input: " + game);
		try {
			ship=JsonManager.readJson(game);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public String nextRound(String round) {
		System.out.println("Next round input: " + round);
		try {
			return JsonManager.writeJsonAction(idOfSailors(ship));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Integer> idOfSailors(Ship ship){
		return ship.getEntities().stream()
				.filter(entities -> entities instanceof Sailor)
				.map(entities ->((Sailor) entities).getId())
				.collect(Collectors.toList());}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}
