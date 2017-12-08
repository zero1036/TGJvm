//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tg.gc;

public class TestAllocation {
    private static int _1MB = 1048576;

    public TestAllocation() {
    }

    public static void main(String[] args) throws Throwable {
        testAllocation();
    }

    public static void testAllocation() throws Throwable {
        byte[] allocation1 = new byte[2 * _1MB];
        byte[] allocation2 = new byte[2 * _1MB];
        byte[] allocation3 = new byte[2 * _1MB];
        byte[] allocation4 = new byte[4 * _1MB];
    }
}
