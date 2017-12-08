package com.tg.base.list;

/**
 * Created by linzc on 2017/3/6.
 */
public class ExceptFn {

    private static long[] cryptTable = new long[0x500];

//    public static void main(String[] args) {
//        prepareCryptTable();
//
//      long result=  hashString("tgor", 1);
//
//        System.out.println(cryptTable.length);
//        System.out.println(result);
//
//    }

    public static void prepareCryptTable() {
        long seed = 0x00100001, index1 = 0, index2 = 0, i;


        for (index1 = 0; index1 < 0x100; index1++) {
            for (index2 = index1, i = 0; i < 5; i++, index2 += 0x100) {
                long temp1, temp2;

                seed = (seed * 125 + 3) % 0x2AAAAB;
                temp1 = (seed & 0xFFFF) << 0x10;

                seed = (seed * 125 + 3) % 0x2AAAAB;
                temp2 = (seed & 0xFFFF);

                cryptTable[(int) index2] = (temp1 | temp2);
            }
        }
    }

    public static long hashString(String target, long dwHashType) {
        char[] chars = target.toCharArray();

        long seed1 = 0x7FED7FED;
        long seed2 = 0xEEEEEEEE;
        int ch;
        for (Integer i = 0; i < chars.length; i++) {
            char c = chars[i];
            ch = (int) c;

            int position = (int) ((dwHashType << 8) + ch);
            seed1 = cryptTable[position] ^ (seed1 + seed2);
            seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
        }
        return seed1;
    }

}
