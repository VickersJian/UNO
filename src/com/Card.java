package com;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

/**
 * Created by 49651 on 2016/12/31.
 */
public class Card extends JLabel {
    private String number;
    private char color; //g:green; y:yellow; r:red; b:blue; f:function;
    private boolean clicked;
    private boolean canClick;

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    public String getNumber() {
        return number;
    }

    public char getColor() {
        return color;
    }


    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isClicked() {
        return clicked;
    }

    public Card(String name, char color) {
        this.number = name;
        this.color = color;
        this.canClick = true;
        this.clicked = false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int distance = 0;
                if (canClick) {

                    Point from = getLocation();
                    if (clicked) {
                        distance = -20;
                        setClicked(false);
                    } else {
                        distance = 20;
                        setClicked(true);
                    }
                }
                Point from = getLocation();
                move(from, new Point(from.x, from.y - distance));
            }
        });
    }

    public void turnRear() {

        ImageIcon imageIcon = new ImageIcon(new ImageIcon("img/d.png").getImage().getScaledInstance(88, 136, Image.SCALE_DEFAULT));
        this.setIcon(imageIcon);
        this.setSize(88, 136);
        this.setLocation(350, 50);
        this.setVisible(true);
    }

    public void turnFront() {

        ImageIcon imageIcon = new ImageIcon(new ImageIcon("img/" + color + number + ".png").getImage().getScaledInstance(88, 136, Image.SCALE_DEFAULT));
        this.setIcon(imageIcon);
        this.setSize(88, 136);

        this.setVisible(true);
    }


    public void move(Point from, Point to) {//发牌动画效果

        if (to.x != from.x) {
            double k = (1.0) * (to.y - from.y) / (to.x - from.x);
            double b = to.y - to.x * k;
            int flag = 0;// 判断向左还是向右移动步幅
            if (from.x < to.x)
                flag = 30;
            else {
                flag = -30;
            }
            for (int i = from.x; Math.abs(i - to.x) > 20; i += flag) {
                double y = k * i + b;// 这里主要用的数学中的线性函数
                setLocation(i, (int) y);
                try {
                    Thread.sleep(5); // 延迟，可自己设置
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
            // 位置校准
            setLocation(to);
    }




}

