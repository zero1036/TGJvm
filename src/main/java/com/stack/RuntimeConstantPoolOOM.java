//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.stack;

import java.util.ArrayList;

public class RuntimeConstantPoolOOM {
    public RuntimeConstantPoolOOM() {
    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        int i = 0;

        while(true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
