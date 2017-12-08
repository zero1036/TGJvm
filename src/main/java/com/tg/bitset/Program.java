package com.tg.bitset;

import java.util.*;
import java.util.stream.Collectors;

public class Program {

    static long startTime;

    public static void main(String[] args) {
        Integer[] arr = generateArray(1000000);

        fnBitSet(arr);

        fnJDKSort(arr);
    }


    private static List<Integer> fnBitSet(Integer[] arr) {
        startWatch();
        final BitSet bitSet = new BitSet();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            bitSet.set(arr[i], true);
        }

        for (int i = 0; i < arr.length; i++) {
            if (bitSet.get(i)) {
                list.add(i);
            }
        }
        stopWatch();
        System.out.println("count:" + list.size());

        return list;
    }


    private static List<Integer> fnJDKSort(Integer[] arr) {
        startWatch();
        List<Integer> list = Arrays.asList(arr).stream().distinct().collect(Collectors.toList());
        /**
         * 注意： int i = Comparator.compare(右, 左)
         * `i < 0` : 降序
         * `i >= 0` : 升序
         */
        Collections.sort(list, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                if (o2 > o1) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        stopWatch();
        System.out.println("count:" + list.size());

        return list;
    }

    private static Integer[] generateArray(int size) {
        Integer[] arr = new Integer[size];
        final Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size);
        }
        return arr;
    }

    private static void startWatch() {
        startTime = System.currentTimeMillis();//记录开始时间
    }

    private static void stopWatch() {
        long endTime = System.currentTimeMillis();//记录结束时间

        float excTime = (float) (endTime - startTime);
        System.out.println("执行时间：" + excTime + "ms");
    }
}
