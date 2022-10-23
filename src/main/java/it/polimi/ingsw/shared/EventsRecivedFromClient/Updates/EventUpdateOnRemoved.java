package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;



import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;

public class EventUpdateOnRemoved extends EventClientUpdate implements Serializable {
    private String nickname;
    private int age;

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public EventUpdateOnRemoved(String nickname, int age){
        this.nickname = nickname;
        this.age = age;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}