package it.polimi.ingsw.shared.EventRecivedFromServer.Request;

import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;

public class EventSetCardChosenRequest extends EventServerRequest implements Serializable {

    private int clientID;
    private String identifier;
    private String cardChosen;

    public int getClientID() {
        return clientID;
    }

    public String getCardChosen() {
        return cardChosen;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EventSetCardChosenRequest(int clientID, String identifier, String cardChosen) {
        this.clientID = clientID;
        this.identifier = identifier;
        this.cardChosen = cardChosen;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
