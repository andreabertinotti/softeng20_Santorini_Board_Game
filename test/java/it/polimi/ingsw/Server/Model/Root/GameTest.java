package it.polimi.ingsw.Server.Model.Root;

import it.polimi.ingsw.shared.Colour;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameTest {

    @Test
    void GameCostructorTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson2 = new ParserJson(2);
        Game game2 = new Game(2, false, parserJson2, remoteView);
        game2.addPlayer("Marc", 24);
        game2.addPlayer("Alexander", 18);
        assertEquals(2,game2.getPlayers().size());

        RemoteView remoteView1 = new RemoteView();
        ParserJson parserJson3 = new ParserJson(3);
        Game game3 = new Game(3, false, parserJson3, remoteView1);
        game3.addPlayer("Marc", 24);
        game3.addPlayer("Alexander", 18);
        game3.addPlayer("Johnny", 22);
        assertEquals(3,game3.getPlayers().size());
    }

    @Test
    void GameWithMorePlayersTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true, parserJson,remoteView);
        assertEquals(0,game.getPlayers().size());
        game.addPlayer("Marc", 24);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);
        game.addPlayer("Carl",28);
        assertEquals(3, game.getPlayers().size());
    }

    @Test
    void addPlayerTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, false, parserJson, remoteView);

        assertEquals(true, game.addPlayer("Marc", 24));
        assertEquals(true, game.addPlayer("Alexander", 18));
        assertEquals(false, game.addPlayer("Marc", 24));
    }

    @Test
    void removePlayerTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, false, parserJson, remoteView);
        game.addPlayer("Marc", 24);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);

        assertEquals(3, game.getPlayers().size());
        assertEquals(true, game.removePlayer("Johnny", 22));
        assertEquals(2, game.getPlayers().size());
        game.removePlayer(game.getPlayers().get(0).getNickname(), game.getPlayers().get(0).getAge());
        game.removePlayer(game.getPlayers().get(0).getNickname(), game.getPlayers().get(0).getAge());
        assertEquals(0, game.getPlayers().size());
    }

    @Test
    void youngestPlayerTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, false, parserJson, remoteView);
        game.addPlayer("Marc", 24);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);

        assertEquals(game.getPlayers().get(1), game.youngestPlayer());
    }

   /* @Test
    void challengerChooseDeckTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true, parserJson, remoteView);

        ArrayList<String> divinityChosen = new ArrayList<>(Arrays.asList("Apollo", "Athena", "Atlas"));

        assertEquals(false, game.challengerChooseCards(divinityChosen));

        game.addPlayer("Marc", 26);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);
        game.chooseChallenger();
        game.challengerChooseCards(divinityChosen);
        assertEquals(game.getDeck().getCardsInDeck().size(), divinityChosen.size());
    }*/

    @Test
    void chooseChallengerTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, false, parserJson, remoteView);

        game.chooseChallenger();
        assertEquals(null, game.getChallenger());
        game.addPlayer("Marc", 26);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);
        game.chooseChallenger();
        assertEquals(game.getPlayers().get(0), game.getChallenger());

    }

    @Test
    void chooseFirstTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, false, parserJson, remoteView);
        assertEquals(false, game.chooseFirst("Johnny"));
        game.addPlayer("Marc", 26);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);

        game.chooseFirst("Johnny");
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(2));
    }

    /*@Test
    void setPlayerDivinityTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true, parserJson, remoteView);
        game.addPlayer("Marc", 26);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);
        game.chooseChallenger();
        ArrayList<String> divinityChosen = new ArrayList<>(Arrays.asList("Apollo", "Athena", "Atlas"));
        game.challengerChooseCards(divinityChosen);
        assertEquals(game.getChallenger(), game.getPlayers().get(0));

        game.setPlayerDivinity("Apollo");
        game.setPlayerDivinity("Atlas");
        assertEquals("Apollo", game.getPlayers().get(1).getCard().getName());
        assertEquals("Atlas", game.getPlayers().get(2).getCard().getName());
        assertEquals("Athena", game.getPlayers().get(0).getCard().getName());
    }*/

    @Test
    void setWorkersInitialPosition() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, false, parserJson, remoteView);
        game.addPlayer("Marc", 24);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);

        assertEquals(0, game.setWorkersInitialPosition(0,0,1,2));
        assertEquals(1, game.setWorkersInitialPosition(0,0,1,2));
        assertEquals(0, game.setWorkersInitialPosition(2,0,4,2));
        assertEquals(1, game.setWorkersInitialPosition(2,0,3,3));
        assertEquals(2, game.setWorkersInitialPosition(1,4,4,4));
        assertEquals(1, game.setWorkersInitialPosition(3,4,4,1));

        for (Player player : game.getPlayers())
            for (Worker worker : player.getWorkers())
                assertNotNull(worker.getPosition());
    }

    @Test
    void selectWorkerTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(2, false, parserJson, remoteView);
        game.addPlayer("Marc", 24);
        game.addPlayer("Alexander", 18);
        assertEquals(0, game.setWorkersInitialPosition(0,0,1,2));
        assertEquals(2, game.setWorkersInitialPosition(2,0,4,2));
        assertEquals(true, game.selectWorker(2, Colour.YELLOW));
        assertEquals(false, game.selectWorker(2, Colour.GREEN));
    }

    @Test
    void nextPlayerTurnTest() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, false, parserJson, remoteView);
        game.addPlayer("Marc", 24);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);
        Turn turn = new SimpleTurn();
        for (Player player : game.getPlayers())
            player.setMyTurn(turn);

        game.setCurrentPlayer(game.getPlayers().get(1));
        game.nextPlayerTurn(true);
        assertEquals(game.getPlayers().get(2), game.getCurrentPlayer());
        game.nextPlayerTurn(true);
        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
    }
}
