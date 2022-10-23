package it.polimi.ingsw.Server.Model.Decorator.MultipleBuild;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

/**
 * This class describes the effect of the divinity Hestia over a player Turn
 */

//HESTIA (Special)
public class OffBorderOneMoreBuild extends TurnDecorator {

    /**
     * This method builds an object with static type Turn and dynamic type OffBorderOneMoreBuild
     * @param turn it is a generic turn to be decorated
     */
    public OffBorderOneMoreBuild(Turn turn) {
        super(turn);
    }

    /**
     * This method allows the worker to build one additional time (if the building position is valid), within the same turn, if the second build isn't on the perimeter of the board
     * @param buildPos is the position on which the player wants to build
     * @param board is the board of the game with all info needed to decide if a build is possible
     * @param worker is the worker meant to build on buildPos
     * @param player it is the currentPlayer
     * @return True if the build is allowed, false otherwise
     */
    @Override
    public boolean checkPossibleBuilding(Position buildPos, Board board, Worker worker, Player player) {
        if(worker.getBuildNum() == 0)
            return board.isNear(worker.getPosition(), buildPos) && buildPos.isEmpty() &&
                    !buildPos.hasDome() && worker.getBuildNum() == 0;
        else if(worker.getBuildNum() == 1) {
            worker.setBuildNum(0);
            if (super.checkPossibleBuilding(buildPos, board, worker, player)) {
                worker.setBuildNum(1);
                if (buildPos.getCoordX() != board.getDimension()-1 && buildPos.getCoordX() != 0 && buildPos.getCoordY() != board.getDimension()-1 && buildPos.getCoordY() != 0)
                    return true;
                else player.setMyTurnOver(false);
            }
            worker.setBuildNum(1);
        }
        return false;
    }
}