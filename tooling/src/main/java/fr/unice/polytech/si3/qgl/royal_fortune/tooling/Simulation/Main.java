package fr.unice.polytech.si3.qgl.royal_fortune.tooling.Simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		double xCheckpoint = -200.0;
		double yCheckpoint = -1000.0;

		String json = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":"+xCheckpoint+",\"y\":"+yCheckpoint+",\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":80.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"royal_fortune\",\"deck\":{\"width\":2,\"length\":3},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2.0,\"height\":3.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Pouce\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Luffy Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Edward Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Edward Pouce\"}],\"shipCount\":1}";
		Game game = new Game(json);
		String textForOutput = game.getAllCheckpointsForOutput()+"---\n";
		while (!game.isFinished()) {
			game.nextRound();
			System.out.println(game);
			textForOutput += game.toString();
		}

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("output.txt"));
			writer.write(textForOutput);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
