package it.polimi.ingsw.Server.Model.Decorator.MultipleBuild;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

/**
 * This class describes the effect of the divinity Demeter over a player Turn
 */

// DEMETER
public class DifferentPosOneMoreBuild extends TurnDecorator {

    /**
     * attribute that keeps track of the first build performed within this turn
     */
    private Position oldBuildPos;

    /**
     * This method builds an object with static type Turn and dynamic type DifferentPosOneMoreBuild
     * @param turn it is a generic turn to be decorated
     */
    public DifferentPosOneMoreBuild(Turn turn) {
        super(turn);
    }


    /**
     * This method allows the worker to build one additional time (if the building position is valid) within the same turn if the second build isn't over the first one
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
        }else if( worker.getBuildNum() == 1 ) {
            worker.setBuildNum(0);
            if (super.checkPossibleBuilding(buildPos, board, worker, player) && oldBuildPos != buildPos) {        //verificare vada bene usare !=
                worker.setBuildNum(1);
                return true;
            }
            worker.setBuildNum(1);
        }
        player.setMyTurnOver(true);
        return false;
    }
}



























/* codice con parti del controller

@Override
    public void build(Player player, Board board) {
        int i = 1;
        Position firstBuild, secondBuild;
        Worker worker;

        do {
            worker = player.chooseWorker();
            firstBuild = player.choosePosition();


            //se la posizione in cui si vuole costruire è valida
        }while(!checkPossibleBuilding(worker.getPosition(), firstBuild, board) );


        while( i <= 2) {

            if( i == 1) {
                firstBuild.setLevel(firstBuild.getLevel()+1);
                if (firstBuild.getLevel() == 3)
                    firstBuild.setDome(true);

                i++;
            }

            else {
                if (!player.endBuild()) {
                    do {
                        secondBuild = player.choosePosition();

                    } while (!checkPossibleBuilding(worker.getPosition(), firstBuild, board) || firstBuild == secondBuild);

                    firstBuild.setLevel(firstBuild.getLevel() + 1);
                    if (firstBuild.getLevel() == 3)
                        firstBuild.setDome(true);
                    i++;

                }else break;
            }

        }

    }

 */