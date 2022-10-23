package it.polimi.ingsw.Server.Model.Decorator;

import it.polimi.ingsw.Server.Model.Root.*;

import java.util.ArrayList;

public interface Turn {

    public void startTurn(Player player);

    public boolean move(Player player, Board board, Worker worker, Position newPos);

    public boolean checkPossibleMove(Position newPos, Board board, Worker worker);

    public boolean build(Player player, Board board, Worker worker, Position buildPos, boolean buildDome);

    public boolean checkPossibleBuilding(Position buildPos, Board board, Worker worker, Player player);

    public boolean effectsOnOpponents(Player player, Position newPos, Board board, Worker worker);

    public boolean isWinner(Position oldPos, Position newPos, Player player, Board board);

    public boolean isLoser(Player player, Board board);

    public boolean checkMoveFlow(Player player, Worker worker);

    public boolean checkBuildFlow(Player player, Worker worker);

    public ArrayList<Boolean> showPossibilities(Player player, Board board);

    public ArrayList<Integer> showMoves(Player player, Board board);

    public ArrayList<Integer> showBuilds( Player player, Board board);
}