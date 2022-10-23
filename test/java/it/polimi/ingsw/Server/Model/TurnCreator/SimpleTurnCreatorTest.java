package it.polimi.ingsw.Server.Model.TurnCreator;

import it.polimi.ingsw.Server.Model.Decorator.MultipleBuild.*;
import it.polimi.ingsw.Server.Model.Decorator.MultipleMove.OnBorderOneMoreMove;
import it.polimi.ingsw.Server.Model.Decorator.MultipleMove.OneMoreMove;
import it.polimi.ingsw.Server.Model.Decorator.PowerfullBuild.BuildDome;
import it.polimi.ingsw.Server.Model.Decorator.PowerfullBuild.BuildUnderneath;
import it.polimi.ingsw.Server.Model.Decorator.PowerfullMove.Push;
import it.polimi.ingsw.Server.Model.Decorator.PowerfullMove.Swap;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Decorator.WinCondition.BlockOpponents;
import it.polimi.ingsw.Server.Model.Decorator.WinCondition.CompleteBuildWin;
import it.polimi.ingsw.Server.Model.Decorator.WinCondition.EasyWin;
import it.polimi.ingsw.Server.Model.Root.Deck;
import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleTurnCreatorTest {


    @Test
    void getTurnTest(){

        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(2);
        Game game = new Game(2, false, parserJson, remoteView);
        game.addPlayer("Richie", 61);

        Player player = game.getPlayers().get(0);

        Turn turn = new SimpleTurn();


        assertEquals(turn.getClass(), player.getMyTurn().getClass());

    }

    ParserJson parserJson = new ParserJson(2);
    Deck deck = new Deck(parserJson.getParserCardsInDeck());

    @Test
    void getApolloTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated = new Swap(turn);

        player.setCard(deck.getCardsInDeck().get(0));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getArtemisTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new OneMoreMove(turn);

        player.setCard(deck.getCardsInDeck().get(1));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getAthenaTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new BlockOpponents(turn);

        player.setCard(deck.getCardsInDeck().get(2));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getAtlasTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new BuildDome(turn);

        player.setCard(deck.getCardsInDeck().get(3));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getChronusTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new CompleteBuildWin(turn);

        player.setCard(deck.getCardsInDeck().get(4));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getDemeterTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated = new DifferentPosOneMoreBuild(turn);

        player.setCard(deck.getCardsInDeck().get(5));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getHephaestusTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new SamePosOneMoreBuild(turn);

        player.setCard(deck.getCardsInDeck().get(6));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getHestiaTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new OffBorderOneMoreBuild(turn);

        player.setCard(deck.getCardsInDeck().get(7));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getMinotaurTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new Push(turn);

        player.setCard(deck.getCardsInDeck().get(8));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getPanTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new EasyWin(turn);

        player.setCard(deck.getCardsInDeck().get(9));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getPoseidonTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new OnGroundThreeBuild(turn);

        player.setCard(deck.getCardsInDeck().get(10));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getPrometheusTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new OnLevelDoubleBuild(turn);

        player.setCard(deck.getCardsInDeck().get(11));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getTritonTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new OnBorderOneMoreMove(turn);

        player.setCard(deck.getCardsInDeck().get(12));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }

    @Test
    void getZeusTurnTest(){
        Player player = new Player("Marc",25);
        Turn turn = new SimpleTurn();
        Turn decorated  = new BuildUnderneath(turn);

        player.setCard(deck.getCardsInDeck().get(13));

        player.createTurn(player.getCard().getEffect());
        assertEquals(decorated.getClass(), player.getMyTurn().getClass());

    }


}
