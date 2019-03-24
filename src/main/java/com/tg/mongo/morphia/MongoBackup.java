package com.tg.mongo.morphia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.tg.mongo.bean.ConditionTemplate;
import com.tg.mongo.bean.Event;
import com.tg.mongo.bean.RuleTemplate;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by linzc on 2018/5/24.
 */
public class MongoBackup {
    public static void main(String[] args) {

        goBackup("rule", RuleTemplate.class, (ruleTemplate -> ruleTemplate.setId(null)), RuleTemplate::getKey);
        goBackup("condition", ConditionTemplate.class, (c -> c.setId(null)), ConditionTemplate::getKey);
//        goBackup("reward", RewardT.class, (c -> c.setId(null)), ConditionTemplate::getKey);
    }

    public static <T> void goBackup(String file, Class<T> var1, Consumer<T> clearId, Function<T, String> getName) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Datastore dsSource = getDatastoreSource();
        Query<T> qry = dsSource.createQuery(var1);
        List<T> list = dsSource.find(var1, "Key !=", "kcc").asList();

        for (T target : list) {
            clearId.accept(target);

            FileOutputStream outSTr = null;
            BufferedOutputStream buff = null;
            try {
                String value = gson.toJson(target);

                String path = String.format("D:\\TGProject\\suzaku\\suzaku-web\\src\\main\\resources\\static\\%s\\%s.json", file, getName.apply(target));
                File file1 = new File(path);
                if (!file1.exists()) {
                    file1.createNewFile();
                }

                outSTr = new FileOutputStream(file1);
                buff = new BufferedOutputStream(outSTr);
                long begin0 = System.currentTimeMillis();

                buff.write(value.getBytes());

                buff.flush();
                buff.close();
                long end0 = System.currentTimeMillis();
                System.out.println("BufferedOutputStream执行耗时:" + (end0 - begin0) + " 毫秒");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                    buff.close();
                    outSTr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 带条件，查询个数
     */
    @Test
    public void updateEvent() {
        Datastore dsSource = getDatastoreSource();
        Datastore dsTarget = getDatastoreTarget();


        Query<Event> qry = dsSource.createQuery(Event.class);
        //带条件查询个数
//        List<Event> list = qry.asList();

        List<Event> list = dsSource.find(Event.class, "EventName !=", "kcc").asList();

        for (Event event : list) {
            Query<Event> qry2 = dsTarget.find(Event.class, "EventName", event.getEventName());

            Key<Event> key = qry2.getKey();
//            UpdateOperations<Event> uo = dsTarget.createUpdateOperations(Event.class);
            int count = dsTarget.updateFirst(qry2, event, true).getUpdatedCount();
            System.out.println(" count = " + count);
        }

    }

    @Test
    public void updateRule() {
        Datastore dsSource = getDatastoreSource();
        Datastore dsTarget = getDatastoreTarget();


        Query<RuleTemplate> qry = dsSource.createQuery(RuleTemplate.class);
        //带条件查询个数
//        List<Event> list = qry.asList();

        List<RuleTemplate> list = dsSource.find(RuleTemplate.class, "Key !=", "kcc").asList();

        for (RuleTemplate template : list) {
            template.setId(null);
            Query<RuleTemplate> qry2 = dsTarget.find(RuleTemplate.class, "Key", template.getKey());

            Key<RuleTemplate> key = qry2.getKey();
//            UpdateOperations<Event> uo = dsTarget.createUpdateOperations(Event.class);
            int count = dsTarget.updateFirst(qry2, template, true).getUpdatedCount();
            System.out.println(" count = " + count);
        }

    }

    @Test
    public void updateCondition() {
        Datastore dsSource = getDatastoreSource();
        Datastore dsTarget = getDatastoreTarget();


        Query<ConditionTemplate> qry = dsSource.createQuery(ConditionTemplate.class);
        //带条件查询个数
//        List<Event> list = qry.asList();

        List<ConditionTemplate> list = dsSource.find(ConditionTemplate.class, "Key !=", "kcc").asList();

        for (ConditionTemplate template : list) {
            template.setId(null);
            Query<ConditionTemplate> qry2 = dsTarget.find(ConditionTemplate.class, "Key", template.getKey());

            Key<ConditionTemplate> key = qry2.getKey();
            int count = dsTarget.updateFirst(qry2, template, true).getUpdatedCount();
            System.out.println(" count = " + count);
        }

    }


    static Gson gson = new Gson();
    static MongoClient clientSource;
    static MongoClient clientTarget;

    /**
     * MongoClient会自动创建连接池，因此，大部分情况下，整个JVM应用中只需要有一个MongoClient实例就可以。
     */
    static {
        try {
            System.out.println("连接服务器测试.................");

            String alpha = "mongodb://ppmoney:devpasswd@192.168.107.17:27017/admin";
            String sit = "mongodb://ppmoney:Da3LzMogT88DjDleiE8@192.168.105.84:27017/admin";
            String release = "mongodb://ppmoney:Da3LzMogT88DjDleiE8@192.168.21.21:27017/admin";

            MongoClientURI clientURI1 = new MongoClientURI(sit);
            clientSource = new MongoClient(clientURI1);

            //预发布
//            MongoClientURI clientURI2 = new MongoClientURI();

            MongoClientURI clientURI2 = new MongoClientURI(release);
            clientTarget = new MongoClient(clientURI2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Datastore getDatastoreSource() {
        morphiaSource = new Morphia();
//        Datastore datastore = morphia.createDatastore(client, "test");
        Datastore datastore = morphiaSource.createDatastore(clientSource, "MissionV2");

        return datastore;
    }


    public static Datastore getDatastoreTarget() {
        morphiaTarget = new Morphia();
//        Datastore datastore = morphia.createDatastore(client, "test");
        Datastore datastore = morphiaSource.createDatastore(clientTarget, "MissionV2");

        return datastore;
    }

    static Morphia morphiaSource;
    static Morphia morphiaTarget;

}
