package com.tg.base.pattern;

import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linzc on 2017/8/28.
 */
public class PatternTest {
    @Test
    public void test3() {

        Pattern patternNum = Pattern.compile("[1-9]\\d*");
        Pattern pattern = Pattern.compile("AddDays\\([1-9]\\d*\\)");
        Matcher matcher = pattern.matcher("AddDays(3)dddAddDays(5)ksdf9");
        ArrayList al = new ArrayList();

        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String part = matcher.group(0);
            Matcher m2 = patternNum.matcher(part);
            m2.find();
            Integer days = Integer.parseInt(m2.group(0));

            matcher.appendReplacement(sb, days.toString());

            al.add(matcher.group(0));
        }

        System.out.println(sb.toString());
        matcher.appendTail(sb);
        System.out.println(sb.toString());
    }
}
