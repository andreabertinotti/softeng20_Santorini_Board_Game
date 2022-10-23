package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;



import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;

public class EventUpdateOnTurn extends EventClientUpdate implements Serializable {
    private String nickname;
    private int age;
    private String whatToDo;

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public String getWhatToDo() {
        return whatToDo;
    }

    public EventUpdateOnTurn(String nickname, int age, String whatToDo){
        this.nickname = nickname;
        this.age = age;
        this.whatToDo = whatToDo;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
