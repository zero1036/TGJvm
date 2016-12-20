package com.base.list;

import java.util.Stack;
import java.util.List;

/**
 * Created by linzc on 2016/11/22.
 */
public class StackBase {

    public static void main(String[] args) {
        //先进后出，数组尾（栈顶）先pop
        Stack stack = new Stack<String>();
        stack.push("a");
        stack.push("b");
        stack.push("c");

        System.out.println(stack.pop()); //c
        System.out.println(stack.pop()); //b
        System.out.println(stack.pop()); //a
    }
}
