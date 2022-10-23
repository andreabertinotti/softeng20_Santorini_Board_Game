package it.polimi.ingsw.Server.Model.Decorator.MultipleBuild;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

/**
 * This class describes the effect of the divinity Hephaestus over a player Turn
 */

// HEPHAESTUS
public class SamePosOneMoreBuild extends TurnDecorator {

    /**
     * This attribute is used to keep track of the position of the first build performed within this turn
     */
    private Position oldBuildPos;


    /**
     * This method builds an object with static type Turn and dynamic type SamePosOneMoreBuild
     * @param turn it is a generic turn to be decorated
     */
    public SamePosOneMoreBuild(Turn turn) {
        super(turn);
    }


    /**
     * This method allows a worker to build twice (if willing to do so) within the same turn, if the second build is done on top of the first one and it doe not entail the construction of a dome
     * @param buildPos is the position on which the player wants to build
     * @param board is the board of the game with all info needed to decide if a build is possible
     * @param worker is the worker meant to build on buildPos
     * @param player it is the currentPlayer
     * @return True if the build is allowed, false otherwise
     */
    @Override
    public boolean checkPossibleBuilding(Position buildPos, Board board, Worker worker, Player player) {
        if(worker.getBuildNum() == 0) {
            oldBuildPos = buildPos;
            return board.isNear(worker.getPosition(), buildPos) && buildPos.isEmpty() &&
                    !buildPos.hasDome() && worker.getBuildNum() == 0;
        } else if(worker.getBuildNum() == 1) {
            worker.setBuildNum(0);
            if (oldBuildPos == buildPos && oldBuildPos.getLevel() <= 2 && super.checkPossibleBuilding(buildPos,board, worker, player)) {
                worker.setBuildNum(1);
                return true;
            }
            worker.setBuildNum(1);
        }
        player.setMyTurnOver(true);
        return false;
    }
}

























/* codice con pezzi del controller


    @Override
    public void build(Player player, Board board) {
        int i = 1;
        Position buildPos;
        Worker worker;

        do {
            worker = player.chooseWorker();
            buildPos = player.choosePosition();


            //se la posizione in cui si vuole costruire è valida
        } while (!checkPossibleBuilding(worker.getPosition(), buildPos, board));


        while (i <= 2) {

            if (i == 1) {
                buildPos.setLevel(buildPos.getLevel() + 1);
                if (buildPos.getLevel() == 3)
                    buildPos.setDome(true);

                i++;
            } else {
                if (buildPos.getLevel() < 2 && !player.endBuild()) {
                    buildPos.setLevel(buildPos.getLevel() + 1);
                    if (buildPos.getLevel() == 3)
                        buildPos.setDome(true);
                    i++;
                }else break;
            }

        }
    }
 */
