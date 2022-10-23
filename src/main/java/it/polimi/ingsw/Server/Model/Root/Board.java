package it.polimi.ingsw.Server.Model.Root;

import java.util.ArrayList;

/**
 * this class represent the board composed of positions
 */
public class Board {
    /**
     * this attribute is the grid of the game board
     */
    private ArrayList<Position> stateboard;
    /**
     * this attribute is the dimension of the grid that is a square
     */
    private static final int dimension = 5;


    public ArrayList<Position> getStateboard() {
        return stateboard;
    }

    public static int getDimension() {
        return dimension;
    }


    /**
     * this method create a new board composed of position with initialize default value
     */
    public Board() {
        this.stateboard = new ArrayList<>();
        for(int i=0; i < dimension; i++)
            for (int j=0; j < dimension; j++) {
                Position position = new Position(i, j);
                stateboard.add(position);
            }
    }

    /**
     * this method search the position in the board
     * @param x is the coordinate of the row
     * @param y is the coordinate of the column
     * @return is the position found, if the position is not in the board the value is null
     */
    public Position getPosition (int x, int y){
        if ((x >= 0 && x < dimension) && (y >= 0 && y < dimension))
            for (Position position : stateboard)
                if (position.getCoordX() == x && position.getCoordY() == y)
                    return position;
        return null;
    }

    /**
     * this method calculate the difference between two position's coordinate without sign
     * @param oldP is the coordinate of old position
     * @param newP is the coordinate of new position
     * @return is the difference between the parameter without sign
     */
    public int differenceModule(int oldP, int newP) {
        int module = oldP - newP;
        if(module < 0)
            module = module*(-1);
        return module;
    }

    /**
     * this method calculate the distance between two positions used in a move
     * @param oldPos is the position left by worker
     * @param newPos is the position where the worker would like to go
     * @return is the sentence if the new position is one of eight position near the position of worker
     */
    public boolean isNear(Position oldPos, Position newPos) {
        if (stateboard.contains(newPos))
            return differenceModule(oldPos.getCoordX(), newPos.getCoordX()) <= 1 &&
                    differenceModule(oldPos.getCoordY(), newPos.getCoordY()) <= 1  &&
                    (oldPos.getCoordX()!=newPos.getCoordX() || oldPos.getCoordY()!=newPos.getCoordY());
        return false;
    }

    /**
     * this moethod create a list of eight positions near worker
     * @param worker is the worker about you want to know the eight positions near
     * @return is the list of eight positions
     */
    public ArrayList<Position> positionNearWorker(Worker worker) {
        ArrayList<Position> nearPos = new ArrayList<>();
        for (int i=0; i < dimension; i++)
            for (int j=0; j < dimension; j++)
                if (isNear(worker.getPosition(), this.getPosition(i, j)) &&
                        worker.getPosition() != this.getPosition(i, j)) {
                    nearPos.add(this.getPosition(i, j));
                }
        return nearPos;
    }

    /**
     * this method move the worker from its actual position and modify the oldPosition of worker
     * with the position left, but than the worker hasn't a position, you will need to call fillCell
     * @param worker is the worker you want to move
     */
    //libera la cella dal worker
    public void freeCell(Worker worker) {
        worker.setOldPosition(worker.getPosition());
        worker.getPosition().setOccupiedBy(null);
        worker.setPosition(null);
    }

    /**
     * this method move the worker in a new position and modify the actual position of worker,
     * but the worker need to come from another position, before this method you need to call freeCell
     * @param newPos is the position where worker will go
     * @param worker is the worker moved by player
     */
    //riempi la cella con il worker arrivato
    public void fillCell(Position newPos, Worker worker) {
        if (stateboard.contains(newPos)) {
            newPos.setOccupiedBy(worker);
            worker.setPosition(newPos);
        }
    }

    /**
     * this method is used to build, it add a block or a dome with the game rules in a position
     * @param pos is the position where add the new block
     */
    public void addBlock(Position pos) {
        if(pos.getLevel() < Position.getMaxLevel())
            pos.setLevel(pos.getLevel()+1);
        else pos.setDome(true);
    }

    /**
     * this method is the opposite of addBock, it remove a block or a dome with the game rules in a position
     * @param pos is the position where remove the block
     */
    public void removeBlock(Position pos) {
        if(!pos.hasDome() && pos.getLevel() <= Position.getMaxLevel() && pos.getLevel() > 0)
            pos.setLevel(pos.getLevel()-1);
        else if (pos.getLevel() == 3 && pos.hasDome())
            pos.setDome(false);
    }
}