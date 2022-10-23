package it.polimi.ingsw.Server.Model.Decorator.PowerfullMove;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.*;

/**
 * This class describes the equals parts of divinities Apollo and Minotaur
 */

public class PowerfullMove extends TurnDecorator {

    /**
     * This method builds an object with static type Turn and dynamic type PowerfulMove
     * @param turn it is a generic turn to be decorated
     */
    public PowerfullMove(Turn turn) {
        super(turn);
    }


    /**
     * This method move a worker to a new position if that is an allowed move, and if the new position is valid but contains a worker performs a push or a swap depending on the dynamic type
     * @param player is the currentPlayer
     * @param board is the game board with all information
     * @param worker is the worker that player wants to move
     * @param newPos is the position to which the player wants to move the worker
     * @return True if the worker has been moved false otherwise
     */
    @Override
    public boolean move(Player player, Board board, Worker worker, Position newPos) {
        if(newPos.isEmpty()) {
            super.move(player, board, worker, newPos);
            return true;
        } else if (player.getMyTurn().checkPossibleMove(newPos, board, worker) &&
                player.getMyTurn().effectsOnOpponents(player,newPos,board,worker) &&
                player.getMyTurn().checkMoveFlow(player, worker)) {
            worker.setMoveNum(worker.getMoveNum()+1);
            Worker forcedWorker = newPos.getOccupiedBy();

            if (player.getWorkers().contains(worker))
                isWinner(worker.getPosition(), newPos, player, board);

            board.freeCell(worker);
            board.freeCell(forcedWorker);

            board.fillCell(newPos, worker);
            board.fillCell(calculateMove(worker.getOldPosition(), worker.getPosition(), board), forcedWorker);
            return true;
        }
        return false;
    }

    /**
     * This method is override in the classes Push and Swap
     * @param oldPos is the position of worker (that of checkPossibleMove() ) before the move
     * @param newPos is the position of worker (that of checkPossibleMove() ) after the move
     * @param board is the board of the game
     * @return a default value
     */
    public Position calculateMove(Position oldPos, Position newPos, Board board){ return null; }
}