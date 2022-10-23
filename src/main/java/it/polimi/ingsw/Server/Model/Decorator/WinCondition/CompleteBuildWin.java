package it.polimi.ingsw.Server.Model.Decorator.WinCondition;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

/**
 * This class describes the effect of the divinity Chronus over a player Turn
 */

// CHRONUS (Special)
public class CompleteBuildWin extends TurnDecorator {

    /**
     * This method builds an object with static type Turn and dynamic type CompleteBuildWin
     * @param turn it is a generic turn to be decorated
     */
    public CompleteBuildWin(Turn turn) {
        super(turn);
    }

    /**
     * this method modify the condition of winning, it allows the player to win if there are 5 tower complete on the board
     * @param oldPos is the position occupied by the worker
     * @param newPos is the position that the worker want to occupied
     * @param player is the current player
     * @param board is the board of the game with all information need
     * @return is the sentence of win, true if player win the game, else false
     */
    @Override
    public boolean isWinner(Position oldPos, Position newPos, Player player, Board board) {
        int count = 0;
        int x,y;
        if (super.isWinner(oldPos, newPos, player, board)){
            player.setWinner(true);
            return true;
        }
        for(x=0; x < board.getDimension(); x++) {
            for (y = 0; y < board.getDimension(); y++) {
                if (board.getPosition(x, y).getLevel() == 3 && board.getPosition(x, y).hasDome())
                    count++;
            }
        }
        if(count>=5) {
            player.setWinner(true);
            return true;
        }
        return false;
    }

    @Override
    public  boolean build(Player player, Board board, Worker worker, Position buildPos, boolean buildDome) {
        if (player.getMyTurn().checkPossibleBuilding(buildPos, board, worker, player) && player.getMyTurn().checkBuildFlow(player,worker)) {
            if((buildPos.getLevel()==3 && buildDome) || (!buildDome && buildPos.getLevel() < 3) ) {
                worker.setBuildNum(worker.getBuildNum() + 1);
                board.addBlock(buildPos);
                player.getMyTurn().isWinner(worker.getPosition(), buildPos, player, board);
                return true;
            }
        }return false;
    }
}
