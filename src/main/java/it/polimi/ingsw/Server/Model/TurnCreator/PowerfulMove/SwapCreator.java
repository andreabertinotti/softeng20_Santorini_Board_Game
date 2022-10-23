package it.polimi.ingsw.Server.Model.TurnCreator.PowerfulMove;

import it.polimi.ingsw.Server.Model.Decorator.PowerfullMove.Swap;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

public class SwapCreator implements Effect {

    /**
     * This class creates the constructor for the turn related to Apollo's ability
     * @return is the decorated turn
     */

    @Override
    public Turn getTurn(){

        Turn turn = new SimpleTurn();
        Turn decorated = new Swap(turn);

        return decorated;
    };

}
