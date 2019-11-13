package com.bri64.uno;

import com.bri64.uno.enums.Color;
import com.bri64.uno.enums.Face;
import com.bri64.uno.enums.TurnResult;

public class Card implements Comparable<Card> {

    private Color color;
    private Face face;

    public Color getColor() {
        return color;
    }

    public Face getFace() {
        return face;
    }

    private Color selectedColor;

    public Color getSelected() {
        return selectedColor;
    }

    public void setSelected(Color color) {
        this.selectedColor = color;
    }

    public Card(Color color, Face face) {
        this.color = color;
        this.face = face;
        this.selectedColor = null;
    }

    public Boolean canPlay(Card c) {
        return this.color == Color.BLACK
                || this.color == c.color
                || this.face == c.face
                || this.color == c.selectedColor;
    }

    public Boolean isNumber() {
        return face.ordinal() <= 9;
    }

    public Boolean isSkip() {
        return face == Face.SKIP;
    }

    public Boolean isReverse() {
        return face == Face.REVERSE;
    }

    public Boolean isDraw2() {
        return face == Face.DRAW2;
    }

    public Boolean isWild() {
        return face == Face.WILD;
    }

    public Boolean isDraw4() {
        return face == Face.DRAW4;
    }

    public Integer getValue() {
        return (isNumber()) ? face.ordinal() : (color != Color.BLACK) ? 20 : 50;
    }

    public TurnResult getResult() {
        if (isSkip()) return TurnResult.SKIP;
        if (isReverse()) return TurnResult.REVERSE;
        if (isDraw2()) return TurnResult.DRAW2;
        if (isWild()) return TurnResult.WILD;
        if (isDraw4()) return TurnResult.DRAW4;
        return TurnResult.DEFAULT;
    }

    public String getFaceName() {
        switch (face) {
            case ZERO:
                return String.valueOf(0);
            case ONE:
                return String.valueOf(1);
            case TWO:
                return String.valueOf(2);
            case THREE:
                return String.valueOf(3);
            case FOUR:
                return String.valueOf(4);
            case FIVE:
                return String.valueOf(5);
            case SIX:
                return String.valueOf(6);
            case SEVEN:
                return String.valueOf(7);
            case EIGHT:
                return String.valueOf(8);
            case NINE:
                return String.valueOf(9);
            default:
                return String.valueOf(face);
        }
    }

    @Override
    public String toString() {
        return Prompt.getCardColored(this);
    }

    @Override
    public int compareTo(Card o) {
        return (this.color != o.color) ? this.color.compareTo(o.color) : this.face.compareTo(o.face);
    }
}