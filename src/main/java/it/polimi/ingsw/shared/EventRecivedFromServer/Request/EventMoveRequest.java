package it.polimi.ingsw.shared.EventRecivedFromServer.Request;


import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;

public class EventMoveRequest extends EventServerRequest implements Serializable {

    private int clientID;
    private String identifier;
    private int x;
    private int y;

    public int getClientID() {
        return clientID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EventMoveRequest(int clientID, String identifier, int x, int y) {
        this.clientID = clientID;
        this.identifier = identifier;
        this.x = x;
        this.y = y;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
