package com;
import java.util.Vector;

/**
 * Created by 49651 on 2016/12/31.
 */
public class Player {

    protected Vector<Card> hand;
    private int name;

    public Vector<Card> getHand() {
        return hand;
    }


    public int getName() {
        return name;
    }


    public Player(int name) {
        this.name = name;
        hand = new Vector<>();
    }

    public void play(Card card) {
        this.hand.remove(card);
    }

    public void draw(Card card) {
        this.hand.addElement(card);
    }

    public int getHandCardQuantity() {
        return hand.size();
    }

    public Card getCard(int i) {
        return hand.get(i);
    }

}
