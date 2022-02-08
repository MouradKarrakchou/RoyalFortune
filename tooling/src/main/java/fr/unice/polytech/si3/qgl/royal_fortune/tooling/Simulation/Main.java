package fr.unice.polytech.si3.qgl.royal_fortune.tooling.Simulation;

public class Main {
    public static void main(String[] args) {
        String json="{\"goal\":{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":-200.0,\"y\":1000.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":80.0,\"orientation\":0.0,\"vertices\":[{\"x\":80.0,\"y\":0.0},{\"x\":73.91036260090294,\"y\":30.614674589207183},{\"x\":56.568542494923804,\"y\":56.5685424949238},{\"x\":30.614674589207187,\"y\":73.91036260090294},{\"x\":4.898587196589413E-15,\"y\":80.0},{\"x\":-30.614674589207176,\"y\":73.91036260090294},{\"x\":-56.5685424949238,\"y\":56.568542494923804},{\"x\":-73.91036260090294,\"y\":30.61467458920719},{\"x\":-80.0,\"y\":9.797174393178826E-15},{\"x\":-73.91036260090294,\"y\":-30.614674589207173},{\"x\":-56.56854249492382,\"y\":-56.5685424949238},{\"x\":-30.614674589207226,\"y\":-73.91036260090291},{\"x\":-1.4695761589768237E-14,\"y\":-80.0},{\"x\":30.6146745892072,\"y\":-73.91036260090293},{\"x\":56.56854249492379,\"y\":-56.56854249492382},{\"x\":73.91036260090291,\"y\":-30.614674589207233}]}}]},\"ship\":{\"name\":\"Moby Dick\",\"deck\":{\"width\":2,\"length\":4},\"entities\":[{\"type\":\"oar\",\"x\":1,\"y\":0},{\"type\":\"oar\",\"x\":1,\"y\":1},{\"type\":\"oar\",\"x\":2,\"y\":0},{\"type\":\"oar\",\"x\":2,\"y\":1},{\"type\":\"oar\",\"x\":3,\"y\":0},{\"type\":\"oar\",\"x\":3,\"y\":1}],\"type\":\"ship\",\"position\":{\"x\":0.0,\"y\":0.0,\"orientation\":0.0},\"shape\":{\"type\":\"rectangle\",\"width\":2.0,\"height\":4.0,\"orientation\":0.0,\"vertices\":[{\"x\":-2.0,\"y\":-1.0},{\"x\":2.0,\"y\":-1.0},{\"x\":2.0,\"y\":1.0},{\"x\":-2.0,\"y\":1.0}]},\"life\":300},\"sailors\":[{\"id\":0,\"x\":1,\"y\":0,\"name\":\"Marin0\"},{\"id\":1,\"x\":2,\"y\":1,\"name\":\"Marin1\"},{\"id\":2,\"x\":3,\"y\":1,\"name\":\"Marin2\"},{\"id\":3,\"x\":2,\"y\":0,\"name\":\"Marin3\"}],\"shipCount\":1}";
        Game game=new Game(json);
        game.nextRound();
        System.out.println(game);
        game.nextRound();
        System.out.println(game);
        game.nextRound();
        System.out.println(game);
        game.nextRound();
        System.out.println(game);
        game.nextRound();
        System.out.println(game);



    }
}
