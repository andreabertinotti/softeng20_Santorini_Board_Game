package it.polimi.ingsw.Server.Model.Decorator.MultipleBuild;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

/**
 * This class describes the effect of the divinity Poseidon over a player Turn
 */

//POSEIDON
public class OnGroundThreeBuild extends TurnDecorator {

    /**
     * This method builds an object with static type Turn and dynamic type OnGroundThreeBuild
     * @param turn it is a generic turn to be decorated
     */
    public OnGroundThreeBuild(Turn turn) {
        super(turn);
    }


    /**
     * This method allows the regular worker builds additionally allows the worker that hasn't been moved within the turn to build up to three times if it is sitting on the ground level
     * @param buildPos is the position on which the player wants to build
     * @param board is the board of the game with all info needed to decide if a build is possible
     * @param worker is the worker meant to build on buildPos
     * @param player it is the currentPlayer
     * @return True if the build is allowed, false otherwise
     */
    @Override
    public boolean checkPossibleBuilding(Position buildPos, Board board, Worker worker, Player player) {
        if(worker.getMoveNum() == 0 &&  worker.getPosition().getLevel()!=0) {
            player.setMyTurnOver(true);
            return false;
        }
        if(worker.getMoveNum() == 0  &&  worker.getBuildNum() < 3  && worker.getPosition().getLevel()==0) {
            int temp = worker.getBuildNum();
            worker.setBuildNum(0);
            if (board.isNear(worker.getPosition(), buildPos) && buildPos.isEmpty() &&
                    !buildPos.hasDome() && worker.getBuildNum() == 0) {
                worker.setBuildNum(temp);
                if (worker.getBuildNum()==2)
                    player.setMyTurnOver(true);
                return true;
            }
            worker.setBuildNum(temp);
        }
        return board.isNear(worker.getPosition(), buildPos) && buildPos.isEmpty() &&
                !buildPos.hasDome() && worker.getBuildNum() == 0;
    }

    /**
     * This method allows the usual flow of the turn and also allows worker1 to build if worker2 has previously moved and build
     * @param player is the currentPlayer
     * @param worker1 is the worker with whom the player is trying to build
     * @return true if the worker can build, false otherwise
     */
    @Override
    public boolean checkBuildFlow(Player player, Worker worker1){
        Worker worker2;
        if ( worker1 == player.getWorkers().get(0))
            worker2 = player.getWorkers().get(1);
        else worker2 = player.getWorkers().get(0);

        if (worker2.getMoveNum() == 1 && worker2.getBuildNum() == 1)
        return true;

        else return super.checkBuildFlow(player, worker1);
    }
}
