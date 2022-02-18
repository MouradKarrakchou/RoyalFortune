package fr.unice.polytech.si3.qgl.royal_fortune.tooling.Simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		double xCheckpoint = 1800.0;
		double yCheckpoint = 1000.0;

		//String json = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":"+xCheckpoint+",\"y\":"+yCheckpoint+",\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":80.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"name\":\"royal_fortune\",\"deck\":{\"width\":2,\"length\":3},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":1,\"type\":\"oar\"},{\"x\":2,\"y\":0,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"oar\"}],\"life\":300,\"shape\":{\"type\":\"rectangle\",\"width\":2.0,\"height\":3.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Jack Pouce\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Luffy Teach\"},{\"x\":1,\"y\":0,\"id\":2,\"name\":\"Edward Pouce\"},{\"x\":1,\"y\":1,\"id\":3,\"name\":\"Edward Pouce\"}],\"shipCount\":1}";
		String json = "{\r\n"
				+ "    \"goal\": {\r\n"
				+ "        \"mode\": \"REGATTA\",\r\n"
				+ "        \"checkpoints\": [\r\n"
				+ "            {\r\n"
				+ "                \"position\": {\r\n"
				+ "                    \"x\": 10,\r\n"
				+ "                    \"y\": -350\r\n"
				+ "                },\r\n"
				+ "                \"shape\": {\r\n"
				+ "                    \"type\": \"circle\",\r\n"
				+ "                    \"radius\": 85.0\r\n"
				+ "                }\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"position\": {\r\n"
				+ "                    \"x\": -150,\r\n"
				+ "                    \"y\": 1250\r\n"
				+ "                },\r\n"
				+ "                \"shape\": {\r\n"
				+ "                    \"type\": \"circle\",\r\n"
				+ "                    \"radius\": 85.0\r\n"
				+ "                }\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    },\r\n"
				+ "    \"ship\": {\r\n"
				+ "        \"name\": \"Moby Dick\",\r\n"
				+ "        \"life\": 300,\r\n"
				+ "	\"position\": {\r\n"
				+ "      		\"x\": 0,\r\n"
				+ "     		\"y\": 0,\r\n"
				+ "      		\"orientation\": 0\r\n"
				+ "    	},\r\n"
				+ "        \"deck\": {\r\n"
				+ "            \"length\": 4,\r\n"
				+ "            \"width\": 2\r\n"
				+ "        },\r\n"
				+ "        \"entities\": [\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"oar\",\r\n"
				+ "                \"x\": 1,\r\n"
				+ "                \"y\": 0\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"oar\",\r\n"
				+ "                \"x\": 1,\r\n"
				+ "                \"y\": 1\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"oar\",\r\n"
				+ "                \"x\": 2,\r\n"
				+ "                \"y\": 0\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"oar\",\r\n"
				+ "                \"x\": 2,\r\n"
				+ "                \"y\": 1\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"oar\",\r\n"
				+ "                \"x\": 3,\r\n"
				+ "                \"y\": 0\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"oar\",\r\n"
				+ "                \"x\": 3,\r\n"
				+ "                \"y\": 1\r\n"
				+ "            }\r\n"
				+ "\r\n"
				+ "        ]\r\n"
				+ "    },\r\n"
				+ "    \"startingPositions\": [\r\n"
				+ "        {\r\n"
				+ "            \"x\": 0.0,\r\n"
				+ "            \"y\": 0.0,\r\n"
				+ "            \"orientation\": 0.0\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"wind\": {\r\n"
				+ "        \"direction\": 0,\r\n"
				+ "        \"strenght\": 0\r\n"
				+ "    },\"sailors\": [\r\n"
				+ "    {\r\n"
				+ "      \"x\": 0,\r\n"
				+ "      \"y\": 0,\r\n"
				+ "      \"id\": 0,\r\n"
				+ "      \"name\": \"Jack Pouce\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"x\": 0,\r\n"
				+ "      \"y\": 1,\r\n"
				+ "      \"id\": 1,\r\n"
				+ "      \"name\": \"Luffy Teach\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"x\": 1,\r\n"
				+ "      \"y\": 0,\r\n"
				+ "      \"id\": 2,\r\n"
				+ "      \"name\": \"Edward Pouce\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"x\": 1,\r\n"
				+ "      \"y\": 1,\r\n"
				+ "      \"id\": 3,\r\n"
				+ "      \"name\": \"Edward Pouce\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
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
