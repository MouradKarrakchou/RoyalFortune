package fr.unice.polytech.si3.qgl.royal_fortune;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrewTest { /*
    private Ship basicShip;
    private Captain captain;
    private List<Checkpoint> checkpoints;
    private List<Sailor> sailors;
    private List<Entities> entities;
    private Crew crew;
    Goal goal;
    Associations associations = new Associations();

    @BeforeEach
    void init(){
        sailors = new ArrayList<>();
        entities = new ArrayList<>();
        checkpoints=new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(5000,5000,0),new Circle("",100)));
        goal=new Goal("",(ArrayList<Checkpoint>) checkpoints);
        basicShip = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));
        captain=new Captain(basicShip,sailors,goal,new FictitiousCheckpoint(checkpoints), new Wind(0,0));
        crew=captain.getCrew();
    }
    @Test
    void associateSailorToOar(){
        List<Entities> oarLeft = new ArrayList<>();
        List<Entities> oarRight=new ArrayList<>();
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));

        // Left oars
        oarLeft.add(new Oar(0, 0));
        oarLeft.add(new Oar(1, 0));

        // Right oars
        oarRight.add(new Oar(0, 3));
        oarRight.add(new Oar(1, 3));

        entities.addAll(oarLeft);
        entities.addAll(oarRight);

        crew.associateSailorToOar(1, DirectionsManager.LEFT);
        assertEquals(1,crew.getListOfUnassignedSailors().size());
    }
    @Test
    void associateSailorToOarRightTest(){
        List<Entities> oarLeft = new ArrayList<>();
        List<Entities> oarRight=new ArrayList<>();
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));

        // Left oars
        oarLeft.add(new Oar(0, 0));
        oarLeft.add(new Oar(1, 0));

        // Right oars
        oarRight.add(new Oar(0, 3));
        oarRight.add(new Oar(1, 3));

        entities.addAll(oarLeft);
        entities.addAll(oarRight);

        crew.associateSailorToOar(2, DirectionsManager.RIGHT);
        assertEquals(0,crew.getListOfUnassignedSailors().size());
        for (Sailor sailor : sailors){
            assertEquals(true,oarRight.contains(crew.getAssociations().getAssociatedEntity(sailor)));
        }
    }
    @Test
    void associateSailorToOarLeftTest(){
        List<Entities> oarLeft = new ArrayList<>();
        List<Entities> oarRight = new ArrayList<>();
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));

        // Left oars
        oarLeft.add(new Oar(0, 0));
        oarLeft.add(new Oar(1, 0));

        // Right oars
        oarRight.add(new Oar(0, 3));
        oarRight.add(new Oar(1, 3));

        entities.addAll(oarLeft);
        entities.addAll(oarRight);


        crew.associateSailorToOar(2, DirectionsManager.LEFT);
        assertEquals(0,crew.getListOfUnassignedSailors().size());
        for (Sailor sailor : sailors){
            assertEquals(true,oarLeft.contains(crew.getAssociations().getAssociatedEntity(sailor)));
        }
    }
    @Test
    void associateSailorToOarEvenlyTest(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        // Left oars
        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));

        // Right oars
        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));

        //Sail
        entities.add(new Sail(3, 3, false));

        crew.associateSailorToOarEvenly();
        assertEquals(0,crew.getListOfUnassignedSailors().size());
        for (Sailor sailor : sailors){
            assertTrue(entities.contains(crew.getAssociations().getAssociatedEntity(sailor)));
        }
        for (Entities oar : entities){
            assertTrue(sailors.contains(crew.getAssociations().getAssociatedSailor(oar)));
        }
    }
    @Test
    void associateSailorToOarEvenlyTest2(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));
        assertEquals(4,crew.getListOfUnassignedSailors().size());
        // Left oars
        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));

        // Right oars
        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));

        entities.add(new Sail(3, 3, false));

        crew.getAssociations().addAssociation(sailors.get(0), entities.get(0));

        crew.associateSailorToOarEvenly();
        assertEquals(1,crew.getListOfUnassignedSailors().size());

    }
    @Test
    void sailorToMoveToRodderTest(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        assertEquals(new ArrayList<>(),crew.sailorMoveToRudder());
        entities.add(new Rudder(0, 0));
        entities.add(new Oar(1, 0));

        associations.addAssociation(sailors.get(0), entities.get(1));

        crew.sailorMoveToRudder();
        assertEquals(0,crew.getListOfUnassignedSailors().size());
        assertEquals(entities.get(1),associations.getAssociatedEntity(sailors.get(0)));
    }
    @Test
    void sailorToOarTest(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        entities.add(new Rudder(0, 0));

        associations.addAssociation(sailors.get(0), entities.get(0));

        assertEquals("[]",crew.sailorsOar().toString());
    }
    @Test
    void sailorToTurnWithRudderTest(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        entities.add(new Rudder(0, 0));
        assertEquals("[]",crew.sailorsTurnWithRudder(20).toString());

        crew.getAssociations().addAssociation(sailors.get(0), entities.get(0));

        assertEquals("[{\"sailorId\":0,\"type\":\"TURN\",\"rotation\":20.0}]",crew.sailorsTurnWithRudder(20).toString());
    }
    @Test
    void sailorToTurnWithRudderTest2(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        entities.add(new Oar(1, 0));
        entities.add(new Rudder(0, 0));

        assertEquals(new ArrayList<>(),crew.sailorsTurnWithRudder(20));}
        */
}
