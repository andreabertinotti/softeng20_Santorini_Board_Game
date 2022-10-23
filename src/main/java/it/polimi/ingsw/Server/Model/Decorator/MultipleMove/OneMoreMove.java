package it.polimi.ingsw.Server.Model.Decorator.MultipleMove;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.TurnDecorator;
import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

/**
 * This class describes the effect of the divinity Artemis over a player Turn
 */

// ARTEMIS
public class OneMoreMove extends TurnDecorator {

    /**
     * This method builds an object with static type Turn and dynamic type OneMoreMove
     * @param turn it is a generic turn to be decorated
     */
    public OneMoreMove(Turn turn){
        super(turn);
    }


    /**
     * this method allows the player to move one more time but return in the old position
     * left with the first move
     * @param newPos is position where worker want to go
     * @param board is the game board with all information around the worker
     * @param worker is the worker that player move
     * @return the sentence of the move, true if it's gone successful, else false
     */
    @Override
    public boolean checkPossibleMove(Position newPos, Board board, Worker worker) {
        if(worker.getMoveNum() == 0)
            return super.checkPossibleMove(newPos, board, worker);
        else if( worker.getMoveNum() == 1) {        //se worker ha già mosso una volta
            worker.setMoveNum(0);                   //setto temporanemaente per potere usare la super check
            if (super.checkPossibleMove(newPos, board, worker) && newPos != (worker.getOldPosition()) ) {
                worker.setMoveNum(1);               //setto nuovamente a uno l'attributo
                return true;
            }
            worker.setMoveNum(1);
        }
        return false;
    }

    @Override
    public boolean checkMoveFlow(Player player, Worker worker1){
        Worker worker2;
        if ( worker1 == player.getWorkers().get(0))
            worker2 = player.getWorkers().get(1);
        else worker2 = player.getWorkers().get(0);

        if(worker2.getMoveNum() == 0 && (worker1.getMoveNum() == 0 || worker1.getMoveNum() == 1) ) {
            return true;
        }
        return false;
    }
}























/*
metodo con parti del controller
    @Override
    public Worker move(Player player, Board board) {
        int i = 1;
        Position newPos, initPos;
        Worker worker;

        do {
            worker = player.chooseWorker();
            newPos = player.choosePosition();


            //se la posizione in cui si vuole muovere il worker è valida
        }while(!checkPossibleMove(worker.getPosition(), newPos, board) );

        initPos = worker.getPosition();

        while( i <= 2) {

            if( i == 1) {
                worker.setOldPosition(worker.getPosition());
                worker.getPosition().setOccupiedBy(null);   //tolgo worker dalla vecchia posizione
                newPos.setOccupiedBy(worker);               //metto worker nella nuova posizione
                i++;
            }

            else {
                if( !player.endMove() ) {
                    do {
                        newPos = player.choosePosition();

                    } while (!checkPossibleMove(worker.getPosition(), newPos, board) || newPos == initPos);

                    worker.setOldPosition(worker.getPosition());
                    worker.getPosition().setOccupiedBy(null);   //tolgo worker dalla vecchia posizione
                    newPos.setOccupiedBy(worker);
                    i++;
                }else break;
            }

        }

        return worker;
    }
 */
