package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by 49651 on 2017/1/1.
 */
public class Layout extends JFrame { //游戏界面布局

    public Container container = null;// 定义容器
    private JMenuItem start, exit, about;// 定义菜单按钮
    private JTextField time[]=new JTextField[3]; //计时器
    public Layout() throws HeadlessException {
        init();
        SetMenu();//
        this.setVisible(true);
    }

    private void SetMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu game = new JMenu("游戏");
        JMenu help = new JMenu("帮助");

        start = new JMenuItem("新游戏");
        exit = new JMenuItem("退出");
        about = new JMenuItem("关于");

        //start.addActionListener();
        //exit.addActionListener();
        //about.addActionListener();

        game.add(start);
        game.add(exit);
        help.add(about);

        jMenuBar.add(game);
        jMenuBar.add(help);
        this.setJMenuBar(jMenuBar);


        for(int i=0;i<3;i++){
            time[i]=new JTextField("倒计时:");
            time[i].setVisible(false);
            container.add(time[i]);
        }
        time[0].setBounds(140, 230, 60, 20);
        time[1].setBounds(374, 360, 60, 20);
        time[2].setBounds(620, 230, 60, 20);

        for(int i=0;i<3;i++)
        {
           // currentList[i]=new Vector<Card>();
        }
    }

    private void init() {

        this.setTitle("Uno");
        this.setSize(830, 620);
        setResizable(false);
        setLocationRelativeTo(getOwner()); // 屏幕居中
        container = this.getContentPane();
        container.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setBackground(new Color(0, 112, 26)); // 背景为绿色

    }

    public void createHand(Card card, Player player, int i) {
        if (player.getName() == 0) {
            card.turnFront();
            card.move(card.getLocation(),new Point(210+i*50,420));
        }

        else {
            card.setCanClick(false);
            card.turnRear();
            if (player.getName() == 1)
                card.move(card.getLocation(),new Point(700,60+i*30));
            else
                card.move(card.getLocation(),new Point(50,60+i*30));
        }
        container.setComponentZOrder(card, 0);
    }
    public void drawHand(Card card, Player player) {
        player.draw(card);
        int i = player.getHandCardQuantity();
        if (player.getName() == 0)
            card.turnFront();
        else
            card.turnRear();
        if (player.getName() == 0)
            card.move(card.getLocation(),new Point(210+i*50,420));
        if (player.getName() == 1)
            card.move(card.getLocation(),new Point(700,60+i*30));
        if (player.getName() == 2)
            card.move(card.getLocation(),new Point(50,60+i*30));
        repositionCard(player.getHand(),player.getName());
        container.setComponentZOrder(card, 0);
    }
    public void repositionCard(Vector<Card> hand, int playerNumber) {//抽牌后重置牌的位置
        Card card;
        for (int i = 0; i < hand.size(); i++) {
            card = hand.get(i);
            if (playerNumber == 0)
                card.move(card.getLocation(), new Point(210+i*50,420));
            if (playerNumber == 1)
                card.move(card.getLocation(), new Point(700,60+i*30));
            if (playerNumber == 2)
                card.move(card.getLocation(), new Point(50,60+i*30));
            container.setComponentZOrder(card, 0);
        }


    }
}
