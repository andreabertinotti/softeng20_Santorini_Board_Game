package it.polimi.ingsw.Server.Model.Decorator.MultipleBuild;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

import java.util.ArrayList;

/**
 * This class describes the effect of the divinity Prometheus over a player Turn
 */

//PROMETHEUS
public class OnLevelDoubleBuild extends TurnDecorator {

    /**
     * This method builds an object with static type Turn and dynamic type OnLevelDoubleBuild
     * @param turn it is a generic turn to be decorated
     */
    public OnLevelDoubleBuild(Turn turn) {
        super(turn);
    }



    /**
     * This method check if worker can be moved to newPos based on the rules without divinities adding the restriction that if that same worker has already build (within this turn) it can not go up
     * @param newPos is the new position to which we want to move worker
     * @param board is the board of the game with all info needed to decide if a move is possible
     * @param worker is the worker meant to be moved to new pos
     * @return True if the movement is allowed False otherwise
     */
    @Override
    public boolean checkPossibleMove(Position newPos, Board board, Worker worker) {
        if (worker.getBuildNum() == 1) {
            if (worker.getMoveNum() == 0)
                worker.setBuildNum(0);
            if (super.checkPossibleMove(newPos, board, worker) && worker.getPosition().getLevel() == newPos.getLevel()) {
                worker.setBuildNum(1);
                return true;
            }
            worker.setBuildNum(1);
            return false;
        }
        return super.checkPossibleMove(newPos, board, worker);
    }


    /**
     * This method allows a worker to build twice (if willing to do so) within the same turn, once before the mandatory move and once after
     * @param buildPos is the position on which the player wants to build
     * @param board is the board of the game with all info needed to decide if a build is possible
     * @param worker is the worker meant to build on buildPos
     * @param player it is the currentPlayer
     * @return True if the build is allowed, false otherwise
     */
    @Override
    public boolean checkPossibleBuilding(Position buildPos, Board board, Worker worker, Player player) {
        if (worker.getMoveNum() == 1 && worker.getBuildNum() == 1) {
            worker.setBuildNum(0);
            if (super.checkPossibleBuilding(buildPos, board, worker, player)) {
                player.setMyTurnOver(true);
                worker.setBuildNum(1);
                return true;
            }
        } else if(super.checkPossibleBuilding(buildPos, board, worker, player) ) {
            player.setMyTurnOver(false);
            return true;
        }
        return false;
    }


    /**
     * This method allows the usual flow of the turn and also allows worker1 to build not having a previous move
     * @param player is the currentPlayer
     * @param worker1 is the worker with whom the player is trying to build
     * @return true if the worker can build, false otherwise
     */
    @Override
    public boolean checkBuildFlow(Player player, Worker worker1) {
        Worker worker2;
        if (worker1 == player.getWorkers().get(0))
            worker2 = player.getWorkers().get(1);
        else worker2 = player.getWorkers().get(0);

        //worker 1 può costruire anche se non ho ancora mosso
        if (worker2.getMoveNum() == 0 && worker1.getMoveNum() == 0) {
            return true;
        } else return super.checkBuildFlow(player, worker1);

    }


    /**
     * This method controls that only one of the two worker of a player can be moved within a turn and also imposes that if there is a worker that has moved without having a previous move it has to be the one to be moved
     * @param player is the currentPlayer
     * @param worker1 is the worker that the player is trying to move
     * @return true if worker1 can be moved, false otherwise
     */
    @Override
    public boolean checkMoveFlow(Player player, Worker worker1){
        Worker worker2;
        if ( worker1 == player.getWorkers().get(0))
            worker2 = player.getWorkers().get(1);
        else worker2 = player.getWorkers().get(0);

        //worker 1 può muovere se nessuno ha già mosso
        if( worker2.getMoveNum() == 0 && worker1.getMoveNum() == 0 ) {
            if (worker2.getBuildNum() != 0)         //vuol dire che deve muovere worker 2
                return false;
            return true;
        }
        return false;
    }

    @Override
    public boolean isLoser(Player player, Board board) {
        if (player.getWorkers().get(0).getMoveNum()==0 && player.getWorkers().get(1).getMoveNum()==0) {
            for (Worker worker : player.getWorkers()) {
                ArrayList<Position> nextPos = board.positionNearWorker(worker);
                for (Position pos : nextPos) {
                    if (player.getMyTurn().checkPossibleMove(pos, board, worker) &&
                            player.getMyTurn().effectsOnOpponents(player, pos, board, worker))
                        return false;
                }
            }
        } else {
            Worker workerMoved = player.getWorkers().get(0);
            if (workerMoved.getMoveNum() == 0)
                workerMoved = player.getWorkers().get(1);
            ArrayList<Position> nextPos = board.positionNearWorker(workerMoved);
            for (Position pos : nextPos) {
                if (player.getMyTurn().checkPossibleBuilding(pos, board, workerMoved, player))
                    return false;
            }
        }
        return true;
    }
}













/* codice con parti del controller



 @Override
    public Worker move(Player player, Board board) {

        Position newPos, initPos;
        Worker worker;

        do {
            worker = player.chooseWorker();
            newPos = player.choosePosition();


            //se la posizione in cui si vuole muovere il worker è valida
        }while(!checkPossibleMove(worker.getPosition(), newPos, board) );

        initPos = worker.getPosition();

        //se non voglio far salire il worker puoi fare una build prima di costruire
        if(initPos.getLevel() <= newPos.getLevel())
            if(!player.endBuild()){

                Position buildPos;
                do {

                    buildPos = player.choosePosition();

                    //se la posizione su cui si vuole costruire è vicina e ci si può costruire
                }while (!checkPossibleBuilding(worker.getPosition(), buildPos, board));
                buildPos.setLevel(buildPos.getLevel()+1);
                if (buildPos.getLevel() == 3)
                    buildPos.setDome(true);

            }

        worker.setOldPosition(worker.getPosition());
        worker.getPosition().setOccupiedBy(null);   //tolgo worker dalla vecchia posizione
        newPos.setOccupiedBy(worker);               //metto worker nella nuova posizione


        return worker;



    }
 */