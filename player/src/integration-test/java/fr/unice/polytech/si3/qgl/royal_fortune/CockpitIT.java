package fr.unice.polytech.si3.qgl.royal_fortune;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CockpitIT {

    Cockpit cockpit;

    @BeforeEach
    void setUp() {
        this.cockpit = new Cockpit();
    }

    @Test
    void test(){
        assertEquals(true, true);
    }

    @Test
    void test2(){
        assertEquals(true, true);
    }
}