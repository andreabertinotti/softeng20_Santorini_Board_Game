package it.polimi.ingsw.Server.Model.Decorator.WinCondition;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;

/**
 * This class describes the effect of the divinity Pan over a player Turn
 */

//PAN
public class EasyWin extends TurnDecorator {

    /**
     * Constructur of a turn with a dynamic Type equals to EasyWin
     * @param turn it is an object with static Type SimpleTurn
     */
    public EasyWin(Turn turn) {
        super(turn);
    }

    /**
     * this method modify the condition of winning, it allows the player to win if his worker move down two or more level
     * @param oldPos is the position left by worker
     * @param newPos is the position where the worker want to go
     * @param player is the current player
     * @param board is the board of the game with all information need
     * @return is the sentence of win, true if player win the game, else false
     */
    @Override
    public boolean isWinner(Position oldPos, Position newPos, Player player, Board board) {
        if (super.isWinner(oldPos, newPos, player,board) || oldPos.getLevel()-newPos.getLevel() >= 2) {
            player.setWinner(true);
            return true;
        }
        return false;
    }
}