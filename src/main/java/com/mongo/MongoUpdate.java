package com.mongo;

import com.mongo.bean.Setting;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.*;

import com.google.gson.Gson;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzc on 2016/10/24.
 */
public class MongoUpdate extends MongoBase {

    public static void main(String[] args) {
//        updateByBson();
        updateFirst();
    }

    /**
     * 类似bson的更新方法，估计是先删除文档，然后再添加
     */
    public static void updateByBson() {
        int count = 0;

        Datastore ds = getDatastore();
        Query<Setting> qry = ds.find(Setting.class, "Name", "信用贷");
        Key<Setting> key = qry.getKey();

        //修改操作
        UpdateOperations<Setting> uo = ds.createUpdateOperations(Setting.class);
        count = ds.update(key, uo).getUpdatedCount();
        System.out.println("1 update count = " + count);

        uo.add("Value", "104002"); //value as array
        uo.add("Value", "104001");
        uo.set("Name", "abcd");  //Name as object

        count = ds.update(key, uo).getUpdatedCount();
        System.out.println("2 update count = " + count);
    }


    public static void updateFirst() {
        int count = 0;

        Datastore ds = getDatastore();
        Query<Setting> qry = ds.find(Setting.class, "Name", "活动标");
        Key<Setting> key = qry.getKey();

        Setting st = new Setting();
        st.setName("活动标");
        st.setValue("104007");
        st.setRange("PrjType");
        st.setActive(true);

        //修改操作
        count = ds.updateFirst(qry, st, true).getUpdatedCount();
        System.out.println("1 update count = " + count);
    }
}
