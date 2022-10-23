package it.polimi.ingsw.Server.Model.Decorator;

import it.polimi.ingsw.Server.Model.Root.*;

import java.util.ArrayList;


/**
 * This class contains the method that will be override based on divinity chose
 */
public class TurnDecorator implements Turn {
    private Turn decoratedTurn;

    public TurnDecorator(Turn turn) {
        this.decoratedTurn = turn;
    }

    @Override
    public void startTurn(Player player) {
        decoratedTurn.startTurn(player);
    }

    @Override
    public boolean move(Player player, Board board, Worker worker, Position newPos) {
        return decoratedTurn.move(player,board,worker,newPos);
    }

    @Override
    public boolean checkPossibleMove(Position newPos, Board board, Worker worker) {
        return decoratedTurn.checkPossibleMove(newPos,board,worker);
    }

    @Override
    public  boolean build(Player player, Board board, Worker worker, Position buildPos,boolean buildDome) {
        return decoratedTurn.build(player,board,worker,buildPos,buildDome);
    }

    @Override
    public boolean checkPossibleBuilding(Position buildPos, Board board, Worker worker, Player player) {
        return decoratedTurn.checkPossibleBuilding(buildPos,board,worker, player);
    }

    @Override
    public boolean effectsOnOpponents(Player player, Position newPos, Board board, Worker worker) {
        return decoratedTurn.effectsOnOpponents(player,newPos,board,worker);
    }

    @Override
    public boolean isWinner(Position oldPos, Position newPos, Player player, Board board) {
        return decoratedTurn.isWinner(oldPos,newPos,player, board);
    }

    @Override
    public boolean isLoser(Player player, Board board) {
        return decoratedTurn.isLoser(player,board);
    }

    @Override
    public boolean checkMoveFlow( Player player, Worker worker){ return decoratedTurn.checkMoveFlow(player,worker);}


    @Override
    public boolean checkBuildFlow( Player player, Worker worker){ return  decoratedTurn.checkBuildFlow(player, worker);}

    @Override
    public ArrayList<Boolean> showPossibilities(Player player, Board board) {
        return decoratedTurn.showPossibilities(player,board);
    }

    @Override
    public ArrayList<Integer> showMoves(Player player, Board board) {
        return decoratedTurn.showMoves(player,board);
    }

    @Override
    public ArrayList<Integer> showBuilds(Player player, Board board) {
        return decoratedTurn.showBuilds(player,board);
    }
}