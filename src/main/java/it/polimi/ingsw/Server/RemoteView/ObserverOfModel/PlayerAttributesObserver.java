package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

import it.polimi.ingsw.shared.Colour;

/**
 * This Observer is called when a player is added to the game (during the initialization),
 * when a player choose it's card,
 * when the challenger is chosen and
 * when the first player is chosen
 */

public interface PlayerAttributesObserver {
    public void onPlayerAttributesUpdate(String nickname, int age, Colour workersColour, String divinity);
}
