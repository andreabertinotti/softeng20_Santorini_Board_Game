package it.polimi.ingsw.Server.Model.Decorator.PowerfullMove;

import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowerfullMoveTest {

    Game sceneOfGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true, parserJson, remoteView);
        game.addPlayer("Marc", 24);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);

        for (Player player : game.getPlayers())
            player.assignWorker(game.getPlayers().indexOf(player));
        //posizione di tutti i worker
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(2,3));
        game.getBoard().getPosition(2,3).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));
        game.getPlayers().get(0).getWorkers().get(1).setPosition(game.getBoard().getPosition(2,4));
        game.getBoard().getPosition(2,4).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(1));
        game.getPlayers().get(1).getWorkers().get(0).setPosition(game.getBoard().getPosition(1,4));
        game.getBoard().getPosition(1,4).setOccupiedBy(game.getPlayers().get(1).getWorkers().get(0));
        game.getPlayers().get(1).getWorkers().get(1).setPosition(game.getBoard().getPosition(1,2));
        game.getBoard().getPosition(1,2).setOccupiedBy(game.getPlayers().get(1).getWorkers().get(1));
        game.getPlayers().get(2).getWorkers().get(0).setPosition(game.getBoard().getPosition(3,2));
        game.getBoard().getPosition(3,2).setOccupiedBy(game.getPlayers().get(2).getWorkers().get(0));

        //costruisce i blocchi
        game.getBoard().getPosition(1,2).setLevel(2);
        game.getBoard().getPosition(1,4).setLevel(2);
        game.getBoard().getPosition(2,2).setLevel(2);
        game.getBoard().getPosition(2,3).setLevel(1);
        game.getBoard().getPosition(2,4).setLevel(1);
        game.getBoard().getPosition(3,2).setLevel(3);

        return game;
    }

    @Test
    void moveNotSwapTest() {
        Turn simpleTurn = new SimpleTurn();
        Turn turn = new PowerfullMove(simpleTurn);
        PowerfullMove decoratedTurn = new Swap(turn);

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);
        game.setCurrentPlayer(player);

        assertEquals(true, decoratedTurn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(2,2)));
    }

    @Test
    void moveSwapTest() {
        Turn simpleTurn = new SimpleTurn();
        Turn turn = new PowerfullMove(simpleTurn);
        PowerfullMove decoratedTurn = new Swap(turn);

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);
        game.setCurrentPlayer(player);

        assertEquals(true, decoratedTurn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(1,4)));
    }

    @Test
    void moveSwapTooHighTest() {
        Turn simpleTurn = new SimpleTurn();
        Turn turn = new PowerfullMove(simpleTurn);
        PowerfullMove decoratedTurn = new Swap(turn);

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);
        game.setCurrentPlayer(player);

        assertEquals(false, decoratedTurn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(3,2)));
    }

    @Test
    void moveNotPushTest() {
        Turn simpleTurn = new SimpleTurn();
        Turn turn = new PowerfullMove(simpleTurn);
        PowerfullMove decoratedTurn = new Push(turn);

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);
        game.setCurrentPlayer(player);

        assertEquals(false, decoratedTurn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(1,4)));
    }

    @Test
    void movePushTest() {
        Turn simpleTurn = new SimpleTurn();
        Turn turn = new PowerfullMove(simpleTurn);
        PowerfullMove decoratedTurn = new Push(turn);

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);
        game.setCurrentPlayer(player);

        assertEquals(true, decoratedTurn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(1,2)));
    }
}