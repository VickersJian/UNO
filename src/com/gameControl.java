package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import static java.lang.System.exit;

/**
 * Created by 49651 on 2016/12/31.
 */
public class gameControl {//游戏控制类

    private Layout layout;
    private JButton publishCard[] = new JButton[3];//出牌按钮
    private Player[] players;
    private aiPlayer[] aiPlayers;
    private int currentPlayer = 0;
    private Vector<Card> deck;
    private Card currentCard;
    private int color; // 0:r; 1:y; 2:g; 3:b; 4:f;
    private int direction = -1; //-1:逆时针 1：顺时针；
    private int flag = 0;
    private Card playCard;
    private Card[] selectedCard;
    private boolean hasPlus;
    public gameControl() {//构造函数

        players = new Player[3];
        players[0] = new Player(0);
        players[1] = new aiPlayer(1);
        players[2] = new aiPlayer(2);
        selectedCard = new Card[3];
        selectedCard[0] = null;
        this.layout = new Layout();
        publishCard[0] = new JButton("出牌");
        publishCard[1] = new JButton("不要");
        publishCard[2] = new JButton("UNO");
        publishCard[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pcPlay();

            }
        });
        publishCard[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerDraw(players[0], 1);

            }
        });
        for (int i = 0; i <= 2; i++) {
            publishCard[i].setBounds(280 + i * 100, 360, 60, 20);
            layout.container.add(publishCard[i]);
            publishCard[i].setVisible(true);
        }
        initDeck();
        shuffle();
        deal();
        Object[] options = {"再来一局", "退出游戏"};
        int response;
        while (true) {//主循环 控制电脑出牌
//             second(1);
            if (currentPlayer != 0) {
                publishCard[0].setEnabled(false);
                publishCard[1].setEnabled(false);
//                second(1);
                aiPlay();
            }
            for (int i = 0; i <3; i++)
                if (players[i].getHandCardQuantity() == 0) {
                    if (i == 0){
                        response =JOptionPane.showOptionDialog(layout.container, "游戏结束,你赢了！", "", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (response == 0)
                            new gameControl();
                        else
                            exit(0);
                    }
                    else {
                        response =JOptionPane.showOptionDialog(layout.container, "游戏结束,你输了。", "", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (response == 0)
                            new gameControl();
                        else
                            exit(0);
                    }
                }

        }
    }

    public void second(double i) {//延时

        try {
            Thread.sleep((int)i * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void pcPlay() {//玩家出牌逻辑判断
        Card sCard = null;

        for (int i = 0; i < players[0].getHandCardQuantity(); i++) {//读取选中的牌
            if (players[0].getCard(i).isClicked()) {//没有判断选中了多张牌的情况，可以在Card类加一个静态变量用来控制只能有一张牌被选中
                sCard = players[0].getCard(i);
                break;
            }
        }

        if (canPlay(sCard, playCard)) {
            playerPlay(sCard);//出牌情况逻辑判断
            publishCard[0].setEnabled(false);
            publishCard[1].setEnabled(false);
        }
        }

    private Card setColor(String name) {//变颜色
        Object[] options = {"红", "黄", "绿", "蓝"};
        Card card = null;
        int response = JOptionPane.showOptionDialog(layout.container, "请选择出牌颜色", "", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (response) {
            case 0:
                card = new Card(name, 'r');
                break;
            case 1:
                card = new Card(name, 'y');
                break;
            case 2:
                card = new Card(name, 'g');
                break;
            case 3:
                card = new Card(name, 'b');
                break;
        }
        return card;
    }

    private Card aiSetColor(String name) {
        Card card = null;
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                card = new Card(name, 'r');
                JOptionPane.showConfirmDialog(layout.container, "玩家选择了红色", "消息", JOptionPane.YES_NO_OPTION);
                break;
            case 1:
                card = new Card(name, 'y');
                JOptionPane.showConfirmDialog(layout.container, "玩家选择了黄色", "消息", JOptionPane.YES_NO_OPTION);
                break;
            case 2:
                card = new Card(name, 'g');
                JOptionPane.showConfirmDialog(layout.container, "玩家选择了绿色", "消息", JOptionPane.YES_NO_OPTION);
                break;
            case 3:
                card = new Card(name, 'b');
                JOptionPane.showConfirmDialog(layout.container, "玩家选择了蓝色", "消息", JOptionPane.YES_NO_OPTION);
                break;
        }
        return card;
    }


    private void aiPlay() {
        Card sCard = null;//准备出的牌
        sCard = ((aiPlayer) players[currentPlayer]).round(playCard);//AI出牌逻辑
        if (sCard == null)
            playerDraw(players[currentPlayer], 1);//最简单的AI,不能出牌才抽牌
        else {
            playerPlay(sCard);
        }
    }

    private boolean canPlay(Card card,Card playCard) {//判断能否出牌
        if (playCard == null)
            return true;
        else
            return card.getNumber().equals(playCard.getNumber()) || card.getColor() == playCard.getColor() || card.getColor() =='f';
    }
    private int calCurrentPlayer(int direction, int currentPlayer) {//计算当前该出牌的玩家
        int player;
        if (direction == -1)
            player = (currentPlayer + 1 + 3) % 3;
        else
            player = (currentPlayer - 1 + 3) % 3;
        return player;
    }
    private void playerDraw(Player player, int count) {//抽牌
        Card card;
        for (int i = 0; i < count; i++) {
            card = deck.lastElement();
            deck.remove(card);
            layout.drawHand(card, player);
        }
        Collections.sort(player.hand,new MyCompare());
        layout.repositionCard(player.hand,player.getName());
        if (count == 1)
            currentPlayer = calCurrentPlayer(direction,currentPlayer);
        if (currentPlayer == 0) {
            publishCard[0].setEnabled(true);
            publishCard[1].setEnabled(true);
        }

    }
    private void playerPlay(Card sCard) {
        int tempPlayer = currentPlayer;
        if (selectedCard[currentPlayer] != null)
            selectedCard[currentPlayer].setVisible(false);
        selectedCard[currentPlayer] = sCard;
        playCard = null;
        //出牌逻辑
        if (selectedCard[currentPlayer].getNumber().equals("10")) {//reverse
            direction = -direction;
            tempPlayer = calCurrentPlayer(direction, currentPlayer);
            setUnvisiable(selectedCard[tempPlayer]);//将下一位玩家已经出过的牌隐藏
            tempPlayer = currentPlayer;
        }
        if (selectedCard[currentPlayer].getNumber().equals("12")) {//+2
            tempPlayer = calCurrentPlayer(direction, currentPlayer);
            setUnvisiable(selectedCard[tempPlayer]);
            playerDraw(players[tempPlayer] ,2);
            tempPlayer = calCurrentPlayer(direction,tempPlayer);//直接跳过下一位玩家的回合（因为只要抽牌）

        }
        if (selectedCard[currentPlayer].getNumber().equals("11")) {//skip
            tempPlayer = calCurrentPlayer(direction, currentPlayer);
            setUnvisiable(selectedCard[tempPlayer]);
            tempPlayer = calCurrentPlayer(direction,tempPlayer);
        } else if (selectedCard[currentPlayer].getColor() == 'f') {//判断功能牌
            //变色
            if (currentPlayer == 0) {//PC玩家
                if (selectedCard[currentPlayer].getNumber().equals(0 + "")) {
                    //设置当前牌
                    playCard = setColor("-1");// -1为选择的颜色牌的number
                } else if (selectedCard[currentPlayer].getNumber().equals(1 + "")) {
                    playCard = setColor("13");//13为+4牌
                    tempPlayer = calCurrentPlayer(direction, currentPlayer);
                    setUnvisiable(selectedCard[tempPlayer]);
                    playerDraw(players[tempPlayer] ,4);
                    tempPlayer = calCurrentPlayer(direction,tempPlayer);
                }
            } else {//AI 自动随机选颜色
                if (selectedCard[currentPlayer].getNumber().equals(0 + "")) {
                    //设置当前牌
                    playCard = aiSetColor("-1");//playCard为选择的颜色牌
                } else if (selectedCard[currentPlayer].getNumber().equals(1 + "")) {
                    playCard = aiSetColor("13");//13为+4牌
                    tempPlayer = calCurrentPlayer(direction, currentPlayer);
                    setUnvisiable(selectedCard[tempPlayer]);
                    playerDraw(players[tempPlayer] ,4);
                    tempPlayer = calCurrentPlayer(direction,tempPlayer);
                }
            }

        }
        //出牌效果
        if (currentPlayer == 0)
            selectedCard[currentPlayer].move(selectedCard[currentPlayer].getLocation(), new Point(380, 200));
        if (currentPlayer == 1)
            selectedCard[currentPlayer].move(selectedCard[currentPlayer].getLocation(), new Point(550, 150));
        if (currentPlayer == 2)
            selectedCard[currentPlayer].move(selectedCard[currentPlayer].getLocation(), new Point(200, 150));
        players[currentPlayer].play(selectedCard[currentPlayer]);
        if (playCard == null)
            playCard = selectedCard[currentPlayer];
        layout.repositionCard(players[currentPlayer].getHand(), players[currentPlayer].getName());
        //确定下一个玩家
        if (tempPlayer != currentPlayer)
            currentPlayer = tempPlayer;
        else
            currentPlayer = calCurrentPlayer(direction, currentPlayer);
        if (currentPlayer == 0) {
            publishCard[0].setEnabled(true);
            publishCard[1].setEnabled(true);
        }

    }
    private void setUnvisiable(Card card) {
        if (card != null && card.isVisible())
            card.setVisible(false);
    }
    private void deal() {//发牌
        Card card;
        for (int i = 0; i < 7; i++)
            for (int j =0; j < 3; j++) {
                card = deck.lastElement();
                deck.removeElement(card);
                layout.createHand(card, players[j], i);
                players[j].draw(card);
            }
        for (int i = 0; i < 3; i++){
            Collections.sort(players[i].hand,new MyCompare());
            layout.repositionCard(players[i].hand,i);
        }

    }

    private void shuffle() {//洗牌
        int random;
        Card temp;
        for (int i = 0; i < 108; i++) {
            random = (int)(Math.random() * 108);
            temp = deck.get(random);
            deck.set(random,deck.get(i));
            deck.set(i,temp);
        }
    }
    private char intToColor(int i) {
        char c = 'a';
        switch (i) {
            case 0: c = 'r';
                    break;
            case 1: c = 'y';
                    break;
            case 2: c = 'g';
                    break;
            case 3: c = 'b';
                    break;
            case 4: c = 'f';
                    break;
        }
        return c;
    }
    private void initDeck() {//初始化牌堆

        int i,j;
        Card card;
        deck = new Vector<Card>();
        for (i = 0; i < 4; i++) { //创建4个0
            card = new Card("0", intToColor(i));
            deck.add(card);

            this.layout.container.add(card);

        }

        for (i =0; i < 4; i++) //创建4组换色和+4
            for (j = 0; j < 2; j++) {
                card = new Card("" + j, 'f');
                deck.add(card);
                this.layout.container.add(card);
            }



        for (int count = 0; count < 2; count++) { //创建2组四种颜色的1-12
            for (i = 0; i < 4; i++)
                for (j = 1; j < 13; j++) {
                    card = new Card("" + j,intToColor(i));
                    deck.add(card);
                    this.layout.container.add(card);
                }
        }

    }

}
