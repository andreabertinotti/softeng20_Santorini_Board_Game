package it.polimi.ingsw.Server.Model.Decorator.PowerfullBuild;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

/**
 * This class describes the effect of the divinity Zeus over a player Turn
 */

//ZEUS (Special)
public class BuildUnderneath extends TurnDecorator {


    /**
     * This method builds an object with static type Turn and dynamic type BuildUnderneath
     * @param turn it is a generic turn to be decorated
     */
    public BuildUnderneath(Turn turn) {
        super(turn);
    }



    /**
     * This method allows a worker to build even underneath itself instead of letting it builds only in the surrounding positions
     * @param buildPos is the position on which the player wants to build
     * @param board is the board of the game with all info needed to decide if a build is possible
     * @param worker is the worker meant to build on buildPos
     * @param player it is the currentPlayer
     * @return True if the build is allowed, false otherwise
     */
    @Override
    public boolean checkPossibleBuilding(Position buildPos, Board board, Worker worker, Player player) {
        if( worker.getPosition() == buildPos  && !buildPos.hasDome()  && worker.getBuildNum() == 0) {
            player.setMyTurnOver(true);
            return true;
        }
        return super.checkPossibleBuilding(buildPos,board,worker, player);
    }
}
