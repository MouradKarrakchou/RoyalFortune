package fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	static final Logger logger = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) throws IOException {
		String jsonWeek4 = "{\n" +
				"  \"goal\": {\n" +
				"    \"mode\": \"REGATTA\",\n" +
				"    \"checkpoints\": [\n" +
				"      {\n" +
				"        \"type\": \"checkpoint\",\n" +
				"        \"position\": {\n" +
				"          \"x\": 2279.6317218053687,\n" +
				"          \"y\": 2879.047015765768\n" +
				"        },\n" +
				"        \"shape\": {\n" +
				"          \"type\": \"circle\",\n" +
				"          \"radius\": 100\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"checkpoint\",\n" +
				"        \"position\": {\n" +
				"          \"x\": 2256.4589017735334,\n" +
				"          \"y\": 1352.1959459459492\n" +
				"        },\n" +
				"        \"shape\": {\n" +
				"          \"type\": \"circle\",\n" +
				"          \"radius\": 100\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"checkpoint\",\n" +
				"        \"position\": {\n" +
				"          \"x\": 3590.3073840382,\n" +
				"          \"y\": 2221.734234234234\n" +
				"        },\n" +
				"        \"shape\": {\n" +
				"          \"type\": \"circle\",\n" +
				"          \"radius\": \"80\"\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"checkpoint\",\n" +
				"        \"position\": {\n" +
				"          \"x\": 2241.8126847430635,\n" +
				"          \"y\": 1345.6855292792793\n" +
				"        },\n" +
				"        \"shape\": {\n" +
				"          \"type\": \"circle\",\n" +
				"          \"radius\": 100\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"checkpoint\",\n" +
				"        \"position\": {\n" +
				"          \"x\": 1132.5943610732181,\n" +
				"          \"y\": 2342.236768018018\n" +
				"        },\n" +
				"        \"shape\": {\n" +
				"          \"type\": \"circle\",\n" +
				"          \"radius\": \"80\"\n" +
				"        }\n" +
				"      }\n" +
				"    ]\n" +
				"  },\n" +
				"  \"ship\": {\n" +
				"    \"name\": \"Péquod\",\n" +
				"    \"life\": 300,\n" +
				"    \"deck\": {\n" +
				"      \"length\": 4,\n" +
				"      \"width\": 3\n" +
				"    },\n" +
				"    \"position\": {\n" +
				"      \"x\": 2185.3061761027725,\n" +
				"      \"y\": -227.86458333333408,\n" +
				"      \"orientation\": -1.5582496193148399\n" +
				"    },\n" +
				"    \"entities\": [\n" +
				"      {\n" +
				"        \"type\": \"oar\",\n" +
				"        \"x\": 0,\n" +
				"        \"y\": 0\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"oar\",\n" +
				"        \"x\": 0,\n" +
				"        \"y\": 2\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"oar\",\n" +
				"        \"x\": 1,\n" +
				"        \"y\": 0\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"oar\",\n" +
				"        \"x\": 1,\n" +
				"        \"y\": 2\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"oar\",\n" +
				"        \"x\": 2,\n" +
				"        \"y\": 0\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"oar\",\n" +
				"        \"x\": 2,\n" +
				"        \"y\": 2\n" +
				"      },\n" +
				"      {\n" +
				"        \"type\": \"rudder\",\n" +
				"        \"x\": 3,\n" +
				"        \"y\": 1\n" +
				"      }\n" +
				"    ]\n" +
				"  },\n" +
				"  \"startingPositions\": [\n" +
				"    {\n" +
				"      \"x\": 2185.3061761027725,\n" +
				"      \"y\": -227.86458333333408,\n" +
				"      \"orientation\": 1.5882496193148399\n" +
				"    }\n" +
				"  ],\n" +
				"  \"wind\": {\n" +
				"    \"direction\": 0,\n" +
				"    \"strength\": 0\n" +
				"  },\n" +
				"  \"sailors\": [\n" +
				"    {\n" +
				"      \"x\": 0,\n" +
				"      \"y\": 0,\n" +
				"      \"id\": 0,\n" +
				"      \"name\": \"Jack Teach\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"x\": 0,\n" +
				"      \"y\": 1,\n" +
				"      \"id\": 1,\n" +
				"      \"name\": \"Jack Teach\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"x\": 1,\n" +
				"      \"y\": 0,\n" +
				"      \"id\": 8,\n" +
				"      \"name\": \"Jack Pouce\"\n" +
				"    },\n" +
				" 	{\n" +
				"      \"x\": 1,\n" +
				"      \"y\": 0,\n" +
				"      \"id\": 9,\n" +
				"      \"name\": \"Jack Pouce\"\n" +
				"    },\n" +
				"	{\n" +
				"      \"x\": 1,\n" +
				"      \"y\": 0,\n" +
				"      \"id\": 10,\n" +
				"      \"name\": \"Jack Pouce\"\n" +
				"    },\n" +
				"	{\n" +
				"      \"x\": 1,\n" +
				"      \"y\": 0,\n" +
				"      \"id\": 11,\n" +
				"      \"name\": \"Jack Pouce\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"x\": 1,\n" +
				"      \"y\": 1,\n" +
				"      \"id\": 3,\n" +
				"      \"name\": \"Luffy Pouce\"\n" +
				"    }\n" +
				"  ],\n" +
				"  \"seaEntities\": [],\n" +
				"  \"maxRound\": 300,\n" +
				"  \"minumumCrewSize\": 5,\n" +
				"  \"maximumCrewSize\": 5\n" +
				"}";
		Game game = new Game(jsonWeek4);
		StringBuilder textForOutput = new StringBuilder(game.getAllCheckpointsForOutput() + "---\n");
		int tour=0;
		while (!game.isFinished()) {
			game.nextRound();
			logger.info(String.valueOf(game));
			textForOutput.append(game);
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
