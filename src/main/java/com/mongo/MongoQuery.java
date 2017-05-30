package com.mongo;

import com.mongo.bean.Customer;
import com.mongo.bean.Setting;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linzc on 2016/10/24.
 */
public class MongoQuery extends MongoBase {

    public static void main(String[] args) {
//        queryCount();
//        queryAll();
//        queryByFindMethod();
//        queryComplex();
        queryOrder();
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

    /**
     * 通过find()，文本条件查询
     * 注意：十分要注意：所有的操作符与字段名，必须留空格，例如：Age!=22，就会报错
     */
    public static void queryByFindMethod() {
        Datastore datastore = getDatastore();
//        Query<Setting> qry = datastore.createQuery(Setting.class);

        //年龄22的用户：{Age:22}
        List<Customer> list = datastore.find(Customer.class, "Age =", 22).asList();

        //等价于以上
        list = datastore.find(Customer.class, "Age", 22).asList();

        //年龄22以上的用户：{Age:{$gt:22}}
        list = datastore.find(Customer.class, "Age >", 22).asList();

        //名字是Tgor、Mark其中一位的用户：{Name:{$in:["Tgor","Mark"]}}
        list = datastore.find(Customer.class, "Name in", new String[]{"Tgor", "Mark"}).asList();

        //名字不在Tgor、Mark之间的用户：{Name:{$nin:["Tgor","Mark"]}}
        list = datastore.find(Customer.class, "Name nin", new String[]{"Tgor", "Mark"}).asList();

        //角色是A、B、C其中之一的用户：{Role:{$in:["A","B","C"]}}。结果失败，所有用户都会查出，role字段是数组，应使用$all
        list = datastore.find(Customer.class, "Role in", new String[]{"A", "B", "C"}).asList();

        //全部具有A、B、C三种角色的用户：{Role:{$all:["A","B","C"]}}。结果失败，所有用户都会查出，role字段是数组，应使用$all
        list = datastore.find(Customer.class, "Role all", new String[]{"A", "B", "C"}).asList();

        //具有3种角色的用户：
        list = datastore.find(Customer.class, "Role size", 3).asList();

        //年龄不等于28的用户：{Age:{$ne:28}}
        list = datastore.find(Customer.class, "Age !=", 28).asList();

        for (Customer customer : list) {
            System.out.println("customer: " + gson.toJson(customer));
        }
    }

    public static void queryComplex() {
        Datastore datastore = getDatastore();
        Query<Customer> qry = datastore.createQuery(Customer.class);

        //年龄22以上，且具有C角色的用户：{$and:[{Age:{$gt:22}},{Role:{$all:["C"]}}]}
        //List<Customer> list = qry.filter("Age >", 22).filter("Role all", new String[]{"C"}).asList();

        //年龄22以上，或具有C角色的用户：{ "$or" : [ { "Age" : { "$gt" : 22}} , { "Role" : { "$all" : [ "C"]}}]}
        List<String> roles = new ArrayList<String>();
        roles.add("C");
        qry.or(qry.criteria("Age").greaterThan(22), qry.criteria("Role").hasAllOf(roles));
        List<Customer> list = qry.asList();

        System.out.println("打印查询条件：" + qry.toString());

        for (Customer customer : list) {
            System.out.println("customer: " + gson.toJson(customer));
        }
    }


    public static void queryOrder() {
        Datastore datastore = getDatastore();
        Query<Customer> qry = datastore.createQuery(Customer.class);

        //根据手机升序，再根据年龄降序
        List<Customer> list = qry.order("Phone,-Age").asList();

        System.out.println("打印查询条件：" + qry.toString());

        for (Customer customer : list) {
            System.out.println("customer: " + gson.toJson(customer));
        }
    }
}
