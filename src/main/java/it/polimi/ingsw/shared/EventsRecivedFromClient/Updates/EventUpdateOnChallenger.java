package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;


import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class EventUpdateOnChallenger extends EventClientUpdate implements Serializable {
    private String nickname;
    private int age;
    private ArrayList<String> deck;

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<String> getDeck() {
        return deck;
    }

    public EventUpdateOnChallenger(String nickname, int age, ArrayList<String> deck){
        this.nickname = nickname;
        this.age = age;
        this.deck = deck;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
