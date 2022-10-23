package it.polimi.ingsw.shared.EventRecivedFromServer.Request;

import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;

public class EventChallengerChoseFirstRequest extends EventServerRequest implements Serializable {

    private int clientID;
    private String identifier;
    private String firstPlayerNickname;

    public int getClientID() {
        return clientID;
    }

    public String getFirstPlayerNickname() {
        return firstPlayerNickname;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EventChallengerChoseFirstRequest(int clientID, String identifier, String firstPlayerNickname) {
        this.clientID = clientID;
        this.firstPlayerNickname = firstPlayerNickname;
        this.identifier = identifier;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
