package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import fr.unice.polytech.si3.qgl.royal_fortune.Wind;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	static final Logger logger = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) throws IOException {
		String json= "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1000,\"y\":100,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":100.0}},{\"position\":{\"x\":-200.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":80.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"royal_fortune\",\"deck\":{\"width\":2,\"length\":4},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Luffy Pouce\"}],\"shipCount\":1}";
		String jsonTest = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":1000,\"y\":100,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":100}},{\"position\":{\"x\":-200,\"y\":1000,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":80}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0,\"y\":0,\"orientation\":0},\"name\":\"royal_fortune\",\"deck\":{\"width\":4,\"length\":3},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"rudder\"},{\"x\":3,\"y\":2,\"type\":\"sail\"},{\"x\":3,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2,\"height\":4,\"orientation\":0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":3,\"id\":4,\"name\":\"Luffy olala\"},{\"x\":1,\"y\":2,\"id\":5,\"name\":\"Luffy siuuuu\"}],\"shipCount\":1}";
		String jsonWeek5 = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":-5586.701434159062,\"y\":-195.31249999999972,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":200}},{\"position\":{\"x\":-3259.452411994781,\"y\":1835.937500000001,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":200}},{\"position\":{\"x\":-1401.564537157755,\"y\":4108.072916666667,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":200}},{\"position\":{\"x\":-3650.5867014341584,\"y\":1529.9479166666674,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":200}},{\"position\":{\"x\":-5495.436766623214,\"y\":4127.604166666669,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":200}},{\"position\":{\"x\":-1140.8083441981753,\"y\":-273.43749999999847,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":200}},{\"position\":{\"x\":-3604.9543676662297,\"y\":2154.9479166666674,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":200}},{\"position\":{\"x\":3337.6792698826616,\"y\":1809.8958333333335,\"orientation\":0},\"shape\":{\"type\":\"circle\",\"radius\":200}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":-3650.58670143416,\"y\":1842.4479166666663,\"orientation\":-2.740166925631097},\"name\":\"royal_fortune\",\"deck\":{\"width\":3,\"length\":7},\"entities\":[{\"x\":6,\"y\":1,\"type\":\"rudder\"},{\"x\":3,\"y\":1,\"type\":\"sail\",\"openned\":false},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":2,\"type\":\"oar\"},{\"x\":4,\"y\":2,\"type\":\"oar\"},{\"x\":5,\"y\":2,\"type\":\"oar\"},{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"}],\"life\":1050,\"shape\":{\"type\":\"rectangle\",\"width\":3,\"height\":7,\"orientation\":0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Edward Pouce\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Edward Pouce\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Edward Teach\"},{\"x\":1,\"y\":1,\"id\":4,\"name\":\"Luffy Pouce\"},{\"x\":1,\"y\":2,\"id\":5,\"name\":\"Luffy Pouce\"},{\"x\":2,\"y\":0,\"id\":6,\"name\":\"Edward Pouce\"},{\"x\":2,\"y\":1,\"id\":7,\"name\":\"Edward Pouce\"},{\"x\":2,\"y\":2,\"id\":8,\"name\":\"Luffy Teach\"},{\"x\":3,\"y\":0,\"id\":9,\"name\":\"Jack Teach\"},{\"x\":3,\"y\":1,\"id\":10,\"name\":\"Jack Teach\"},{\"x\":3,\"y\":2,\"id\":11,\"name\":\"Luffy Teach\"}],\"shipCount\":1}";
		Wind wind = new Wind(0.0, 100.0);
		Game game = new Game(jsonWeek5);
		StringBuilder textForOutput = new StringBuilder(game.getAllCheckpointsForOutput() + "---\n");
		int tour=0;
		while (!game.isFinished()&&tour<100) {
			game.nextRound(wind);
			logger.info(String.valueOf(game));
			textForOutput.append(game);
			tour++;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
			try {
				writer.write(String.valueOf(textForOutput));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.log(Level.INFO, "Exception");
			}
		}

	}
}
