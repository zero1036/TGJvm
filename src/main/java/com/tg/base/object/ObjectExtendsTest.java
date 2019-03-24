package com.tg.base.object;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.Map;

/**
 * Created by linzc on 2018/5/11.
 */
public class ObjectExtendsTest {
    Gson gson = new Gson();

    @Test
    public void test() {
        Object obj = gson.fromJson("{\"id\":23}", Object.class);

        Map<String, Object> map = (Map<String, Object>) obj;
        Object value = map.get("id");
        System.out.println(value);
        System.out.println(obj);

    }
}
