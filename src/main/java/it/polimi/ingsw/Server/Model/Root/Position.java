package it.polimi.ingsw.Server.Model.Root;

/**
 * this class represent a single position of game board
 */
public class Position {
    /**
     * this attributes are the coordinates on the board, like a two-dimensional array
     */
    private int coordX;
    private int coordY;
    /**
     * this attribute is the of the tower built
     */
    private int level;
    /**
     * this attribute is the maximum level of the blocks, after that you can build only a dome
     */
    private static final int maxLevel = 3;
    /**
     * this attribute is used to know if the tower is complete with a dome
     */
    private boolean dome;
    /**
     * this attribute is the worker who occupies the position, it's null if the position is empty
     */
    private Worker occupiedBy;


    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static int getMaxLevel() {
        return maxLevel;
    }

    public boolean hasDome() {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }

    public Worker getOccupiedBy() {
        return occupiedBy;
    }

    public void setOccupiedBy(Worker occupedBy) {
        this.occupiedBy = occupedBy;
    }

    public boolean isEmpty() {
        return getOccupiedBy() == null;
    }


    /**
     * this method create a single position and initialized its with default values of new game
     * @param x is the coordinate of the row
     * @param y is the coordinate of the column
     */
    public Position(int x, int y) {
        this.coordX = x;
        this.coordY = y;
        this.level = 0;
        this.setDome(false);
        this.setOccupiedBy(null);
    }
}