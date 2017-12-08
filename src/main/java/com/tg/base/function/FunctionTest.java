package com.tg.base.function;

import com.tg.base.function.functionInterfaces.Function2;
import org.junit.Test;

import java.io.FileFilter;
import java.util.Collections;
import java.util.function.*;

/**
 * Created by linzc on 2017/8/3.
 */
public class FunctionTest {

    @Test
    public void testBase() {
        // Function<T, R> -T作为输入，返回的R作为输出
        Function<String, String> function = (x) -> {
            System.out.print(x + ": ");
            return "Function";
        };
        System.out.println(function.apply("hello world"));

        //Predicate<T> -T作为输入，返回的boolean值作为输出
        Predicate<String> pre = (x) -> {
            System.out.print(x);
            return false;
        };
        System.out.println(": " + pre.test("hello World"));

        //Consumer<T> - T作为输入，执行某种动作但没有返回值
        Consumer<String> con = (x) -> {
            System.out.println(x);
        };
        con.accept("hello world");

        //Supplier<T> - 没有任何输入，返回T
        Supplier<String> supp = () -> {
            return "Supplier";
        };
        System.out.println(supp.get());

        //BinaryOperator<T> -两个T作为输入，返回一个T作为输出，对于“reduce”操作很有用
        BinaryOperator<String> bina = (x, y) -> {
            System.out.print(x + " " + y);
            return "BinaryOperator";
        };
        System.out.println("  " + bina.apply("hello ", "world"));
    }


    @Test
    public void testFn() {
        Integer record = 0;
        Integer result = 0;
        String ss;

        result = operation(10, target -> target + 10);
        System.out.println("result=" + result + " record=" + result);

        result = operation(10, target -> {
//            record = target + 10;
            return target + 10;
        });
        System.out.println("result=" + result + " record=" + result);


    }

    @Test
    public void testRuunable() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("运行匿名类");
            }
        });

        //Runnable标识@FunctionalInterface，为函数式接口，编译器会验证该接口是否满足函数式接口的要求，实质类似DotNet的Action
        Runnable runnable = () -> System.out.println("运行表达式");
        Thread thread = new Thread(runnable);

        System.out.println("开始");
        thread.start();
        thread1.start();

        Thread.sleep(1000);
    }

    /**
     * 自定义多参数Function2
     */
    @Test
    public void testCustomFunction2() {
        Function2<Integer, Integer, Integer, String> function2 = (p1, p2, p3) -> String.format("%s", p1 + p2 + p3);
        System.out.println(function2.apply(10, 20, 30));
    }


    private Integer operation(Integer source, Function<Integer, Integer> fn) {
        return fn.apply(source);
    }
}
