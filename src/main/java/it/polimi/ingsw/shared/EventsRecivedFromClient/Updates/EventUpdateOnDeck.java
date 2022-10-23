package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;



import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class EventUpdateOnDeck extends EventClientUpdate implements Serializable {
    private ArrayList<String> cards;

    public ArrayList<String> getCards() {
        return cards;
    }

    public EventUpdateOnDeck(ArrayList<String> cards){
        this.cards = cards;
    }


    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
