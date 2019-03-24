package com.tg.mongo.morphia;

import com.tg.mongo.bean.Cids;
import com.tg.mongo.bean.Customer;
import com.tg.mongo.bean.Setting;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

/**
 * Created by linzc on 2016/10/24.
 */
public class MongoUpdate extends MongoBase {

    public static void main(String[] args) {
//        updateByBson();
//        updateFirst();
//        findAndModify();
        test();
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

    public static void findAndModify() {
        Datastore ds = getDatastore();
        Query<Cids> qry = ds.find(Cids.class);
        qry.filter("name =", "missionMid");

        UpdateOperations<Cids> uo = ds.createUpdateOperations(Cids.class);
        uo.inc("id", 1);

        Cids cids = ds.findAndModify(qry, uo);
        System.out.println(cids.getId());
    }


    public static void test() {

String name = "tg4";
        Datastore ds = getDatastore();
        Query<Customer> qry = ds.find(Customer.class);
        qry.filter("name =", name);


        UpdateOperations<Customer> uoVersion = ds.createUpdateOperations(Customer.class);
        uoVersion.inc("version", 1);
        Customer ct = ds.findAndModify(qry, uoVersion, true);
        if (ct == null) {
            Customer newCt = new Customer();
            newCt.setAge(1);
            newCt.setName(name);
            newCt.setVersion(1);
            ds.save(newCt);
        }
        else{

        }

        UpdateOperations<Customer> updateAge = ds.createUpdateOperations(Customer.class);
        updateAge.inc("age", 1);

        UpdateResults results = ds.updateFirst(qry, updateAge, true);
        int n = results.getWriteResult().getN();


        Customer customer = qry.get();
//        Customer cids = (Customer) results;
        System.out.println(results);
    }


}
