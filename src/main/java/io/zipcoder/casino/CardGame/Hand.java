package io.zipcoder.casino.CardGame;

import io.zipcoder.casino.CardGame.Cards.Card;
import io.zipcoder.casino.CardGame.Cards.Deck;
import io.zipcoder.casino.CardGame.Cards.Suit;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Hand {
    private int size;
    private ArrayList<Card> cards = new ArrayList<>();

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void drawCard(Deck deck) {
         cards.add(deck.getCard(0));
         deck.removeCardFromDeck(0);
    }

    public ArrayList<Card> showMyCards() {
        return cards;
    }

    public void addCardsToHand(ArrayList<Card> cards){

        for (Card c : cards){
            this.cards.add(c);

        }
    }

    public void removeCardsFromHand(ArrayList<Card> cards) {
        for (Card c : cards) {
            this.cards.remove(c);
        }
    }



    public String toString(){
        String cardsInHand = "";

        for (Card c: cards) {

            cardsInHand += "| " + c.getCardFaceValue() + c.getCardSuitValue() + " |\t";

        }

        cardsInHand += "\n";

        return cardsInHand;
    }

    public int getSize() {
        return cards.size();
    }
}


