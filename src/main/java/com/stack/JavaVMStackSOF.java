//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.stack;

public class JavaVMStackSOF {
    private int stackLength = 1;

    public JavaVMStackSOF() {
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF oom = new JavaVMStackSOF();

        try {
            oom.stackLeak();
        } catch (Throwable var3) {
            System.out.println("stack length:" + oom.stackLength);
            throw var3;
        }
    }

    private void stackLeak() {
        ++this.stackLength;
        this.stackLeak();
    }
}
