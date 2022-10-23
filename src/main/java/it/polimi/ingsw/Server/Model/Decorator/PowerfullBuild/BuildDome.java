package it.polimi.ingsw.Server.Model.Decorator.PowerfullBuild;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.*;

/**
 * This class describes the effect of the divinity Atlas over a player Turn
 */

// ATLAS
public class BuildDome extends TurnDecorator {


    /**
     * This method builds an object with static type Turn and dynamic type BuildDome
     * @param turn it is a generic turn to be decorated
     */
    public BuildDome(Turn turn) {
        super(turn);
    }


    /**
     * This method builds a dome even if the tower doesn't have three levels
     * @param player it is the currentPlayer
     * @param board is the game board with all the information
     * @param worker it is the worker that player wants to build with
     * @param buildPos it is the position in which the player wants to build with the worker
     * @param buildDome it is a boolean, true if the player wants to build a dome false otherwise
     * @return True if the construction has been done, False otherwise
     */
    @Override
    public boolean build(Player player, Board board, Worker worker, Position buildPos, boolean buildDome) {
        //se la posizione su cui si vuole costruire è vicina e ci si può costruire
        if(checkPossibleBuilding(buildPos, board, worker, player) ) {
            worker.setBuildNum(worker.getBuildNum()+1);
            if(buildDome)
                buildPos.setDome(true);
            else board.addBlock(buildPos);
            return true;                    //se la costruzione è avvenuta true
        }
        return false;                       //se la costruzione non è avvenuta false
    }
}








/* codice con parti del controller




    @Override
    public void build(Player player, Board board) {
        Worker worker;
        Position buildPos;
        do {
            worker = player.chooseWorker();
            buildPos = player.choosePosition();

            //se la posizione su cui si vuole costruire è vicina e ci si può costruire
        }while (!checkPossibleBuilding(worker.getPosition(), buildPos, board));
        buildPos.setLevel(buildPos.getLevel()+1);
        if (player.buildDome())
            buildPos.setDome(true);

    }
 */