//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tg.gc;

import java.util.ArrayList;

public class jConsoleTest {
    public jConsoleTest() {
    }

    public static void fillHeap(int num) throws InterruptedException {
        ArrayList list = new ArrayList();

        for(int i = 0; i < num; ++i) {
            Thread.sleep(50L);
            list.add(new jConsoleTest.OOMObject());
        }

        System.gc();
    }

    public static void main(String[] args) throws Exception {
        fillHeap(1000);
    }

    static class OOMObject {
        public byte[] placeholder = new byte[65536];

        OOMObject() {
        }
    }
}
