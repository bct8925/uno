package com.bri64.uno;

import com.bri64.uno.enums.Color;
import com.bri64.uno.enums.Face;

import java.util.*;
import java.util.stream.Collectors;

public class Deck extends LinkedList<Card> {

    public void shuffle() {
        Collections.shuffle(this);
    }

    public void sort() {
        sort(Card::compareTo);
    }

    public Card getTop() {
        return (!isEmpty()) ? getFirst() : null;
    }

    public Card getBottom() {
        return getLast();
    }

    public Card drawOne(Deck playDeck) {
        if (isEmpty()) {
            handleEmpty(playDeck);
        }

        Card drawn = (!isEmpty()) ? removeFirst() : null;
        if (drawn != null) drawn.setSelected(null);
        return drawn;
    }

    public Deck drawN(Deck playDeck, Integer n) {
        Deck d = new Deck();
        for (int i = 0; i < n; i++) {
            Card c = drawOne(playDeck);
            if (c == null) break;
            d.add(c);
        }
        return d;
    }

    public void play(Card c) {
        addFirst(c);
    }

    public void addCard(Card c) {
        if (c != null) {
            c.setSelected(null);
            add(c);
            sort();
        }
    }

    public void addCards(Collection<Card> d) {
        addAll(d);
        sort();
    }

    public void handleEmpty(Deck playDeck) {
        Card top = playDeck.removeFirst();
        playDeck.shuffle();
        addAll(playDeck);
        playDeck.clear();
        playDeck.add(top);
    }

    @Override
    public String toString() {
        return "{" + stream().map(Card::toString).collect(Collectors.joining(", ")) + "}";
    }

    public static Deck getNewDeck() {
        Deck deck = new Deck();
        for (int i = 0; i < 4; i++) {
            Color color = Color.values()[i];
            Card c = new Card(Color.BLUE, Face.TWO);
            deck.add(new Card(color, Face.ZERO));
            for (int j = 1; j <= 12; j++) {
                deck.add(new Card(color, Face.values()[j]));
                deck.add(new Card(color, Face.values()[j]));
            }
            deck.add(new Card(Color.BLACK, Face.WILD));
            deck.add(new Card(Color.BLACK, Face.DRAW4));
        }
        deck.sort();
        return deck;
    }
}
