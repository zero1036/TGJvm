package com.tg.annotation.custom;

/**
 * Created by Thinkpads on 2018/3/15.
 */
@NameScanner(name = "name scanner test class")
public class NameScannerTest {
    @NameScanner(name = "field name")
    private String name;

    @NameScanner
    private int age;

    @NameScanner(name = "get name method")
    public String getName() {
        return this.name;
    }

    @NameScanner
    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        NameScannerProcessor2.init(NameScannerTest.class);
        System.out.println("--finished--");
    }
}
