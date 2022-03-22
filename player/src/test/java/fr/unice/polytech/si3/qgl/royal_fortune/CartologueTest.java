package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Reef;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Stream;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class CartologueTest {
    Cartologue cartologue;
    List<Stream> listStream;
    List<Reef> listReef;
    Wind wind;
    @BeforeEach
    void init() {
        listStream=new ArrayList<>();
        listReef=new ArrayList<>();
        wind=new Wind();
        cartologue=new Cartologue(listStream,listReef,wind);
    }
    
}
