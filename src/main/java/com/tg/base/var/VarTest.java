package com.tg.base.var;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by linzc on 2018/5/8.
 */
public class VarTest {
    @Test
    public void test() {
        String var1 = "tg";

        change(var1);
        System.out.println(var1);

    }

    private void change(String var2) {
        var2 = "mark";
    }

    @Test
    public void test2() {
        Account var1 = new Account("tg");
        var1.setInfo(new Info("good"));

        //mark
        change2(var1);
        System.out.println(var1);

        //sa  false
        change3(var1);
        System.out.println(var1);

        //bad
        change4(var1);
        System.out.println(var1);
    }

    @Test
    public void test3() {
        List<Account> accounts = Lists.newArrayList();
        Account var1 = new Account("tg");
        var1.setInfo(new Info("good"));
        accounts.add(var1);

        //change true
        change5(accounts.get(0));
        System.out.println(var1);

        change6(accounts.get(0));
        System.out.println(var1);
    }

    private void change2(Account var2) {
        var2.setName("mark");
    }

    private void change3(Account var2) {
        var2 = new Account("sa");
    }

    private void change4(Account var2) {
        var2.setInfo(new Info("bad"));
    }

    private void change5(Account var2) {
        var2.setInfo(new Info("bad"));
    }

    private void change6(Account var2) {
        TListener tListener = new TListener();
        tListener.setAccount(var2);
        tListener.go();
    }

    public class TListener {
        public Account account;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public void go() {
            account.setName("listner");
        }

    }
}
