package it.polimi.ingsw.Server.Model.TurnCreator.MultipleMoveCreator;

import it.polimi.ingsw.Server.Model.Decorator.MultipleMove.OnBorderOneMoreMove;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

public class OnBorderOneMoreMoveCreator implements Effect {

    /**
     * This class creates the constructor for the turn related to Triton's ability
     * @return is the decorated turn
     */

    @Override
    public Turn getTurn(){

        Turn turn = new SimpleTurn();
        Turn decorated = new OnBorderOneMoreMove(turn);

        return decorated;
    };

}
