package fr.unice.polytech.si3.qgl.royal_fortune.tooling;

import fr.unice.polytech.si3.qgl.royal_fortune.tooling.simulation.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void gameWithSeaEntities(){
        String jsonWeek6 = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":8182.608695652171,\"y\":2654.7231270358307,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":200.0}},{\"position\":{\"x\":5469.565217391305,\"y\":-1123.7785016286687,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":1500.0}},{\"position\":{\"x\":4434.7826086956475,\"y\":374.5928338762211,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":200.0}},{\"position\":{\"x\":1939.1304347826083,\"y\":5806.188925081433,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":200.0}},{\"position\":{\"x\":9573.913043478302,\"y\":5765.472312703588,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":200.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":2652.1739130434785,\"y\":2646.5798045602605,\"orientation\":0.0},\"name\":\"royal_fortune\",\"deck\":{\"width\":3,\"length\":8},\"entities\":[{\"x\":1,\"y\":1,\"type\":\"rudder\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":2,\"type\":\"oar\"},{\"x\":5,\"y\":2,\"type\":\"oar\"},{\"x\":4,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":1,\"type\":\"sail\",\"openned\":false},{\"x\":7,\"y\":2,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"}],\"life\":1200,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":8.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Luffy Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Luffy Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Luffy Teach\"},{\"x\":1,\"y\":1,\"id\":4,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":2,\"id\":5,\"name\":\"Tom Pouce\"},{\"x\":2,\"y\":0,\"id\":6,\"name\":\"Edward Pouce\"},{\"x\":2,\"y\":1,\"id\":7,\"name\":\"Edward Pouce\"},{\"x\":2,\"y\":2,\"id\":8,\"name\":\"Edward Pouce\"},{\"x\":3,\"y\":0,\"id\":9,\"name\":\"Jack Pouce\"},{\"x\":3,\"y\":1,\"id\":10,\"name\":\"Jack Pouce\"},{\"x\":3,\"y\":2,\"id\":11,\"name\":\"Edward Pouce\"}],\"shipCount\":1}";
        String jsonListSeaEntitiesWeek6 = "[{\"position\":{\"x\":8153.389030097254,\"y\":351.1753941441404,\"orientation\":0.4537856055185257},\"type\":\"reef\",\"shape\":{\"type\":\"rectangle\",\"width\":\"400\",\"height\":\"1350\"}},{\"position\":{\"x\":6186.903137789905,\"y\":2006.7567567567567,\"orientation\":-0.17453292519943295},\"type\":\"reef\",\"shape\":{\"type\":\"rectangle\",\"width\":500,\"height\":\"1750\"}},{\"position\":{\"x\":6630.286493860851,\"y\":3209.4594594594596,\"orientation\":-0.24434609527920614},\"type\":\"reef\",\"shape\":{\"type\":\"rectangle\",\"width\":500,\"height\":\"1750\"}},{\"position\":{\"x\":7174.22818123444,\"y\":1102.9701576576585,\"orientation\":0.6981317007977318},\"type\":\"reef\",\"shape\":{\"type\":\"rectangle\",\"width\":\"250\",\"height\":\"1200\"}},{\"position\":{\"x\":7409.664698840839,\"y\":701.646959459459,\"orientation\":0.7330382858376184},\"type\":\"stream\",\"shape\":{\"type\":\"rectangle\",\"width\":\"400\",\"height\":\"1000\"},\"strength\":10},{\"position\":{\"x\":6384.720327421553,\"y\":2581.0810810810804,\"orientation\":3.0019663134302466},\"type\":\"stream\",\"shape\":{\"type\":\"rectangle\",\"width\":200,\"height\":1000},\"strength\":10}]";
        Game game = new Game(jsonWeek6, jsonListSeaEntitiesWeek6);
        assertEquals(6, game.getAllSeaEntities().size());
    }

    @Test
    void gameWithOutSeaEntities(){
        String jsonWeek6 = "{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":8182.608695652171,\"y\":2654.7231270358307,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":200.0}},{\"position\":{\"x\":5469.565217391305,\"y\":-1123.7785016286687,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":1500.0}},{\"position\":{\"x\":4434.7826086956475,\"y\":374.5928338762211,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":200.0}},{\"position\":{\"x\":1939.1304347826083,\"y\":5806.188925081433,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":200.0}},{\"position\":{\"x\":9573.913043478302,\"y\":5765.472312703588,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":200.0}}]},\"ship\":{\"type\":\"ship\",\"position\":{\"x\":2652.1739130434785,\"y\":2646.5798045602605,\"orientation\":0.0},\"name\":\"royal_fortune\",\"deck\":{\"width\":3,\"length\":8},\"entities\":[{\"x\":1,\"y\":1,\"type\":\"rudder\"},{\"x\":6,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":2,\"type\":\"oar\"},{\"x\":5,\"y\":2,\"type\":\"oar\"},{\"x\":4,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":5,\"y\":0,\"type\":\"oar\"},{\"x\":6,\"y\":1,\"type\":\"sail\",\"openned\":false},{\"x\":7,\"y\":2,\"type\":\"oar\"},{\"x\":7,\"y\":0,\"type\":\"oar\"}],\"life\":1200,\"shape\":{\"type\":\"rectangle\",\"width\":3.0,\"height\":8.0,\"orientation\":0.0}},\"sailors\":[{\"x\":0,\"y\":0,\"id\":0,\"name\":\"Luffy Teach\"},{\"x\":0,\"y\":1,\"id\":1,\"name\":\"Luffy Pouce\"},{\"x\":0,\"y\":2,\"id\":2,\"name\":\"Jack Teach\"},{\"x\":1,\"y\":0,\"id\":3,\"name\":\"Luffy Teach\"},{\"x\":1,\"y\":1,\"id\":4,\"name\":\"Jack Pouce\"},{\"x\":1,\"y\":2,\"id\":5,\"name\":\"Tom Pouce\"},{\"x\":2,\"y\":0,\"id\":6,\"name\":\"Edward Pouce\"},{\"x\":2,\"y\":1,\"id\":7,\"name\":\"Edward Pouce\"},{\"x\":2,\"y\":2,\"id\":8,\"name\":\"Edward Pouce\"},{\"x\":3,\"y\":0,\"id\":9,\"name\":\"Jack Pouce\"},{\"x\":3,\"y\":1,\"id\":10,\"name\":\"Jack Pouce\"},{\"x\":3,\"y\":2,\"id\":11,\"name\":\"Edward Pouce\"}],\"shipCount\":1}";
        Game game = new Game(jsonWeek6);
        assertEquals(0, game.getAllSeaEntities().size());
    }
}
