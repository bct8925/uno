package com.bri64.uno;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Hand extends Deck {

    public List<Card> playableCards(Deck playDeck) {
        return stream().filter((card -> card.canPlay(playDeck.getTop()))).collect(Collectors.toList());
    }

    public Card autoPlay(Deck drawDeck, Deck playDeck, Player player) {
        List<Card> playableCards = playableCards(playDeck);
        if (!playableCards.isEmpty()) {
            Collections.shuffle(playableCards);
            remove(playableCards.get(0));
            return playableCards.get(0);
        } else {
            int size = size();
            Card c = draw(drawDeck, playDeck, player);
            System.out.println("Player " + player.getNumber() + " drew " + (size() - size) + " cards");
            return c;
        }
    }

    public Card draw(Deck drawDeck, Deck playDeck, Player player) {
        Boolean keepDrawing = true; // TODO make rule
        if (keepDrawing) {
            Card drawn = drawDeck.drawOne(playDeck);
            if (drawn == null) return null;
            if (!player.isCPU()) {
                System.out.println("Player " + player.getNumber() + " draws " + drawn);
                Util.sleep(1000);
            }

            while (!drawn.canPlay(playDeck.getTop())) {
                addCard(drawn);
                drawn = drawDeck.drawOne(playDeck);
                if (drawn == null) return null;
                if (!player.isCPU()) {
                    System.out.println("Player " + player.getNumber() + " draws " + drawn);
                    Util.sleep(1000);
                }
            }

            Boolean forcePlay = true; // TODO make rule
            if (forcePlay) {
                return drawn;
            } else {
                addCard(drawn); // TODO make choice
            }
        } else {
            Card drawn = drawDeck.drawOne(playDeck);
            if (drawn == null) return null;
            if (!player.isCPU()) {
                System.out.println("Player " + player.getNumber() + " draws " + drawn);
            }

            Boolean forcePlay = false; // TODO make rule
            if (forcePlay) {
                return drawn;
            } else {
                addCard(drawn); // TODO make choice
            }
        }
        return null;
    }

    public Integer getValue() {
        return stream().map(Card::getValue).reduce(0, Integer::sum);
    }

    public String showHand() {
        StringBuilder result = new StringBuilder();
        int i = 1;
        for (Card c : this) {
            result.append("(").append(i++).append(") ").append(c).append("  ");
        }
        return result.toString();
    }
}
