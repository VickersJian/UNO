package com;

/**
 * Created by Dylan on 2017/1/2.
 */
public class aiPlayer extends Player {//电脑玩家

    public aiPlayer(int name) {
        super(name);
    }

    public Card round(Card currentCard) {//到电脑玩家回合时做的判断逻辑
        Card card = null;
        String number = currentCard.getNumber();//当前牌的数字
        char color = currentCard.getColor();//当前牌的颜色

        int i;
        for (i = 0; i < hand.size(); i++) {
            if (hand.get(i).getColor() == color) {
                card = hand.get(i);
                break;
            }
        }
        if (card == null)
            for (i=0; i < hand.size(); i++) {
                if (hand.get(i).getNumber().equals(number) ) {
                    card = hand.get(i);
                    break;
                }
            }
        if (card == null)
            for (i=0; i < hand.size(); i++) {
                if (hand.get(i).getColor() == 'f') {
                    card = hand.get(i);
                    break;
                }
            }


        return card;
    }
}
