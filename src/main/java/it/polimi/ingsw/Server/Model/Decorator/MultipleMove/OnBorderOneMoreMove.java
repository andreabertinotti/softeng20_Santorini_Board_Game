package it.polimi.ingsw.Server.Model.Decorator.MultipleMove;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

import java.util.ArrayList;

/**
 * This class describes the effect of the divinity Triton over a player Turn
 */

//Triton (Special)
public class OnBorderOneMoreMove extends TurnDecorator {

    /**
     * This method builds an object with static type Turn and dynamic type OneBorderOneMoreMove
     * @param turn it is a generic turn to be decorated
     */
    public OnBorderOneMoreMove(Turn turn) {
        super(turn);
    }


    /**
     * this method allows the player to move one more time if the first move put worker in a
     * perimeter position, he can continue as long as worker go in a perimeter position
     * @param newPos is position where worker want to go
     * @param board is the game board with all information around the worker
     * @param worker is the worker that player move
     * @return the sentence of the move, true if it's gone successful, else false
     */
    @Override
    public boolean checkPossibleMove(Position newPos, Board board, Worker worker) {
        if (worker.getPosition().getCoordX()== board.getDimension()-1 || worker.getPosition().getCoordX()== 0 ||
                worker.getPosition().getCoordY()== board.getDimension()-1 || worker.getPosition().getCoordY()== 0) {
            worker.setMoveNum(0);
        }
        return super.checkPossibleMove(newPos, board, worker);
    }

    @Override
    public boolean isLoser(Player player, Board board) {
        for (Worker worker : player.getWorkers()) {
            int moveNum = worker.getMoveNum();
            ArrayList<Position> nextPos = board.positionNearWorker(worker);
            for (Position pos : nextPos) {
                if (player.getMyTurn().checkPossibleMove(pos, board, worker) &&
                        player.getMyTurn().effectsOnOpponents(player, pos, board, worker)) {
                    worker.setMoveNum(moveNum);
                    return false;
                }
            }
        }
        if (player.getWorkers().get(0).getMoveNum()>0 || player.getWorkers().get(1).getMoveNum()>0) {
            for (Worker worker : player.getWorkers()) {
                int buildNum = worker.getBuildNum();
                ArrayList<Position> nextPos = board.positionNearWorker(worker);
                for (Position pos : nextPos) {
                    if (player.getMyTurn().checkPossibleBuilding(pos, board, worker, player))
                        worker.setBuildNum(buildNum);
                        return false;
                }
            }
        } else if (player.getWorkers().get(0).getMoveNum()>=1 || player.getWorkers().get(1).getMoveNum()>=1 &&
                player.getWorkers().get(0).getBuildNum()>=1 || player.getWorkers().get(1).getBuildNum()>=1)
            return false;
        return true;
    }

    @Override
    public boolean checkMoveFlow(Player player, Worker worker1){
        Worker worker2;
        if ( worker1 == player.getWorkers().get(0))
            worker2 = player.getWorkers().get(1);
        else worker2 = player.getWorkers().get(0);

        if(worker2.getMoveNum() == 0) {
            return true;
        }
        return false;
    }
}
