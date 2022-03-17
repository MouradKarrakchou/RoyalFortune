package fr.unice.polytech.si3.qgl.royal_fortune.tooling;

import fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {

    @Test
    void isFinishedTest() {
        String json = "{\n" +
                "    \"goal\": {\n" +
                "      \"mode\": \"REGATTA\",\n" +
                "      \"checkpoints\": [\n" +
                "        {\n" +
                "          \"position\": {\n" +
                "            \"x\": 0,\n" +
                "            \"y\": 0,\n" +
                "            \"orientation\": 0\n" +
                "          },\n" +
                "          \"shape\": {\n" +
                "            \"type\": \"circle\",\n" +
                "            \"radius\": 50\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"ship\": {\"position\": {\n" +
                "        \"x\": 0,\n" +
                "        \"y\": 0,\n" +
                "        \"orientation\": 0\n" +
                "      }\n" +
                "    }\n" +
                "  }";

        Game game = new Game(json);

        assertTrue(game.isFinished());
    }

    @Test
    void isNotFinishedTest() {
        String json = "{\n" +
                "    \"goal\": {\n" +
                "      \"mode\": \"REGATTA\",\n" +
                "      \"checkpoints\": [\n" +
                "        {\n" +
                "          \"position\": {\n" +
                "            \"x\": 1000,\n" +
                "            \"y\": 0,\n" +
                "            \"orientation\": 0\n" +
                "          },\n" +
                "          \"shape\": {\n" +
                "            \"type\": \"circle\",\n" +
                "            \"radius\": 50\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"ship\": {\"position\": {\n" +
                "        \"x\": 0,\n" +
                "        \"y\": 0,\n" +
                "        \"orientation\": 0\n" +
                "      }\n" +
                "    }\n" +
                "  }";

        Game game = new Game(json);

        assertFalse(game.isFinished());
    }

}
