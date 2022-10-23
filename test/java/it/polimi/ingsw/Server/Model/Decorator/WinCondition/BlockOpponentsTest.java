package it.polimi.ingsw.Server.Model.Decorator.WinCondition;

import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockOpponentsTest {

    Game sceneOfGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true, parserJson,remoteView);
        game.addPlayer("Marc", 24);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);

        for (Player player : game.getPlayers())
            player.assignWorker(game.getPlayers().indexOf(player));
        //posizione di tutti i worker
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(2,3));
        game.getBoard().getPosition(2,3).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));

        //costruisce i blocchi
        game.getBoard().getPosition(1,2).setLevel(0);
        game.getBoard().getPosition(1,3).setLevel(1);
        game.getBoard().getPosition(1,4).setLevel(3);
        game.getBoard().getPosition(2,2).setLevel(3);
        game.getBoard().getPosition(2,2).setDome(true);
        game.getBoard().getPosition(2,3).setLevel(1);
        game.getBoard().getPosition(2,4).setLevel(2);

        return game;
    }

    @Test
    void moveTest() {
        Turn simpleTurn = new SimpleTurn();
        Turn decoratedTurn = new BlockOpponents(simpleTurn);

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);
        game.setCurrentPlayer(player);

        decoratedTurn.startTurn(player);
        assertEquals(true, decoratedTurn.move(player, game.getBoard(), player.getWorkers().get(0),
                game.getBoard().getPosition(1,2)));
        for (Player opponents : game.getPlayers())
            if (!opponents.equals(player))
                assertEquals(false, opponents.isActiveEffect());
        decoratedTurn.startTurn(player);
        assertEquals(true, decoratedTurn.move(player, game.getBoard(), player.getWorkers().get(0),
                game.getBoard().getPosition(1,3)));
        decoratedTurn.startTurn(player);
        assertEquals(false, decoratedTurn.move(player, game.getBoard(), player.getWorkers().get(0),
                game.getBoard().getPosition(1,4)));
        decoratedTurn.startTurn(player);
        assertEquals(true, decoratedTurn.move(player, game.getBoard(), player.getWorkers().get(0),
                game.getBoard().getPosition(2,4)));
        for (Player opponents : game.getPlayers())
            if (!opponents.equals(player))
                assertEquals(true, opponents.isActiveEffect());


        game.getBoard().addBlock(game.getBoard().getPosition(2,3));
        decoratedTurn.startTurn(player);
        assertEquals(false, decoratedTurn.move(player, game.getBoard(), player.getWorkers().get(0),
                game.getBoard().getPosition(2,2)));
    }
}
