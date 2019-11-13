package com.bri64.uno;

import com.bri64.uno.enums.Color;
import com.bri64.uno.enums.TurnResult;

import java.util.Random;

import static com.bri64.uno.Prompt.promptInt;
import static com.bri64.uno.Prompt.promptString;

public class Player {
    private Integer number;
    private Boolean cpu;
    private Hand hand;

    public Integer getNumber() {
        return number;
    }

    public Boolean isCPU() {
        return cpu;
    }

    public Hand getHand() {
        return hand;
    }

    public Boolean hasWon() {
        return hand.isEmpty();
    }

    public Player(Integer number, Boolean isCpu) {
        this.number = number;
        this.cpu = isCpu;
        this.hand = new Hand();
    }

    public TurnResult takeTurn(Deck drawDeck, Deck playDeck) {
        return (isCPU()) ? takeTurnCPU(drawDeck, playDeck) : takeTurnHuman(drawDeck, playDeck);
    }

    private TurnResult takeTurnCPU(Deck drawDeck, Deck playDeck) {
        Card c = hand.autoPlay(drawDeck, playDeck, this);
        if (c != null) {
            playDeck.addFirst(c);
            System.out.println("Player " + number + " plays " + c + " (" + hand.size() + " cards left)");
            return c.getResult();
        } else {
            return TurnResult.DEFAULT;
        }
    }

    private TurnResult takeTurnHuman(Deck drawDeck, Deck playDeck) {
        promptString("Player " + number + "'s turn! [Press Enter]");

        Card card = null;
        while (card == null) {
            int selection = promptInt("Pick a playable card (or 0 for draw): " + hand.showHand());
            if (selection == 0) {
                card = hand.draw(drawDeck, playDeck, this);
                if (card == null) { // No more cards to draw
                    System.out.println("Draw deck is empty");
                    return TurnResult.DEFAULT;
                }
            } else {
                card = (selection >= 1 && selection <= hand.size()) ? hand.get(selection - 1) : null;
                if (card != null && !card.canPlay(playDeck.getTop())) {
                    card = null;
                }
            }

        }
        hand.remove(card);
        playDeck.addFirst(card);
        System.out.println("Player " + number + " plays " + card + " (" + hand.size() + " cards left)");
        return card.getResult();
    }

    public Color chooseColor() {
        if (cpu) {
            int random = Math.abs(new Random().nextInt()) % 4;
            return Color.values()[random];
        } else {
            Color color = null;
            while (color == null) {
                int selection = promptInt("Pick a color: (1) Red  (2) Green  (3) Blue  (4) Yellow  ");
                color = (selection >= 1 && selection <= 4) ? Color.values()[selection - 1] : null;
            }
            return color;
        }
    }
}
