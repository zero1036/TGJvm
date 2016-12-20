package com.base.list;

import java.util.*;

/**
 * Created by Administrator on 2016/11/19.
 */
public class ArrayListBase {

    public static void main(String[] args) {
        testSimple();
    }

    private static void testSimple() {
        int capacity = 2; //ÈİÁ¿ÉèÎª2
        List<Integer> list = new ArrayList<Integer>(capacity);
        list.add(1);
        list.add(2);
        list.add(3);
    }
}
