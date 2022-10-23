package it.polimi.ingsw.Server.Model.Decorator.PowerfullMove;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

/**
 * This class describes the effect of the divinity Minotaur over a player Turn
 */

//MINOTAUR
public class Push extends PowerfullMove {

    /**
     * This method builds an object with static type Turn and dynamic type Push
     * @param turn it is a generic turn to be decorated
     */
    public Push(Turn turn) {
        super(turn);
    }

    /**
     * This method allows a worker to occupy another worker position pushing it back (if the position the passiv worker is pushed to is free and does not have a dome)
     * @param newPos is the new position to which we want to move worker
     * @param board is the board of the game with all info needed to decide if a move is possible
     * @param worker is the worker meant to be moved to new pos
     * @return True if the movement is allowed False otherwise
     */
    @Override
    public boolean checkPossibleMove(Position newPos, Board board, Worker worker) {
        if (super.checkPossibleMove(newPos, board, worker) || (board.isNear(worker.getPosition(), newPos) &&
                worker.getMoveNum()==0 && !newPos.hasDome() && worker.getPosition().getLevel() - newPos.getLevel() > -2 &&
                !newPos.getOccupiedBy().getColour().equals(worker.getColour()) &&
                calculateMove(worker.getPosition(), newPos, board)!=null))
            return true;
        return false;
    }


    /**
     * This method is used in case of a push; it calculates the position to which the passive worker has to be pushed
     * @param oldPos is the position of worker (that of checkPossibleMove() ) before the move
     * @param newPos is the position of worker (that of checkPossibleMove() ) after the move
     * @param board is the board of the game
     * @return the position to which the pushed worker will be moved if the push is possible, returns null otherwise
     */
    @Override
    public Position calculateMove(Position oldPos, Position newPos, Board board) {
        int dirX, dirY;

        if (oldPos.getCoordX() > newPos.getCoordX())
            dirX = newPos.getCoordX()-1;
        else  dirX = newPos.getCoordX()+1;
        if (oldPos.getCoordY() > newPos.getCoordY())
            dirY = newPos.getCoordY()-1;
        else  dirY = newPos.getCoordY()+1;
        if (dirX >= 0 && dirX < board.getDimension() && dirY >= 0 && dirY < board.getDimension() &&
                board.getPosition(dirX, dirY).isEmpty() &&  !board.getPosition(dirX, dirY).hasDome() )
            return board.getPosition(dirX, dirY);
        else return null;
    }
}
