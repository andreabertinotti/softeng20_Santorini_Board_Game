
package it.polimi.ingsw.Server.Model.Root;


import java.util.ArrayList;

public class Deck {

    private ArrayList<DivinityCard> cardsInDeck;

    public ArrayList<DivinityCard> getCardsInDeck() {
        return cardsInDeck;
    }

    public void setCardsInDeck(ArrayList<DivinityCard> cardsInDeck) {
        this.cardsInDeck = cardsInDeck;
    }

    /**
     * this method creates a deck of DivinityCard cards usable in the game accordingly to the number of players
     */
    public Deck(ArrayList<DivinityCard> parseCardsInDeck) {
        cardsInDeck = parseCardsInDeck;
    }

    public void removeCard(DivinityCard toBeRemoved){
        cardsInDeck.remove(toBeRemoved);
    }
}