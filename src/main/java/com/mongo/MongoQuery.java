package com.mongo;

import com.mongo.bean.Setting;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * Created by linzc on 2016/10/24.
 */
public class MongoQuery extends MongoBase {

    public static void main(String[] args) {
        queryCount();
        queryAll();
    }

    /**
     * 带条件，查询个数
     */
    public static void queryCount() {
        Datastore datastore = getDatastore();
        Query<Setting> qry = datastore.createQuery(Setting.class);
        //带条件查询个数
        long count = qry.field("Range").equal("PrjType").countAll();
        System.out.println("count = " + count);
    }

    /**
     * 查询所有对象
     */
    public static void queryAll() {
        Datastore datastore = getDatastore();
        Query<Setting> qry = datastore.createQuery(Setting.class);

        Iterable<Setting> settings = qry.fetch();
        while (settings.iterator().hasNext()) {
            Setting st = settings.iterator().next();
            String target = gson.toJson(st);
            System.out.println("fetch: " + target);
        }
    }
}
