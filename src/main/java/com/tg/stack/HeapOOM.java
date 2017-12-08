//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tg.stack;

import java.util.ArrayList;

public class HeapOOM {
    public HeapOOM() {
    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList();

        while(true) {
            list.add(new HeapOOM.OOMObject());
        }
    }

    static class OOMObject {
        OOMObject() {
        }
    }
}
