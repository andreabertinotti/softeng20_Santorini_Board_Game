package it.polimi.ingsw.Server.Model.Root;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {

    @Test
    void PositionCostructorTest() {
        Position position = new Position(0,0);
        assertEquals(0, position.getCoordX());
        assertEquals(0, position.getCoordX());
        assertEquals(0, position.getLevel());
        //assertFalse(position.hasDome());
    }
}