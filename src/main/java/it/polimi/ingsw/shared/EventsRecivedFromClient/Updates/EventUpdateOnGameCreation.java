package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;



import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class EventUpdateOnGameCreation extends EventClientUpdate implements Serializable {
    private int nPlayer;
    private boolean withDivinities;
    private int boardDimension;
    private ArrayList<String> players;

    public int getnPlayer() {
        return nPlayer;
    }

    public boolean isWithDivinities() {
        return withDivinities;
    }

    public int getBoardDimension() {
        return boardDimension;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public EventUpdateOnGameCreation(int nPlayer, boolean withDivinities, int boardDimension, ArrayList<String> players){
        this.nPlayer = nPlayer;
        this.withDivinities = withDivinities;
        this.boardDimension = boardDimension;
        this.players = players;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
