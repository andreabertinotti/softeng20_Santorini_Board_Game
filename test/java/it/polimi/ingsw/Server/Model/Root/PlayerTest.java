package it.polimi.ingsw.Server.Model.Root;

import it.polimi.ingsw.shared.Colour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerTest {

    @Test
    void PlayerCostructorTest() {
        Player player = new Player("Marc",25);

        assertEquals("Marc", player.getNickname());
        assertEquals(25, player.getAge());
        //assertFalse(player.isChallenger());
        assertNull(player.getCard());
        //assertFalse(player.getIsWinner());
    }

    @Test
    void assignWorkerTest() {
        Player player = new Player("Marc",25);

        player.assignWorker(0);
        assertEquals(2, player.getWorkers().size());
        assertEquals(1, player.getWorkers().get(0).getID());
        assertEquals(2, player.getWorkers().get(1).getID());
        for (Worker worker : player.getWorkers()) {
            assertEquals(Colour.GREEN, worker.getColour());
            assertNull(worker.getPosition());
        }
    }
}