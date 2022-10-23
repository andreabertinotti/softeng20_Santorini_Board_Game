package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;



import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class EventUpdateOnWorkerSelection extends EventClientUpdate implements Serializable {
    private ArrayList<Boolean> possibilities;

    public ArrayList<Boolean> getPossibilities() {
        return possibilities;
    }

    public EventUpdateOnWorkerSelection(ArrayList<Boolean> possibilities){
        this.possibilities = possibilities;
    }


    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
