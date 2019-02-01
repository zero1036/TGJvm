package com.tg.base.list;

import java.util.*;

/**
 * Created by linzc on 2016/11/21.
 */
public class ArrayListBase {

    public static void main(String[] args) {
//        baseMethod();
//        sort();
//        removeDuplicate1();
//        removeDuplicate2();
//        removeDuplicate3();
        removeDuplicate4();
//        removeDuplicate5();
//        removeDuplicate6();

//        long dd = ExceptFn.hashString("In(CustomerInfo.UtmSource,'weixin_gzyd,WX_ytl,wh4g_ydxs3,WH4G_ydyyt,WH4G_ydxs,wh4g_ydxs2,WHAN_MMCPA,WHAN_MMCPA2,WHAN_MMCPA3,XL,X,xunlei,XL?xluserid,XL?referfrom')", 1);
//        long ddddc = dd + 1;
//
//
//        String lpszString = "In(CustomerInfo4g_ydxs3,WH4G_ydyyt,WH4G_ydxs,wh4g_ydxs2,WHAN_MMCPA,WHAN_MMCPA2,WHAN_MMCPA3,XL,X,xunlei,XL?xluserid,XL?referfrom')";
//
//        long nHash = ExceptFn.hashString(lpszString, 0);
//        long nHashA = ExceptFn.hashString(lpszString, 1);
//        long nHashB = ExceptFn.hashString(lpszString, 2);
//        long nHashStart = nHash % 5;
//        long nHashPos = nHashStart;
    }

    private static void baseMethod() {
        List list = new ArrayList<String>(2);
        list.add("a");
        list.add("b");
        list.add("c");
    }

    private static void sort() {
        List list = new ArrayList<Integer>();
        int target = 500;
        for (int i = 1; i <= target; i++) {
            list.add(i);
        }

        list.set(4, 100);
        list.set(24, 101);

        /**
         * 注意： int i = Comparator.compare(右, 左)
         * `i < 0` : 降序
         * `i >= 0` : 升序
         */
        Collections.sort(list, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                if (o2 > o1) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for (int i = 1; i <= target; i++) {
            System.out.println(list.get(i - 1));
        }
    }

    /**
     * 10w-执行时间：1.195s
     * 20w-执行时间：4.971s
     * 30w-执行时间：36.773s
     */
    private static void removeDuplicate1() {
        int[] arr = generateArray(100000);

        List<Integer> target = new ArrayList<Integer>();    //处理结果数组

        startWatch();

        for (int i = 0; i < arr.length; i++) {

            if (arr[i] == -1) continue;
            for (int j = i + 1; j < arr.length; j++) {

                if (arr[i] == arr[j]) { //记录重复项下标
                    arr[j] = -1;
                }
            }
            target.add(arr[i]);
        }
        stopWatch();

        System.out.println(target.size());
    }

    /**
     * 10w-执行时间：1.205s
     * 20w-执行时间：30.021s
     * 30w-执行时间：67.122s
     */
    private static void removeDuplicate2() {
        int size = 300000;
        int[] arr = generateArray(size);

        List<Integer> target = new ArrayList<Integer>();

        startWatch();

        BitSet bitSet = new BitSet(size);

        int current = 0;
        int mapper = 0;
        for (int i = 0; i < arr.length; i++) {
            if (bitSet.get(i)) {
                continue;
            }

            current = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                mapper = arr[j];
                if (current == mapper) {
                    bitSet.set(j, true);
                }

            }
            target.add(current);
        }
        stopWatch();

        System.out.println(target.size());
        //10w-12.199s
    }

    /**
     * 10w-执行时间：7.492s
     * 20w-执行时间：35.609s
     */
    private static void removeDuplicate3() {
        int size = 200000;
        int[] arr = generateArray(size);
        List<Integer> target = new ArrayList<Integer>();
        startWatch();
        int current = 0;
        for (int i = 0; i < arr.length; i++) {
            current = arr[i];
            if (target.contains(current)) {
                continue;
            } else {
                target.add(current);
            }
        }
        stopWatch();

        System.out.println(target.size());
    }

    /**
     * 10w-执行时间：0.016s
     * 20w-执行时间：0.023s
     * 30w-执行时间：0.025s
     * 100w-执行时间：0.125s
     * <p>
     * 10w个记录，只有一条重复，时间复杂度o(n)，即是99%以上对象，通过hashCode确定数组位置可以确定不同位置，由于位置99%以上不同，无需遍历单个节点的链表
     * removeDuplicate5()，尝试99%都是重复
     */
    private static void removeDuplicate4() {
        int size = 1000000;
        int[] arr = generateArray(size);
        Set<Integer> target = new LinkedHashSet<Integer>();

        startWatch();
        int current = 0;
        for (int i = 0; i < arr.length; i++) {
            current = arr[i];
            target.add(current);
        }
        stopWatch();

        System.out.println(target.size());
    }

    /**
     * 10w-执行时间：0.024s
     * 20w-执行时间：0.009s
     * 30w-执行时间：0.011s
     * 100w-执行时间：0.018s
     * <p>
     * 相比较removeDuplicate4()，10w个记录，只有一条不重复，时间复杂度o(n)，即是99%以上对象，通过hashCode确定数组位置都是同一个位置，数组无需扩容，无需遍历单个节点的链表
     * removeDuplicate6()，尝试99%都是不重复，且hashCode不能确定都是不同位置，不采用Integer
     */
    private static void removeDuplicate5() {
        int size = 100000;
        int[] arr = generateArray(size);
        Set<Integer> target = new LinkedHashSet<Integer>();

        startWatch();
        int current = 0;
        for (int i = 0; i < arr.length; i++) {
            current = arr[i];
            target.add(current);
        }
        stopWatch();

        System.out.println(target.size());
    }

    /**
     * 注意：结果是失败的，因为equal相同值的引用对象，引用地址不同，hash不同，Map数组位置不同，则认为不是重复
     * 所以Set与Map的去重方法，只能用于值对象
     */
    private static void removeDuplicate6() {
        int size = 100000;
        Person[] arr = generateArray3(size);

        Set<Person> target = new LinkedHashSet<Person>();

        startWatch();
        Person current = null;
        for (int i = 0; i < arr.length; i++) {
            current = arr[i];
            target.add(current);
        }
        stopWatch();
        System.out.println(target.size());
    }

    static long startTime;

    private static void startWatch() {
        startTime = System.currentTimeMillis();//记录开始时间
    }

    private static void stopWatch() {
        long endTime = System.currentTimeMillis();//记录结束时间

        float excTime = (float) (endTime - startTime) / 1000;
        System.out.println("执行时间：" + excTime + "s");
    }

    private static int[] generateArray(int target) {
        int[] arr = new int[target];
        for (int i = 1; i <= target; i++) {
            arr[i - 1] = i;
        }
        arr[target - 1] = 1;
        return arr;
    }

    private static int[] generateArray2(int target) {
        int[] arr = new int[target];
        for (int i = 1; i <= target; i++) {
            if (i != arr.length) {
                arr[i - 1] = 1;
            } else {
                arr[i - 1] = i;
            }
        }
        return arr;
    }

    private static Person[] generateArray3(int target) {
        Person[] arr = new Person[target];
        for (int i = 1; i <= target; i++) {
            Person person = new Person(i, "f" + i, "s" + i);
            arr[i - 1] = person;
        }
        arr[target - 1] = new Person(1, "f1", "s1");
        return arr;
    }

}
