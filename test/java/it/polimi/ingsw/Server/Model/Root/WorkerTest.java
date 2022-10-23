package it.polimi.ingsw.Server.Model.Root;

import it.polimi.ingsw.shared.Colour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WorkerTest {

    @Test
    void WorkerCostructorTest() {
        Worker worker = new Worker(1,1);
        assertEquals(1, worker.getID());
        assertEquals(Colour.YELLOW, worker.getColour());
        assertNull(worker.getPosition());
    }
}
