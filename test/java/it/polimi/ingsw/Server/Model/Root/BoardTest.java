package it.polimi.ingsw.Server.Model.Root;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BoardTest {

    @Test
    void BoardCostructorTest() {
        Board board = new Board();

        for (int i = 0; i < board.getDimension(); i++)
            for (int j = 0; j < board.getDimension(); j++) {
                assertEquals(i, board.getPosition(i, j).getCoordX());
                assertEquals(j, board.getPosition(i, j).getCoordY());
                assertEquals(0, board.getPosition(i, j).getLevel());
                //assertEquals(false, board.getPosition(i, j).hasDome();
            }
    }

    @Test
    void getPositionTest() {
        Board board = new Board();

        assertEquals(board.getStateboard().get(0), board.getPosition(0,0));
        assertNull(board.getPosition(-1,0));
        assertNull(board.getPosition(5,5));
    }

    @Test
    void differenceModuleTest() {
        Board board = new Board();

        assertEquals(board.differenceModule(1,2), board.differenceModule(2,1));
    }

    @Test
    void isNearTest() {
        Board board = new Board();

        //assertTrue(board.isNear(board.getPosition(0, 0), board.getPosition(0, 1)));
        //assertFalse(board.isNear(board.getPosition(0, 0), board.getPosition(0, 2)));
    }

    @Test
    void positionNearWorkerTest() {
        Board board = new Board();
        Worker worker = new Worker(0,0);
        worker.setPosition(board.getPosition(2,2));

        ArrayList<Position> nearPos = board.positionNearWorker(worker);
        assertEquals(8, nearPos.size());
        for (Position pos : nearPos)
            System.out.println(pos.getCoordX() + " " + pos.getCoordY());
    }

    @Test
    void freeCellTest() {
        Board board = new Board();
        Worker worker = new Worker(1,1);
        worker.setPosition(board.getPosition(0,0));

        board.freeCell(worker);
        assertEquals(board.getPosition(0,0), worker.getOldPosition());
        assertNull(board.getPosition(0,0).getOccupiedBy());
        assertNull(worker.getPosition());
    }

    @Test
    void fillCellTest() {
        Board board = new Board();
        Worker worker = new Worker(1,1);

        board.fillCell(board.getPosition(0,0),worker);
        assertEquals(worker, board.getPosition(0,0).getOccupiedBy());
        assertEquals(board.getPosition(0,0), worker.getPosition());
    }

    @Test
    void addBlockTest() {
        Board board = new Board();

        for (int i = 1; i <= Position.getMaxLevel(); i++) {
            board.addBlock(board.getPosition(0, 0));
            assertEquals(i, board.getPosition(0, 0).getLevel());
            //assertEquals(false, board.getPosition(0, 0).hasDome());
        }
        board.addBlock(board.getPosition(0, 0));
        assertEquals(3, board.getPosition(0, 0).getLevel());
        //assertTrue(board.getPosition(0, 0).hasDome());
    }

    @Test
    void removeBlockTest() {
        Board board = new Board();

        board.getPosition(0,0).setLevel(3);
        board.getPosition(0,0).setDome(true);
        board.removeBlock(board.getPosition(0, 0));
        assertEquals(3, board.getPosition(0, 0).getLevel());
        //assertFalse(board.getPosition(0, 0).hasDome());
        for (int i=2; i > 0; i--) {
            board.removeBlock(board.getPosition(0, 0));
            assertEquals(i, board.getPosition(0, 0).getLevel());
            //assertFalse(board.getPosition(0, 0).hasDome());
        }
    }
}
