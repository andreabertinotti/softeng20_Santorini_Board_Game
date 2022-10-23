package it.polimi.ingsw.Server.Model.Root;

import it.polimi.ingsw.shared.Colour;

public class Worker {
    /**
     * this attribute is the ID used to differentiate the worker of same player
     */
    private int ID;
    /**
     * this attribute is the colour of player, worker with same colour are used by same player
     */
    private Colour colour;
    /**
     * this attribute is the actual position of the worker on the board
     */
    private Position position;
    /**
     * this attribute is the old position of the worker on the board, use to know where it was before the move
     */
    private Position oldPosition;
    /**
     * this attribute is the number of move made by worker, it's used to know if the worker moved during current turn
     */
    private int moveNum;
    /**
     * this attribute is the number of build made by worker, it's used to know if the worker built during current turn
     */
    private int buildNum;


    public void setMoveNum(int moveNum) {
        this.moveNum = moveNum;
    }

    public void setBuildNum(int buildNum) {
        this.buildNum = buildNum;
    }

    public int getMoveNum() {
        return moveNum;
    }

    public int getBuildNum() {
        return buildNum;
    }

    public void setOldPosition(Position oldPosition) {
        this.oldPosition = oldPosition;
    }

    public Position getOldPosition() {
        return oldPosition;
    }

    public Colour getColour() {
        return colour;
    }

    public int getID() {
        return ID;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    /**
     * this method create worker for the player
     * @param ID is number of worker, unique among the player's worker
     * @param tempID is the number of player in the list, it's used to choose the color of worker
     */
    public Worker(int ID, int tempID) {
        this.ID = ID;
        switch (tempID) {
            case 0: this.colour = Colour.GREEN;
                    break;
            case 1: this.colour = Colour.YELLOW;
                    break;
            case 2: this.colour = Colour.RED ;
                    break;
        }
        this.position = null;
    }
}