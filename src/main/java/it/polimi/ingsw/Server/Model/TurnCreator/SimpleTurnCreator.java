package it.polimi.ingsw.Server.Model.TurnCreator;

import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;

/**
 * This class creates the constructor for the most generic type of turn
 *
 */

public class SimpleTurnCreator implements Effect {

    public Turn getTurn(){

        Turn turn = new SimpleTurn();

        return turn;
    };

}
