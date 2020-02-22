package com;

import java.util.Comparator;

/**
 * Created by Dylan on 2017/1/3.
 */
public class MyCompare implements Comparator<Card> {//排序
    @Override
    public int compare(Card o1, Card o2) {
        int i = -1;
        if ((o1.getColor() == 'f' && o2.getColor() == 'f') || (o1.getColor() == 'r' && o2.getColor() == 'r') || (o1.getColor() == 'y' && o2.getColor() == 'y') || (o1.getColor() == 'g' && o2.getColor() == 'g') || (o1.getColor() == 'b' && o2.getColor() == 'b')) {
            if (Integer.parseInt(o1.getNumber()) < Integer.parseInt(o2.getNumber()))
                i = 1;
        }

        if (o1.getColor() == 'f' && (o2.getColor() == 'r' || o2.getColor() == 'y' || o2.getColor() == 'g' || o2.getColor() == 'b'))
            i = 1;

        if (o1.getColor() == 'r' && (o2.getColor() == 'y' || o2.getColor() == 'g' || o2.getColor() == 'b'))
            i = 1;
        if (o1.getColor() == 'y' && (o2.getColor() == 'g' || o2.getColor() == 'b'))
            i = 1;
        if (o1.getColor() == 'g' && (o2.getColor() == 'b'))
            i = 1;
        return i;

    }
}
