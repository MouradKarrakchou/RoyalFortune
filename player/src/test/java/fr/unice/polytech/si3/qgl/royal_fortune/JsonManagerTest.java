package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.dao.InitGameDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.dao.NextRoundDAO;
import fr.unice.polytech.si3.qgl.royal_fortune.json_management.JsonManager;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonManagerTest {

    @BeforeEach
    void init(){

    }

    @Test
    void readJsonTest() {
        String json ="""
            {
                "type": "ship",
                "life": 100,
                "position": {
                "x": 10,
                "y": 20,
                "orientation": 0
                },
                "name": "Boat test"
            }""";
        Ship ship = JsonManager.readShipJson(json);

        assertEquals("ship", ship.getType());
        assertEquals(100, ship.getLife());
        assertEquals(new Position(10, 20, 0), ship.getPosition());
        assertEquals("Boat test", ship.getName());
    }

    @Test
    void getNodeTest() {
        String json = "{\n" +
                "  \"goal\": {\n" +
                "    \"mode\": \"REGATTA\",\n" +
                "    \"checkpoints\": [\n" +
                "      {\n" +
                "        \"position\": {\n" +
                "          \"x\": 1000,\n" +
                "          \"y\": 0,\n" +
                "          \"orientation\": 0\n" +
                "        },\n" +
                "        \"shape\": {\n" +
                "          \"type\": \"circle\",\n" +
                "          \"radius\": 50\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"position\": {\n" +
                "          \"x\": 0,\n" +
                "          \"y\": 0,\n" +
                "          \"orientation\": 0\n" +
                "        },\n" +
                "        \"shape\": {\n" +
                "          \"type\": \"circle\",\n" +
                "          \"radius\": 50\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"ship\": {\n" +
                "    \"type\": \"ship\",\n" +
                "    \"life\": 100,\n" +
                "    \"position\": {\n" +
                "      \"x\": 0,\n" +
                "      \"y\": 0,\n" +
                "      \"orientation\": 0\n" +
                "    },\n" +
                "    \"name\": \"Les copaings d'abord!\",\n" +
                "    \"deck\": {\n" +
                "      \"width\": 3,\n" +
                "      \"length\": 6\n" +
                "    },\n" +
                "    \"entities\": [\n" +
                "      {\n" +
                "        \"x\": 1,\n" +
                "        \"y\": 0,\n" +
                "        \"type\": \"oar\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"x\": 1,\n" +
                "        \"y\": 2,\n" +
                "        \"type\": \"oar\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"x\": 3,\n" +
                "        \"y\": 0,\n" +
                "        \"type\": \"oar\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"x\": 3,\n" +
                "        \"y\": 2,\n" +
                "        \"type\": \"oar\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"x\": 4,\n" +
                "        \"y\": 0,\n" +
                "        \"type\": \"oar\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"x\": 4,\n" +
                "        \"y\": 2,\n" +
                "        \"type\": \"oar\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"x\": 2,\n" +
                "        \"y\": 1,\n" +
                "        \"type\": \"sail\",\n" +
                "        \"openned\": false\n" +
                "      },\n" +
                "      {\n" +
                "        \"x\": 5,\n" +
                "        \"y\": 0,\n" +
                "        \"type\": \"rudder\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"shape\": {\n" +
                "      \"type\": \"rectangle\",\n" +
                "      \"width\": 3,\n" +
                "      \"height\": 6,\n" +
                "      \"orientation\": 0\n" +
                "    }\n" +
                "  },\n" +
                "  \"sailors\": [\n" +
                "    {\n" +
                "      \"x\": 0,\n" +
                "      \"y\": 0,\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"Edward Teach\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"x\": 0,\n" +
                "      \"y\": 1,\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Edward Pouce\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"x\": 0,\n" +
                "      \"y\": 2,\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"Tom Pouce\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"x\": 1,\n" +
                "      \"y\": 0,\n" +
                "      \"id\": 3,\n" +
                "      \"name\": \"Jack Teach\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"x\": 1,\n" +
                "      \"y\": 1,\n" +
                "      \"id\": 4,\n" +
                "      \"name\": \"Jack Teach\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"x\": 1,\n" +
                "      \"y\": 2,\n" +
                "      \"id\": 5,\n" +
                "      \"name\": \"Tom Pouce\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"shipCount\": 1\n" +
                "}";

        String result = "{\"type\":\"ship\",\"life\":100,\"position\":{\"x\":0,\"y\":0,\"orientation\":0},\"name\":\"Les copaings d'abord!\",\"deck\":{\"width\":3,\"length\":6},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":2,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"sail\",\"openned\":false},{\"x\":5,\"y\":0,\"type\":\"rudder\"}],\"shape\":{\"type\":\"rectangle\",\"width\":3,\"height\":6,\"orientation\":0}}";

//        String result = "100";

        assertEquals(result, JsonManager.getNode(json, "ship"));
    }

    
    @Test
    void readInitGameJSON() {
    	String json = "{\r\n"
    			+ "  \"goal\": {\r\n"
    			+ "    \"mode\": \"REGATTA\",\r\n"
    			+ "    \"checkpoints\": [\r\n"
    			+ "      {\r\n"
    			+ "        \"position\": {\r\n"
    			+ "          \"x\": -200,\r\n"
    			+ "          \"y\": 1000,\r\n"
    			+ "          \"orientation\": 0\r\n"
    			+ "        },\r\n"
    			+ "        \"shape\": {\r\n"
    			+ "          \"type\": \"circle\",\r\n"
    			+ "          \"radius\": 80\r\n"
    			+ "        }\r\n"
    			+ "      }\r\n"
    			+ "    ]\r\n"
    			+ "  },\r\n"
    			+ "  \"ship\": {\r\n"
    			+ "    \"type\": \"ship\",\r\n"
    			+ "    \"position\": {\r\n"
    			+ "      \"x\": 0,\r\n"
    			+ "      \"y\": 0,\r\n"
    			+ "      \"orientation\": 0\r\n"
    			+ "    },\r\n"
    			+ "    \"name\": \"royal_fortune\",\r\n"
    			+ "    \"deck\": {\r\n"
    			+ "      \"width\": 2,\r\n"
    			+ "      \"length\": 3\r\n"
    			+ "    },\r\n"
    			+ "    \"entities\": [\r\n"
    			+ "      {\r\n"
    			+ "        \"x\": 1,\r\n"
    			+ "        \"y\": 0,\r\n"
    			+ "        \"type\": \"oar\"\r\n"
    			+ "      },\r\n"
    			+ "      {\r\n"
    			+ "        \"x\": 1,\r\n"
    			+ "        \"y\": 1,\r\n"
    			+ "        \"type\": \"oar\"\r\n"
    			+ "      },\r\n"
    			+ "      {\r\n"
    			+ "        \"x\": 2,\r\n"
    			+ "        \"y\": 0,\r\n"
    			+ "        \"type\": \"oar\"\r\n"
    			+ "      },\r\n"
    			+ "      {\r\n"
    			+ "        \"x\": 2,\r\n"
    			+ "        \"y\": 1,\r\n"
    			+ "        \"type\": \"oar\"\r\n"
    			+ "      }\r\n"
    			+ "    ],\r\n"
    			+ "    \"life\": 300,\r\n"
    			+ "    \"shape\": {\r\n"
    			+ "      \"type\": \"rectangle\",\r\n"
    			+ "      \"width\": 2,\r\n"
    			+ "      \"height\": 3,\r\n"
    			+ "      \"orientation\": 0\r\n"
    			+ "    }\r\n"
    			+ "  },\r\n"
    			+ "  \"sailors\": [\r\n"
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
    			+ "  ],\r\n"
    			+ "  \"shipCount\": 1\r\n"
    			+ "}";
    	InitGameDAO init;
    	init = JsonManager.readInitGameDAOJson(json);
    	assertEquals(1, init.getShipCount());
    	assertEquals(4, init.getSailors().size());
    	assertEquals(4, init.getShip().getEntities().size());
    }

    @Test
    void readNextRoundJSON() {
    	String json = "{\r\n"
    			+ "  \"ship\": {\r\n"
    			+ "    \"type\": \"ship\",\r\n"
    			+ "    \"life\": 300,\r\n"
    			+ "    \"position\": {\r\n"
    			+ "      \"x\": 0,\r\n"
    			+ "      \"y\": 0,\r\n"
    			+ "      \"orientation\": 0\r\n"
    			+ "    },\r\n"
    			+ "    \"name\": \"royal_fortune\",\r\n"
    			+ "    \"deck\": {\r\n"
    			+ "      \"width\": 2,\r\n"
    			+ "      \"length\": 3\r\n"
    			+ "    },\r\n"
    			+ "    \"entities\": [\r\n"
    			+ "      {\r\n"
    			+ "        \"type\": \"oar\",\r\n"
    			+ "        \"x\": 1,\r\n"
    			+ "        \"y\": 0\r\n"
    			+ "      },\r\n"
    			+ "      {\r\n"
    			+ "        \"type\": \"oar\",\r\n"
    			+ "        \"x\": 1,\r\n"
    			+ "        \"y\": 1\r\n"
    			+ "      },\r\n"
    			+ "      {\r\n"
    			+ "        \"type\": \"oar\",\r\n"
    			+ "        \"x\": 2,\r\n"
    			+ "        \"y\": 0\r\n"
    			+ "      },\r\n"
    			+ "      {\r\n"
    			+ "        \"type\": \"oar\",\r\n"
    			+ "        \"x\": 2,\r\n"
    			+ "        \"y\": 1\r\n"
    			+ "      }\r\n"
    			+ "    ],\r\n"
    			+ "    \"shape\": {\r\n"
    			+ "      \"type\": \"rectangle\",\r\n"
    			+ "      \"width\": 2,\r\n"
    			+ "      \"height\": 3,\r\n"
    			+ "      \"orientation\": 0\r\n"
    			+ "    }\r\n"
    			+ "  }\r\n"
    			+ "}";
    	NextRoundDAO nextRound;
    	nextRound = JsonManager.readNextRoundDAOJson(json);
    	assertEquals(4, nextRound.getShip().getEntities().size());
    }

}
