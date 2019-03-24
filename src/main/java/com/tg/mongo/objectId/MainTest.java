package com.tg.mongo.objectId;

import com.google.common.collect.Maps;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.Map;

/**
 * Created by linzc on 2018/6/26.
 */
public class MainTest {
    @Test
    public void test() {

        ObjectId id = new ObjectId();
        System.out.println(id);
        System.out.println(Integer.toString(id.getCounter()));
        System.out.println(ObjectId.getCurrentCounter());

        id = new ObjectId();
        System.out.println(id);
        System.out.println(Integer.toString(id.getCounter()));
        System.out.println(ObjectId.getCurrentCounter());
    }


    @Test
    public void test2() {

        Map<String, Integer> map = Maps.newHashMap();
        for (int i = 1; i <= 1000000; i++) {
            ObjectId id = new ObjectId();

            if(!map.containsKey(id.toString())){
                map.put(id.toString(), i);
            }
        }

        System.out.println(map.size());
    }
}
