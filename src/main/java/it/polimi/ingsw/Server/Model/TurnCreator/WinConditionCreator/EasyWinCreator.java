package it.polimi.ingsw.Server.Model.TurnCreator.WinConditionCreator;

import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.WinCondition.EasyWin;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

public class EasyWinCreator implements Effect {

    /**
     * This class creates the constructor for the turn related to Pan's ability
     * @return is the decorated turn
     */

    @Override
    public Turn getTurn(){

        Turn turn = new SimpleTurn();
        Turn decorated = new EasyWin(turn);

        return decorated;
    };

}
