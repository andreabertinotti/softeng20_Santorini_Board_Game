package it.polimi.ingsw.Server.Model.Decorator.WinCondition;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.*;

/**
 * This class describes the effect of the divinity Athena over a player Turn
 */

//ATHENA
public class BlockOpponents extends TurnDecorator {

    /**
     * This method builds an object with static type Turn and dynamic type BlockOpponents
     * @param turn it is a generic turn to be decorated
     */
    public BlockOpponents(Turn turn) {
        super(turn);
    }

    /**
     * this method set on true the activeEffect of all player, so they can't move up in their next turn
     * @param player is the currentPlayer
     * @param board is the game board with all information
     * @param worker is the worker moved by player
     * @param newPos is the position where the player would move the worker
     * @return is the state of the move, true if it's gone successful, else false
     */
    @Override
    public boolean move(Player player, Board board, Worker worker, Position newPos) {
        if(checkPossibleMove(newPos, board, worker) &&
                newPos.getLevel() > worker.getPosition().getLevel()) {
            for (Player opponents : player.getOpponents())
                    opponents.setActiveEffect(true);
        } else {             //se la move effettuata non scatena l'effetto di atena setto il parametro acctiveEffect di tutti gli opponnets a false
            for (Player opponents : player.getOpponents())
                    opponents.setActiveEffect(false);
        }
        return super.move(player, board, worker, newPos);
    }
}