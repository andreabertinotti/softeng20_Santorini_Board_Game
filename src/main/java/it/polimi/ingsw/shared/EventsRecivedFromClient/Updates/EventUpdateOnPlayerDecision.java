package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;



import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class EventUpdateOnPlayerDecision extends EventClientUpdate implements Serializable {
    private ArrayList<Integer> validMovesPos;

    public ArrayList<Integer> getValidMovesPos() {
        return validMovesPos;
    }

    public EventUpdateOnPlayerDecision(ArrayList<Integer> validMovesPos){
        this.validMovesPos = validMovesPos;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
