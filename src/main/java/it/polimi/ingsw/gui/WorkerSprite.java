package it.polimi.ingsw.gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class WorkerSprite {

    private Image image;
    private int ID;
    //private Colour colour;
    private int positionX;
    private int positionY;
    private int width;       //costante da settare in modo fisso in base alla grandezza della cella
    private int height;      //costante da settare in modo fisso in base alla grandezza della cella

    public WorkerSprite(String image, int positionX, int positionY) {
        this.image = new Image(image);
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void update(int x, int y) {  //x e y sono le posizioni dell'array quindi 0,1,2,3,4, da tramutare in pixel
        positionX = x;
        positionY = y;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage(image, positionX, positionY);
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }
}
