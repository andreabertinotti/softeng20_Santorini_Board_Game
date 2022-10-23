package it.polimi.ingsw.Server.Model.Decorator;

import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Worker;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleTurnTest {

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
        game.getPlayers().get(2).getWorkers().get(0).setPosition(game.getBoard().getPosition(3,2));
        game.getBoard().getPosition(3,2).setOccupiedBy(game.getPlayers().get(2).getWorkers().get(0));

        //costruisce i blocchi
        game.getBoard().getPosition(1,2).setLevel(3);
        game.getBoard().getPosition(1,3).setLevel(3);
        game.getBoard().getPosition(1,3).setDome(true);
        game.getBoard().getPosition(1,4).setLevel(2);
        game.getBoard().getPosition(2,2).setLevel(3);
        game.getBoard().getPosition(2,3).setLevel(2);
        game.getBoard().getPosition(2,4).setLevel(1);
        game.getBoard().getPosition(3,2).setLevel(0);
        game.getBoard().getPosition(3,3).setLevel(1);
        game.getBoard().getPosition(3,4).setLevel(0);

        return game;
    }

    @Test
    void startTurnTest() {
        Turn turn = new SimpleTurn();
        Player player = new Player("Marc",25);

        player.assignWorker(0);
        turn.startTurn(player);
        assertEquals(0, player.getWorkers().get(0).getMoveNum());
        assertEquals(0, player.getWorkers().get(0).getBuildNum());
        assertEquals(0, player.getWorkers().get(1).getMoveNum());
        assertEquals(0, player.getWorkers().get(1).getBuildNum());
        //non testato
        assertEquals(false, player.isMyTurnOver());
    }

    @Test
    void moveTest1() {
        Turn turn = new SimpleTurn();

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(turn);
        game.setCurrentPlayer(player);

        assertEquals(true, turn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(1, 2)));
    }

    @Test
    void moveTest2() {
        Turn turn = new SimpleTurn();

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(turn);
        game.setCurrentPlayer(player);

        assertEquals(true, turn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(3,3)));
    }

    @Test
    void moveTest3() {
        Turn turn = new SimpleTurn();

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(turn);
        game.setCurrentPlayer(player);

        assertEquals(true, turn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(3,4)));
    }

    @Test
    void moveTest4() {
        Turn turn = new SimpleTurn();

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(turn);
        game.setCurrentPlayer(player);

        assertEquals(false, turn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(0,0)));
    }

    @Test
    void moveTestOnFalse() {
        Turn turn = new SimpleTurn();

        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(turn);
        game.setCurrentPlayer(player);

        assertEquals(false, turn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(1,3)));
        assertEquals(false, turn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(1,4)));

        player.getWorkers().get(0).setPosition(game.getBoard().getPosition(3,3));
        game.getBoard().getPosition(3,3).setOccupiedBy(player.getWorkers().get(0));
        game.getBoard().getPosition(2,3).setOccupiedBy(null);

        assertEquals(false, turn.move(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(2,2)));
    }

    @Test
    void checkPossibleMoveTest() {
        Turn turn = new SimpleTurn();
        Game game = sceneOfGame();

        assertEquals(true, turn.checkPossibleMove(game.getBoard().getPosition(1,2),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0)));
        assertEquals(false, turn.checkPossibleMove(game.getBoard().getPosition(1,3),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0)));
        assertEquals(false, turn.checkPossibleMove(game.getBoard().getPosition(1,4),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0)));
        assertEquals(true, turn.checkPossibleMove(game.getBoard().getPosition(3,3),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0)));
        assertEquals(true, turn.checkPossibleMove(game.getBoard().getPosition(3,4),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0)));
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(3,3));
        game.getBoard().getPosition(3,3).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));
        game.getBoard().getPosition(2,3).setOccupiedBy(null);
        assertEquals(false, turn.checkPossibleMove(game.getBoard().getPosition(2,2),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0)));
    }

    @Test
    void buildTest() {
        Turn turn = new SimpleTurn();
        boolean res;
        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(turn);
        game.setCurrentPlayer(player);

        player.getWorkers().get(0).setMoveNum(1);

        res = turn.build(player, game.getBoard(),player.getWorkers().get(0), game.getBoard().getPosition(1,2), true);
        assertEquals(true, res);

        turn.startTurn(player);
        player.getWorkers().get(0).setMoveNum(1);
        assertEquals(true, turn.build(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(3,4), false));

        turn.startTurn(player);
        player.getWorkers().get(0).setMoveNum(1);
        assertEquals(false, turn.build(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(1,3), false));
        assertEquals(false, turn.build(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(1,4), false));
        assertEquals(false, turn.build(player, game.getBoard(),
                player.getWorkers().get(0), game.getBoard().getPosition(2,3), false));

    }

    @Test
    void checkPossibleBuildTest() {
        Turn turn = new SimpleTurn();
        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);

        assertEquals(true, turn.checkPossibleBuilding(game.getBoard().getPosition(1,2),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0),player));
        assertEquals(true, turn.checkPossibleBuilding(game.getBoard().getPosition(3,4),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0),player));
        assertEquals(false, turn.checkPossibleBuilding(game.getBoard().getPosition(1,3),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0),player));
        assertEquals(false, turn.checkPossibleBuilding(game.getBoard().getPosition(1,4),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0),player));
        assertEquals(false, turn.checkPossibleBuilding(game.getBoard().getPosition(2,3),game.getBoard(),
                game.getPlayers().get(0).getWorkers().get(0),player));
    }

    @Test
    void isWinnerTest() {
        Turn turn = new SimpleTurn();
        Game game = sceneOfGame();

        assertEquals(true, turn.isWinner(game.getPlayers().get(0).getWorkers().get(0).getPosition(),
                game.getBoard().getPosition(1,2), game.getPlayers().get(0),game.getBoard()));
        assertEquals(false, turn.isWinner(game.getPlayers().get(0).getWorkers().get(0).getPosition(),
                game.getBoard().getPosition(3,3), game.getPlayers().get(0), game.getBoard()));
        game.getBoard().getPosition(2,4).setLevel(2);
        assertEquals(false, turn.isWinner(game.getPlayers().get(0).getWorkers().get(0).getPosition(),
                game.getBoard().getPosition(2,4), game.getPlayers().get(0), game.getBoard()));
    }

    @Test
    void isLoserTest() {
        Turn turn = new SimpleTurn();
        Game game = sceneOfGame();
        game.getBoard().getPosition(1,2).setDome(true);
        game.getBoard().getPosition(2,2).setDome(true);
        game.getPlayers().get(1).getWorkers().get(1).setPosition(game.getBoard().getPosition(3,3));
        game.getBoard().getPosition(3,3).setOccupiedBy(game.getPlayers().get(1).getWorkers().get(1));

        assertEquals(false, turn.isLoser(game.getPlayers().get(0), game.getBoard()));

        game.getPlayers().get(2).getWorkers().get(1).setPosition(game.getBoard().getPosition(3,4));
        game.getBoard().getPosition(3,4).setOccupiedBy(game.getPlayers().get(2).getWorkers().get(1));

        assertEquals(true, turn.isLoser(game.getPlayers().get(0), game.getBoard()));
    }

    @Test
    void effectsOnOpponentsTest() {
        Turn turn = new SimpleTurn();
        Game game = sceneOfGame();

        assertEquals(true, turn.effectsOnOpponents(game.getPlayers().get(0),
                game.getBoard().getPosition(1,2), game.getBoard(), game.getPlayers().get(0).getWorkers().get(0)));
        game.getPlayers().get(0).setActiveEffect(true);
        assertEquals(false, turn.effectsOnOpponents(game.getPlayers().get(0),
                game.getBoard().getPosition(1,2), game.getBoard(), game.getPlayers().get(0).getWorkers().get(0)));
        assertEquals(true, turn.effectsOnOpponents(game.getPlayers().get(0),
                game.getBoard().getPosition(3,3), game.getBoard(), game.getPlayers().get(0).getWorkers().get(0)));
    }

    @Test
    void checkMoveFlow(){
        Turn turn = new SimpleTurn();
        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(turn);
        Worker worker = player.getWorkers().get(0);
        Worker worker1 = player.getWorkers().get(1);
        boolean res;

        res = turn.build(player, game.getBoard(), worker,game.getBoard().getPosition(2,2), false);
        assertEquals(false, res);

        worker1.setMoveNum(1);

        res = turn.move(player, game.getBoard(), worker,game.getBoard().getPosition(2,2));
        assertEquals(false, res);
    }

    @Test
    void checkBuildFlow(){
        Turn turn = new SimpleTurn();
        Game game = sceneOfGame();
        Player player = game.getPlayers().get(0);
        player.setMyTurn(turn);
        Worker worker = player.getWorkers().get(0);
        boolean res;

        res = turn.build(player, game.getBoard(), worker,game.getBoard().getPosition(2,2), false);
        assertEquals(false, res);

        worker.setMoveNum(1);
        res = turn.build(player, game.getBoard(), worker,game.getBoard().getPosition(2,2), true);
        assertEquals(true, res);
    }
}