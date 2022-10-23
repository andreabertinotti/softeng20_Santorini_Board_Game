package it.polimi.ingsw.shared.EventRecivedFromServer.Request;


import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;

public class EventAddPlayerRequest extends EventServerRequest implements Serializable {

    private String nickname;
    private int clientID;
    private int age;

    public int getClientID() {
        return clientID;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public EventAddPlayerRequest(String nickname, int clientID, int age) {
        this.nickname = nickname;
        this.clientID = clientID;
        this.age = age;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
