package it.polimi.ingsw.Server.Model.Root;

import it.polimi.ingsw.Server.Parser.ParserJson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {

    @Test
    void DeckForTwoTest(){

        ParserJson parserJson = new ParserJson(3);
        Deck deck = new Deck(parserJson.getParserCardsInDeck());

        int expectedLenght = 14;

        assertEquals(expectedLenght, deck.getCardsInDeck().size());
    }

    @Test
    void DeckForThreeTest(){

        ParserJson parserJson = new ParserJson(3);
        Deck deck = new Deck(parserJson.getParserCardsInDeck());

        int expectedLenght = 13;

        assertEquals(expectedLenght, deck.getCardsInDeck().size());
    }

}