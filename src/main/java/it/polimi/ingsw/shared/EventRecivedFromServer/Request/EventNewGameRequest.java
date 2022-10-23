package it.polimi.ingsw.shared.EventRecivedFromServer.Request;


import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;

public class EventNewGameRequest extends EventServerRequest implements Serializable {
    private int clientID;
    private int nPlayer;
    private boolean withDivinities;

    public int getClientID() {
        return clientID;
    }

    public int getnPlayer() {
        return nPlayer;
    }

    public boolean isWithDivinities() {
        return withDivinities;
    }

    public EventNewGameRequest(int clientID, int nPlayer, boolean withDivinities) {
        this.clientID = clientID;
        this.nPlayer = nPlayer;
        this.withDivinities = withDivinities;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
