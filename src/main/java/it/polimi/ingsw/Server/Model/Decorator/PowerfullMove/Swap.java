package it.polimi.ingsw.Server.Model.Decorator.PowerfullMove;

import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

/**
 * This class describes the effect of the divinity Apollo over a player Turn
 */

// APOLLO
public class Swap extends PowerfullMove {

    /**
     * This method builds an object with static type Turn and dynamic type Swap
     * @param turn it is a generic turn to be decorated
     */
    public Swap(Turn turn){
        super(turn);
    }



    /**
     * This method allows a worker to swap its position if there is a worker sitting in the position newPos
     * @param newPos is the new position to which we want to move worker
     * @param board is the board of the game with all info needed to decide if a move is possible
     * @param worker is the worker meant to be moved to new pos
     * @return True if the movement is allowed False otherwise
     */
    @Override
    public boolean checkPossibleMove(Position newPos, Board board ,Worker worker) {
        // se è una mossa normale oppure se è una mossa valida per Apollo
        if (super.checkPossibleMove(newPos, board, worker) || (board.isNear(worker.getPosition(), newPos) &&
                worker.getMoveNum() == 0 && !newPos.hasDome() && worker.getPosition().getLevel() - newPos.getLevel() > -2 &&
                !newPos.getOccupiedBy().getColour().equals(worker.getColour())))
            return true;
        return false;
    }

    /**
     * This method is used in case a swap has to be done; it keeps track of the position to which the swapped worker will be moved
     * @param oldPos is the position of worker (that of checkPossibleMove() ) before the move
     * @param newPos is the position of worker (that of checkPossibleMove() ) after the move
     * @param board is the board of the game
     * @return the position to which the swapped worker will be moved
     */
    @Override
    public Position calculateMove(Position oldPos, Position newPos, Board board) {
        return oldPos;
    }
}