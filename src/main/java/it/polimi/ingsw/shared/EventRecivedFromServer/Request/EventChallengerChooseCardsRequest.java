package it.polimi.ingsw.shared.EventRecivedFromServer.Request;

import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class EventChallengerChooseCardsRequest extends EventServerRequest implements Serializable {

    private int clientID;
    private String identifier;
    private ArrayList<String> chosenDeck;

    public int getClientID() {
        return clientID;
    }

    public ArrayList<String> getChosenDeck() {
        return chosenDeck;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EventChallengerChooseCardsRequest(int clientID, String identifier, ArrayList<String> chosenDeck) {
        this.clientID = clientID;
        this.chosenDeck = chosenDeck;
        this.identifier = identifier;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
