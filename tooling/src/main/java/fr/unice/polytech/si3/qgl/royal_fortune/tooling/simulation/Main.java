package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	static final Logger logger = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) throws IOException {
		String json= " {\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":-221.64276401564362,\"y\":-833.3333333333336,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":100.0}},{\"position\":{\"x\":-200.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":80.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"royal_fortune\",\"deck\":{\"width\":2,\"length\":4},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2.0,\"height\":4.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Luffy Pouce\"}],\"shipCount\":1}";
		Game game = new Game(json);
		StringBuilder textForOutput = new StringBuilder(game.getAllCheckpointsForOutput() + "---\n");
		while (!game.isFinished()) {
			game.nextRound();
			logger.info(String.valueOf(game));
			textForOutput.append(game);
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		try {
			writer.write(String.valueOf(textForOutput));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.log(Level.INFO, "Exception");
		} finally {
			writer.close();
		}

	}
}
