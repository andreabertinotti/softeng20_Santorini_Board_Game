package it.polimi.ingsw.shared.EventRecivedFromServer.Request;

import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;

public class EventDisconnectionRequest extends EventServerRequest implements Serializable {
    private int clientID;

    public int getClientID() {
        return clientID;
    }


    public EventDisconnectionRequest(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
