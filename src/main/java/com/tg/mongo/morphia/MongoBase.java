package com.tg.mongo.morphia;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.google.gson.Gson;

/**
 * Created by linzc on 2016/10/24.
 */
public class MongoBase {

    static Gson gson = new Gson();
    static MongoClient client;

    /**
     * MongoClient会自动创建连接池，因此，大部分情况下，整个JVM应用中只需要有一个MongoClient实例就可以。
     */
    static {
        try {
            System.out.println("连接服务器测试.................");
            /**
             * MongoClient连接方式一：不推荐，代码量大且复杂
             */
//            ServerAddress serverAddress = new ServerAddress("192.168.1.72", 27017);
//            List<ServerAddress> seeds = new ArrayList<ServerAddress>();
//            seeds.add(serverAddress);
//            MongoCredential credentials = MongoCredential.createMongoCRCredential("ppmoney", "test",
//                    "Da3LzMogT88DjDleiE8".toCharArray());
//            List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
//            credentialsList.add(credentials);
//            client = new MongoClient(seeds, credentialsList);

            /**
             * MongoClient连接方式二：MongoClientURI通过connection string，简单且可配置
             * 连接文本说明：https://www.zybuluo.com/zero1036/note/708248
             */
//            MongoClientURI clientURI = new MongoClientURI("mongodb://ppmoney:Da3LzMogT88DjDleiE8@192.168.1.72:27017/admin");
            MongoClientURI clientURI = new MongoClientURI("mongodb://ppmoney:nQGSsf3ErHFnSlIT@192.168.101.41:27017/missionv2");
            client = new MongoClient(clientURI);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Datastore getDatastore() {
        morphia = new Morphia();
//        Datastore datastore = morphia.createDatastore(client, "test");
        Datastore datastore = morphia.createDatastore(client, "missionv2");

        return datastore;
    }

    public static MongoDatabase getDatabase() {
        return client.getDatabase("missionv2");
    }

    static Morphia morphia;
}
