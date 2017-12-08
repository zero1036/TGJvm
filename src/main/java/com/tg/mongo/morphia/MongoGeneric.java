package com.tg.mongo.morphia;

import com.tg.mongo.bean.InvestEvent;
import com.tg.mongo.bean.RuntimeEvent;
import com.tg.mongo.bean.RuntimeEventObj;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 动态内嵌对象测试
 * Created by linzc on 2017/6/2.
 */
public class MongoGeneric extends MongoBase {
    public static void main(String[] args) {
//        insert();
//        query();
//        update();
        insertMulti();
    }

    public static void query() {
        Datastore datastore = getDatastore();

        RuntimeEvent<InvestEvent> event = new RuntimeEvent<>();
        Query<RuntimeEvent<InvestEvent>> qry = datastore.createQuery((Class<RuntimeEvent<InvestEvent>>) event.getClass());
        //带条件查询个数
        List<RuntimeEvent<InvestEvent>> list = qry.filter("Cid = ", 148).asList();
        System.out.println("count = " + list.size());
    }


    public static void update() {
        Datastore datastore = getDatastore();

        RuntimeEvent<InvestEvent> event = new RuntimeEvent<>();
        Class<RuntimeEvent<InvestEvent>> eventClass = (Class<RuntimeEvent<InvestEvent>>) event.getClass();
        Query<RuntimeEvent<InvestEvent>> qry = datastore.createQuery(eventClass);
        UpdateOperations<RuntimeEvent<InvestEvent>> uo = datastore.createUpdateOperations(eventClass);
        uo.set("Cid", 148);

        int count = datastore.update(qry, uo).getUpdatedCount();
        System.out.println("count = " + count);
    }

    private static void insert() {
        Datastore datastore = getDatastore();

        InvestEvent investEvent = new InvestEvent();
        investEvent.setName("Test");
        investEvent.setAmount(142.3);
        RuntimeEvent<InvestEvent> event = new RuntimeEvent<>();
        event.setCustomerId("148");
//        event.setEvent("InvestEvent");
        event.setData(investEvent);

        Key<RuntimeEvent<InvestEvent>> key = datastore.save(event);

        System.out.println(key.getId());
    }


    private static void insertMulti() {
        Datastore datastore = getDatastore();

        Random random = new Random();

        for (int i = 1; i <= 200000; i++) {
            double amount = random.nextDouble();
            int customerId = random.nextInt(100);
            String eventName = random.nextBoolean() ? "InvestEvent" : "NgInvestEvent";

            InvestEvent investEvent = new InvestEvent();
            investEvent.setAmount(amount);
            RuntimeEvent<InvestEvent> event = new RuntimeEvent<>();
            event.setCustomerId(customerId+"");
//            event.setEvent(eventName);
//            event.setCreateDate(new Date());
            event.setData(investEvent);

            Key<RuntimeEvent<InvestEvent>> key = datastore.save(event);
//            System.out.println(key.getId());
        }
    }


    @Test
    public void testInsertObj() {
        Datastore datastore = getDatastore();

        Object obj1 = gson.fromJson("{\"CustomerId\":11908001,\"Amount\":50.0,\"Key\":\"TestKey\"}", Object.class);

//        DBObject blogEntryDbObj =  morphia.toDBObject(obj1);
        RuntimeEventObj event = new RuntimeEventObj();
        event.setCustomerId(1);
        event.setEvent("InvestEvent");
        event.setCreateDate(new Date());
        event.setData(obj1);

        datastore.save(event);

        Object obj2 = gson.fromJson("{\"CustomerId\":11908001,\"TeamId\":5,\"Platform\":\"App\",\"ClientIp\":\"192.168.100.122\",\"EventDate\":\"0001-01-01T00:00:00\"}", Object.class);
        RuntimeEventObj event2 = new RuntimeEventObj();
        event2.setCustomerId(2);
        event2.setEvent("LoginEvent");
        event2.setCreateDate(new Date());
        event2.setData(obj2);

        datastore.save(event2);
    }

    @Test
    public void testSelectObj() {
        Datastore datastore = getDatastore();
        Query<RuntimeEventObj> query = datastore.createQuery(RuntimeEventObj.class);

        query.disableValidation();
        query.filter("Evt = ", "InvestEvent").filter("Data.CustomerId =", 11908001);

        System.out.println(query.toString());

        List<RuntimeEventObj> list = query.asList();
        System.out.println(list.size());
    }
}
