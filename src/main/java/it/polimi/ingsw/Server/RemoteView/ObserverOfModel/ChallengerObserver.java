package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

import java.util.ArrayList;

/**
 * This Observer is called whenever the challenger is chosen
 */

public interface ChallengerObserver {

    public void onChallengerChosen(String nickname, int age,  ArrayList<String> deck);
}
