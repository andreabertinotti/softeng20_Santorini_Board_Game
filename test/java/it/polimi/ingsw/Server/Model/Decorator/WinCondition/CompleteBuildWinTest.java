package it.polimi.ingsw.Server.Model.Decorator.WinCondition;

import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Worker;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompleteBuildWinTest {


    Game sceneOfGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true, parserJson, remoteView);
        game.addPlayer("Marc", 24);

        for (Player player : game.getPlayers())
            player.assignWorker(game.getPlayers().indexOf(player));
        //posizione di tutti i worker
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(2, 3));
        game.getBoard().getPosition(2, 3).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));

        game.getBoard().getPosition(2, 3).setLevel(2);
        game.getBoard().getPosition(2, 4).setLevel(3);

        game.getBoard().getPosition(0, 0).setLevel(3);
        game.getBoard().getPosition(0,0).setDome(true);
        game.getBoard().getPosition(0, 1).setLevel(3);
        game.getBoard().getPosition(0, 1).setDome(true);
        game.getBoard().getPosition(1, 0).setLevel(3);
        game.getBoard().getPosition(1, 0).setDome(true);
        game.getBoard().getPosition(1, 1).setLevel(3);
        game.getBoard().getPosition(1, 1).setDome(true);
        game.getBoard().getPosition(1, 2).setLevel(3);
        game.getBoard().getPosition(1, 2).setDome(true);


        return game;
    }


    @Test
    void checkPossibleMoveTest() {

        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new CompleteBuildWin(turn);

        Player player = game.getPlayers().get(0);
        game.setCurrentPlayer(player);
        player.setMyTurn(decoratedTurn);

        Worker worker = game.getBoard().getPosition(2, 3).getOccupiedBy();
        decoratedTurn.startTurn(game.getPlayers().get(0));


        //esecuzione di una mossa normalmente vincente
        res = decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(2, 4));
        assertEquals(true, res);
        assertEquals(true, player.getIsWinner());


    }

    @Test
    void checkPossibleMoveTest2() {

        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new CompleteBuildWin(turn);

        Player player = game.getPlayers().get(0);
        game.setCurrentPlayer(player);
        player.setMyTurn(decoratedTurn);

        Worker worker = game.getBoard().getPosition(2, 3).getOccupiedBy();
        decoratedTurn.startTurn(game.getPlayers().get(0));


        //esecuzione di una mossa su una board che ha 5 torri complete
        res = decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(1, 3));
        assertEquals(true, res);
        assertEquals(true, player.getIsWinner());


    }



    @Test
    void checkPossibleMoveTest3() {

        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new CompleteBuildWin(turn);
        game.getBoard().getPosition(1, 2).setLevel(2);
        game.getBoard().getPosition(1, 2).setDome(true);

        Player player = game.getPlayers().get(0);
        game.setCurrentPlayer(player);
        player.setMyTurn(decoratedTurn);

        Worker worker = game.getBoard().getPosition(2, 3).getOccupiedBy();
        decoratedTurn.startTurn(game.getPlayers().get(0));


        //esecuzione di una mossa su una board che ha 5 torri con dome ( non complete )
        res = decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(1, 3));
        assertEquals(true, res);
        assertEquals(false, player.getIsWinner());


    }


}
