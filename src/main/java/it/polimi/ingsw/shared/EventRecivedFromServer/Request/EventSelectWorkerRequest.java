package it.polimi.ingsw.shared.EventRecivedFromServer.Request;

import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;
import it.polimi.ingsw.shared.Colour;

import java.io.Serializable;

public class EventSelectWorkerRequest extends EventServerRequest implements Serializable {

    private int clientID;
    private String identifier;
    private int ID;
    private Colour colour;

    public int getClientID() {
        return clientID;
    }

    public int getID() {
        return ID;
    }

    public Colour getColour() {
        return colour;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EventSelectWorkerRequest( int clientID, String identifier, int ID, Colour colour) {
        this.clientID = clientID;
        this.identifier = identifier;
        this.ID = ID;
        this.colour = colour;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
