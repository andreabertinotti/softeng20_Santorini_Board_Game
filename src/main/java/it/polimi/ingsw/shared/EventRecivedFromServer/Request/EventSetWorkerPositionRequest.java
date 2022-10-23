package it.polimi.ingsw.shared.EventRecivedFromServer.Request;



import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;

public class EventSetWorkerPositionRequest extends EventServerRequest implements Serializable {

    private int clientID;
    private String identifier;
    private int firstCoordX;
    private int firstCoordY;
    private int secondCoordX;
    private int secondCoordY;

    public int getClientID() {
        return clientID;
    }

    public int getFirstCoordX() {
        return firstCoordX;
    }

    public int getFirstCoordY() {
        return firstCoordY;
    }

    public int getSecondCoordX() {
        return secondCoordX;
    }

    public int getSecondCoordY() {
        return secondCoordY;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EventSetWorkerPositionRequest(int clientID, String identifier, int firstCoordX, int firstCoordY, int secondCoordX, int secondCoordY) {
        this.clientID = clientID;
        this.identifier = identifier;
        this.firstCoordX = firstCoordX;
        this.firstCoordY = firstCoordY;
        this.secondCoordX = secondCoordX;
        this.secondCoordY = secondCoordY;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
