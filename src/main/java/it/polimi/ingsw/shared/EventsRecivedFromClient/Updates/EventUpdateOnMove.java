package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;

import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;
import it.polimi.ingsw.shared.Colour;

import java.io.Serializable;

public class EventUpdateOnMove extends EventClientUpdate implements Serializable {
    private int x;
    private int y;
    private int ID;
    private Colour colour;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getID() {
        return ID;
    }

    public Colour getColour() {
        return colour;
    }

    public EventUpdateOnMove(int x, int y, int ID, Colour colour){
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.colour = colour;
    }


    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
