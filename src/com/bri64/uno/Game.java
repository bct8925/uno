package com.bri64.uno;

import com.bri64.uno.enums.Color;
import com.bri64.uno.enums.TurnResult;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;

    private Deck playDeck;
    private Deck drawDeck;

    private Integer turn;
    private Boolean clockwise;

    public Game() {
        setupHands();
        playGame();
        handleWinner();
    }

    private void setupHands() {
        players = new ArrayList<>();
        players.add(new Player(1, false));
        players.add(new Player(2, true));
        players.add(new Player(3, true));
        players.add(new Player(4, true));

        playDeck = new Deck();
        drawDeck = Deck.getNewDeck();
        drawDeck.shuffle();
        for (int i = 0; i < 7; i++) {
            players.get(0).getHand().addCard(drawDeck.drawOne(playDeck));
            players.get(1).getHand().addCard(drawDeck.drawOne(playDeck));
            players.get(2).getHand().addCard(drawDeck.drawOne(playDeck));
            players.get(3).getHand().addCard(drawDeck.drawOne(playDeck));
        }
        playDeck.addCard(drawDeck.drawOne(playDeck));

        //System.out.println("p1: " + players.get(0).getHand());
        //System.out.println("p2: " + players.get(1).getHand());
        //System.out.println("p3: " + players.get(2).getHand());
        //System.out.println("p4: " + players.get(3).getHand());
        //System.out.println("play: " + playDeck.getTop() + "\n");
    }

    private void playGame() {
        turn = 3;
        clockwise = true;
        System.out.println("-------First Card-------");
        System.out.println("--" + playDeck.getTop() + "--");
        handleTurnResult(playDeck.getTop().getResult(), players.get(turn));

        int numTurns = 1;
        while (!players.get(0).hasWon() && !players.get(1).hasWon() &&
                !players.get(2).hasWon() && !players.get(3).hasWon()) {
            System.out.println("-------Turn " + numTurns++ + "-------");
            System.out.println("--" + playDeck.getTop() + "--");
            doTurn(players.get(turn));
            Util.sleep(1000);
        }
    }

    private void doTurn(Player p) {
        handleTurnResult(p.takeTurn(drawDeck, playDeck), p);
    }

    private void handleTurnResult(TurnResult result, Player p) {
        if (result == TurnResult.SKIP) {
            int skipped = nextTurn(turn, clockwise);
            turn = nextTurn(skipped, clockwise);
            System.out.println("Player " + (skipped + 1) + " skipped");
        } else if (result == TurnResult.REVERSE) {
            clockwise = !clockwise;
            turn = nextTurn(turn, clockwise);
            System.out.println("Reversed");
        } else if (result == TurnResult.DRAW2) {
            int nextPlayer = nextTurn(turn, clockwise);
            players.get(nextPlayer).getHand().addCards(drawDeck.drawN(playDeck, 2));
            turn = nextTurn(nextTurn(turn, clockwise), clockwise);
            System.out.println("Player " + (nextPlayer + 1) + " draws 2 cards");
        } else if (result == TurnResult.WILD) {
            Color color = p.chooseColor();
            playDeck.getTop().setSelected(color);
            turn = nextTurn(turn, clockwise);
        } else if (result == TurnResult.DRAW4) {
            Color color = p.chooseColor();
            playDeck.getTop().setSelected(color);
            int nextPlayer = nextTurn(turn, clockwise);
            players.get(nextPlayer).getHand().addCards(drawDeck.drawN(playDeck, 4));
            turn = nextTurn(nextTurn(turn, clockwise), clockwise);
            System.out.println("Player " + (nextPlayer + 1) + " draws 4 cards");
        } else {
            turn = nextTurn(turn, clockwise);
        }
        System.out.println();
    }

    private void handleWinner() {
        Player winner = null;
        for (int i = 0; i < 4; i++) {
            if (players.get(i).hasWon()) {
                winner = players.get(i);
                break;
            }
        }
        List<Player> losers = new ArrayList<>(players);
        losers.remove(winner);
        Integer score = losers.stream().map(Player::getHand).map(Hand::getValue).reduce(0, Integer::sum);
        System.out.println("=====Player " + winner.getNumber() + " won (Score: " + score + ")=====");
    }

    public static Integer nextTurn(Integer turn, Boolean clockwise) {
        return (clockwise) ? ((turn + 1 + 4) % 4) : ((turn - 1 + 4) % 4);
    }
}
